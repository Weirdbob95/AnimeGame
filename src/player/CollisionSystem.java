package player;

import core.AbstractSystem;
import core.Main;
import core.Vec3;
import movement.PositionComponent;
import movement.PreviousPositionComponent;
import movement.VelocityComponent;

public class CollisionSystem extends AbstractSystem {

    private PositionComponent pc;
    private VelocityComponent vc;
    private PreviousPositionComponent ppc;
    private CollisionComponent cc;

    public CollisionSystem(PositionComponent pc, VelocityComponent vc, PreviousPositionComponent ppc, CollisionComponent cc) {
        this.pc = pc;
        this.vc = vc;
        this.ppc = ppc;
        this.cc = cc;
    }

    @Override
    public void update() {
        //Collide with units
        for (CollisionComponent other : Main.gameManager.elc.getListC(CollisionComponent.class)) {
            if (other != cc) {
                Vec3 diff = other.pc.pos.subtract(pc.pos).setZ(0);
                if (diff.lengthSquared() < 3 * cc.width * other.width) {//(sc.size + other.size) * (sc.size + other.size)) {
                    Vec3 diffN = diff.normalize();
                    if (vc.vel.dot(diffN) >= 0) {
                        Vec3 change = diff.subtract(diffN.multiply(cc.width + other.width)).multiply(.05);
                        pc.pos = pc.pos.add(change);
                        other.pc.pos = other.pc.pos.subtract(change);
                        vc.vel = vc.vel.subtract(diffN.multiply(vc.vel.dot(diffN)));
                    }
                }
            }
        }
        //Collide with walls
        if (!cc.open(pc.pos)) {
            Vec3 diff = pc.pos.subtract(ppc.pos);
            pc.pos = ppc.pos;
            if (!cc.open(pc.pos)) {
                System.out.println("bad");
            }
            for (int i = 0; i < 10; i++) {
                if (cc.open(pc.pos.add(new Vec3(diff.x * .1, 0, 0)))) {
                    pc.pos = pc.pos.add(new Vec3(diff.x * .1, 0, 0));
                } else {
                    vc.vel = vc.vel.setX(0);
                    break;
                }
            }
            for (int i = 0; i < 10; i++) {
                if (cc.open(pc.pos.add(new Vec3(0, diff.y * .1, 0)))) {
                    pc.pos = pc.pos.add(new Vec3(0, diff.y * .1, 0));
                } else {
                    vc.vel = vc.vel.setY(0);
                    break;
                }
            }
            for (int i = 0; i < 10; i++) {
                if (cc.open(pc.pos.add(new Vec3(0, 0, diff.z * .1)))) {
                    pc.pos = pc.pos.add(new Vec3(0, 0, diff.z * .1));
                } else {
                    vc.vel = vc.vel.setZ(0);
                    break;
                }
            }
        }
    }
}
