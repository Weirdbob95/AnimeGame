package gui;

import core.AbstractSystem;
import core.Main;
import util.Vec2;
import graphics.Camera;

public class GUISystem extends AbstractSystem {

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void update() {
        Camera.setProjection2D(new Vec2(), Main.gameManager.rmc.viewSize);
    }

}
