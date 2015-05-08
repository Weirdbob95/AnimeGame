package player;

import core.AbstractSystem;
import core.Main;
import core.MouseInput;
import movement.PositionComponent;
import movement.RotationComponent;
import util.Vec3;
import util.Vec3Polar;

public class CameraSystem extends AbstractSystem {

    private PositionComponent pc;
    private RotationComponent rc;
    private CameraComponent cc;

    public CameraSystem(PositionComponent pc, RotationComponent rc, CameraComponent cc) {
        this.pc = pc;
        this.rc = rc;
        this.cc = cc;
    }

    @Override
    public void update() {
        cc.t -= MouseInput.mouseDelta().x / 500;
        cc.p += MouseInput.mouseDelta().y / 500;
        if (cc.p > Math.PI / 2) {
            cc.p = Math.PI / 2;
        }
        if (cc.p < -Math.PI / 2) {
            cc.p = -Math.PI / 2;
        }
        Main.gameManager.rmc.pos = pc.pos.add(new Vec3(0, 0, 1)).add(new Vec3Polar(-5, cc.t, cc.p).toRect());
        Main.gameManager.rmc.lookAt = pc.pos.add(new Vec3(0, 0, 1));
    }
}
