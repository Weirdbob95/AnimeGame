package enemies;

import core.AbstractSystem;
import core.Main;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import player.Player;

public class AISystem extends AbstractSystem {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;

    public AISystem(PositionComponent pc, VelocityComponent vc, RotationComponent rc) {
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
    }

    @Override
    public void update() {
        vc.vel = Main.gameManager.elc.get(Player.class).getComponent(PositionComponent.class).pos.subtract(pc.pos).setLength(.05).setZ(vc.vel.z);
        rc.rot = vc.vel.direction() + Math.PI / 2;
    }

}
