package player;

import core.AbstractSystem;
import core.Main;
import core.Vec3;
import movement.PositionComponent;

public class CameraSystem extends AbstractSystem {

    private PositionComponent pc;

    public CameraSystem(PositionComponent pc) {
        this.pc = pc;
    }

    @Override
    public void update() {
        Main.gameManager.rmc.pos = pc.pos.add(new Vec3(0, -5, 5));
        Main.gameManager.rmc.lookAt = pc.pos;
    }
}
