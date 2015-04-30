package graphics;

import core.*;
import static core.Util.floatBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class RenderManagerSystem extends AbstractSystem {

    private RenderManagerComponent rmc;

    public RenderManagerSystem(RenderManagerComponent rmc) {
        this.rmc = rmc;

        try {
            //Display Init
            Camera.setDisplayMode(rmc.displayWidth, rmc.displayHeight, rmc.startFullscreen);
            Display.setVSyncEnabled(true);
            Display.setResizable(true);
            Display.setTitle("So how are you today?");
            Display.create();
            //OpenGL Init
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            //3D
            glEnable(GL_DEPTH_TEST);
            glDepthFunc(GL_LEQUAL);
            glAlphaFunc(GL_GREATER, 0.5f);

            glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

            //----------- Variables & method calls added for Lighting Test -----------//
            glShadeModel(GL_SMOOTH);
            glLightModel(GL_LIGHT_MODEL_AMBIENT, floatBuffer(.5, .5, .5, 1));		// global ambient light

            glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
            glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
            //----------- END: Variables & method calls added for Lighting Test -----------//
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (Keys.isPressed(Keyboard.KEY_F11)) {
            Camera.setDisplayMode(rmc.displayWidth, rmc.displayHeight, !Display.isFullscreen());
        }

        Camera.calculateViewport(new Vec2(rmc.viewWidth, rmc.viewHeight));

        Camera.setProjection3D(rmc.fov, rmc.displayWidth / rmc.displayHeight, rmc.pos, rmc.lookAt, rmc.UP);
    }

}
