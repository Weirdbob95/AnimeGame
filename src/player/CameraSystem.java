package player;

import core.AbstractSystem;
import core.Main;
import movement.PositionComponent;
import movement.RotationComponent;
import util.Vec3;

public class CameraSystem extends AbstractSystem {

    private PositionComponent pc;
    private RotationComponent rc;

    public CameraSystem(PositionComponent pc, RotationComponent rc) {
        this.pc = pc;
        this.rc = rc;
    }

    @Override
    public void update() {
        Main.gameManager.rmc.pos = pc.pos.add(new Vec3(0, -.01, 15));
//        Main.gameManager.rmc.pos = pc.pos.add(new Vec3Polar(10, rc.rot + Math.PI, Math.PI / 5).toRect());
        Main.gameManager.rmc.lookAt = pc.pos;
    }
}
