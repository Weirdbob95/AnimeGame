package graphics;

import core.AbstractSystem;
import util.Color4d;
import core.Main;
import static util.Util.floatBuffer;
import movement.PositionComponent;
import movement.RotationComponent;
import static org.lwjgl.opengl.GL11.*;

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

        if (mc.visible) {

            glDisable(GL_TEXTURE_2D);
            SunComponent sc = Main.gameManager.getComponent(SunComponent.class);

            //Test
//            Color4d.RED.glColor();
//            glPushMatrix();
//            glTranslated(pc.pos.x, pc.pos.y, pc.pos.z);
//            glScaled(mc.scale, mc.scale, mc.scale);
//            mc.getModel().opengldraw();
//            glPopMatrix();
            //Model
            mc.color.glColor();
            glPushMatrix();
            glTranslated(pc.pos.x, pc.pos.y, pc.pos.z);
            glRotated(rc.rot * 180 / Math.PI, 0, 0, 1);
            glScaled(mc.scale, mc.scale, mc.scale);
            mc.getModel().opengldraw();
            glPopMatrix();

            if (mc.shadow) {
                //Shadow
                Color4d.BLACK.glColor();
                glPushMatrix();
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
