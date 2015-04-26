package graphics;

import core.*;
import static core.Util.floatBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class RenderManagerSystem extends AbstractSystem {

    private RenderManagerComponent rmc;

    public RenderManagerSystem(RenderManagerComponent rmc) {
        this.rmc = rmc;

        try {
            //Display Init
            setDisplayMode(rmc.displayWidth, rmc.displayHeight, rmc.startFullscreen);
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
            glEnable(GL_ALPHA_TEST);

            glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

            //----------- Variables & method calls added for Lighting Test -----------//
            glShadeModel(GL_SMOOTH);
            glLightModel(GL_LIGHT_MODEL_AMBIENT, floatBuffer(.5, .5, .5, 1));		// global ambient light

            glEnable(GL_LIGHTING);										// enables light0

            glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
            glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
            //----------- END: Variables & method calls added for Lighting Test -----------//
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
    }

    private void calculateViewport() {
        int w = Display.getWidth();
        int h = Display.getHeight();
        int dw = rmc.viewWidth;
        int dh = rmc.viewHeight;
        int vw, vh;
        if (w * dh > h * dw) {
            vh = h;
            vw = dw * h / dh;
        } else {
            vw = w;
            vh = dh * w / dw;
        }
        int left = (w - vw) / 2;
        int bottom = (h - vh) / 2;
        glViewport(left, bottom, vw, vh);
    }

    public static void setDisplayMode(int width, int height, boolean fullscreen) {
        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width)
                && (Display.getDisplayMode().getHeight() == height)
                && (Display.isFullscreen() == fullscreen)) {
            return;
        }
        try {
            DisplayMode targetDisplayMode = null;
            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;
                for (DisplayMode current : modes) {
                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }
                        // if we've found a match for bpp and frequence against the
                        // original display mode then it's probably best to go for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
                                && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width, height);
            }
            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return;
            }
            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);
        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }
    }

    @Override
    public void update() {
        if (Keys.isPressed(Keyboard.KEY_F11)) {
            setDisplayMode(rmc.displayWidth, rmc.displayHeight, !Display.isFullscreen());
        }

        calculateViewport();
        //3D
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective((float) rmc.fov, (float) rmc.viewWidth / rmc.viewHeight, 0.1f, 1000);
        gluLookAt((float) rmc.pos.x, (float) rmc.pos.y, (float) rmc.pos.z,
                (float) rmc.lookAt.x, (float) rmc.lookAt.y, (float) rmc.lookAt.z,
                (float) rmc.UP.x, (float) rmc.UP.y, (float) rmc.UP.z);

        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(1, 1, 1, 1);

        //3D
        glClear(GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
    }

}
