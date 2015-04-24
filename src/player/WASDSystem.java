package player;

import core.AbstractSystem;
import core.Keys;
import core.Vec3;
import movement.RotationComponent;
import movement.VelocityComponent;
import org.lwjgl.input.Keyboard;

public class WASDSystem extends AbstractSystem {

    private VelocityComponent vc;
    private RotationComponent rc;

    public WASDSystem(VelocityComponent vc, RotationComponent rc) {
        this.vc = vc;
        this.rc = rc;
    }

    @Override
    public void update() {
        vc.vel = new Vec3();
        if (Keys.isDown(Keyboard.KEY_W)) {
            vc.vel = vc.vel.add(new Vec3(0, .1, 0));
        }
        if (Keys.isDown(Keyboard.KEY_A)) {
            vc.vel = vc.vel.add(new Vec3(-.1, 0, 0));
        }
        if (Keys.isDown(Keyboard.KEY_S)) {
            vc.vel = vc.vel.add(new Vec3(0, -.1, 0));
        }
        if (Keys.isDown(Keyboard.KEY_D)) {
            vc.vel = vc.vel.add(new Vec3(.1, 0, 0));
        }
        if (!vc.vel.equals(new Vec3())) {
            rc.rot = vc.vel.direction() + Math.PI / 2;
        }
    }

}
