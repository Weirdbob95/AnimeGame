package graphics;

import core.AbstractSystem;
import core.Main;
import movement.PositionComponent;
import movement.RotationComponent;
import static org.lwjgl.opengl.GL11.*;
import util.Color4d;
import static util.Util.floatBuffer;

public class ModelSystem extends AbstractSystem {

    private PositionComponent pc;
    private RotationComponent rc;
    private ModelComponent mc;

    public ModelSystem(PositionComponent pc, RotationComponent rc, ModelComponent mc) {
        this.pc = pc;
        this.rc = rc;
        this.mc = mc;
    }

    @Override
    public void update() {
        mc.animIndex += mc.animSpeed;

        if (Main.gameManager.rmc.pos.subtract(pc.pos).lengthSquared() > 5000) {
            return;
        }
        if (mc.visible) {
            if (mc.tex == null) {
                glDisable(GL_TEXTURE_2D);
            } else {
                glEnable(GL_TEXTURE_2D);
                mc.tex.bind();
            }
            SunComponent sc = Main.gameManager.getComponent(SunComponent.class);

            if (mc.shadow) {
                glPushMatrix();
            }

            //Model
//            WHITE.glColor();
            mc.color.glColor();
            glTranslated(pc.pos.x, pc.pos.y, pc.pos.z);
            glRotated(rc.rot * 180 / Math.PI, 0, 0, 1);
            glScaled(mc.scale, mc.scale, mc.scale);
            mc.getModel().opengldraw();
            //Faster than pop
            double invScale = 1 / mc.scale;
            glScaled(invScale, invScale, invScale);
            glRotated(-rc.rot * 180 / Math.PI, 0, 0, 1);
            glTranslated(-pc.pos.x, -pc.pos.y, -pc.pos.z);

            if (mc.shadow) {
                //Shadow
                Color4d.BLACK.glColor();

                glTranslated(pc.pos.x - pc.pos.z * Math.cos(sc.getTheta()) / Math.tan(sc.getPhi()), pc.pos.y - pc.pos.z * Math.sin(sc.getTheta()) / Math.tan(sc.getPhi()), .01);
                glRotated(rc.rot * 180 / Math.PI, 0, 0, 1);
                glMultMatrix(floatBuffer(
                        1, 0, 0, 0,
                        0, 1, 0, 0,
                        -Math.cos(sc.getTheta() - rc.rot) / Math.tan(sc.getPhi()), -Math.sin(sc.getTheta() - rc.rot) / Math.tan(sc.getPhi()), 0, 0,
                        0, 0, 0, 1));
                glScaled(mc.scale, mc.scale, mc.scale);
                mc.getModel().opengldraw();
                glPopMatrix();
            }
        }
    }
}
