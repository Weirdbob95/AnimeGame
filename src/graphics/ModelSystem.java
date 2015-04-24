package graphics;

import core.AbstractSystem;
import core.Color4d;
import core.Main;
import static core.Util.floatBuffer;
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
//        glMaterial(GL_FRONT, GL_SPECULAR, mc.color.toFloatBuffer());
//        glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);
//        glMaterial(GL_FRONT, GL_DIFFUSE, mc.color.toFloatBuffer());
//        glMaterial(GL_FRONT, GL_AMBIENT, mc.color.toFloatBuffer());

        glDisable(GL_TEXTURE_2D);
        SunComponent sc = Main.gameManager.getComponent(SunComponent.class);

        //Model
        mc.color.glColor();
        glPushMatrix();
        glTranslated(pc.pos.x, pc.pos.y, pc.pos.z);
        glRotated(rc.rot * 180 / Math.PI, 0, 0, 1);
        glRotated(90, 1, 0, 0);
        glScaled(mc.scale, mc.scale, mc.scale);
        mc.model.opengldraw();
        glPopMatrix();

        //Shadow
        Color4d.BLACK.glColor();
        glPushMatrix();
        glTranslated(pc.pos.x, pc.pos.y, pc.pos.z + .01);
        glRotated(rc.rot * 180 / Math.PI, 0, 0, 1);
        glRotated(90, 1, 0, 0);
        glMultMatrix(floatBuffer(
                1, 0, 0, 0,
                -Math.cos(rc.rot - sc.theta) / Math.tan(sc.phi), 0, -Math.sin(rc.rot - sc.theta) / Math.tan(sc.phi), 0,
                0, 0, 1, 0,
                0, 0, 0, 1));
        //glTranslated(0, height, 0);
        glScaled(mc.scale, mc.scale, mc.scale);
        mc.model.opengldraw();
        glPopMatrix();
    }

}
