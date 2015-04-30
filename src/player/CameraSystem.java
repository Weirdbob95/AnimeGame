package player;

import core.AbstractSystem;
import core.Main;
import movement.PositionComponent;
import util.Vec3;

public class CameraSystem extends AbstractSystem {

    private PositionComponent pc;

    public CameraSystem(PositionComponent pc) {
        this.pc = pc;
    }

    @Override
    public void update() {
        Main.gameManager.rmc.pos = pc.pos.add(new Vec3(0, -5, 15));
        Main.gameManager.rmc.lookAt = pc.pos;
    }
}
