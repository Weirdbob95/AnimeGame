package gui;

import core.AbstractSystem;
import core.Main;
import graphics.RenderManagerComponent;
import static org.lwjgl.opengl.GL11.*;

public class GUISystem extends AbstractSystem {

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void update() {
        RenderManagerComponent rmc = Main.gameManager.getComponent(RenderManagerComponent.class);
        //Set view for easy drawing
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, rmc.displayWidth, 0, rmc.displayHeight, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glDisable(GL_ALPHA_TEST);
        glDisable(GL_LIGHTING);
    }

}
