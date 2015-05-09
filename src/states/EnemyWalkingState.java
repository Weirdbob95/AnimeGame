package states;

import collisions.CollisionComponent;
import core.AbstractEntity;
import core.Main;
import enemies.EnemyPathingComponent;
import graphics.ModelComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import player.Player;
import util.Vec3;

public class EnemyWalkingState extends State {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;
    private EnemyPathingComponent epc;
    public boolean sprinting;

    public EnemyWalkingState(AbstractEntity e, StateComponent sc) {
        super(e, sc);
        pc = e.getComponent(PositionComponent.class);
        vc = e.getComponent(VelocityComponent.class);
        rc = e.getComponent(RotationComponent.class);
        cc = e.getComponent(CollisionComponent.class);
        mc = e.getComponent(ModelComponent.class);
        epc = e.getComponent(EnemyPathingComponent.class);
    }

    @Override
    public void update() {
        Vec3 playerPos = Main.gameManager.elc.getEntity(Player.class).getComponent(PositionComponent.class).pos;

        double fall = vc.vel.z;
        vc.vel = new Vec3();
//                if (Math.random() < .01) {
//                    epc.dest = epc.post.add(Vec3.random(5));
//                }
        epc.dest = playerPos;

        if (pc.pos.subtract(epc.dest).setZ(0).lengthSquared() > .0025) {
            vc.vel = epc.dest.subtract(pc.pos).setZ(0).setLength(.05);
        } else {
            vc.vel = epc.dest.subtract(pc.pos).setZ(0);
        }

        //Rotation and animation
        if (!vc.vel.equals(new Vec3())) {
            rc.rot = vc.vel.direction();
            mc.setAnim("run", 10);
            mc.animSpeed = .2;
        } else {
            mc.setModel("player_actionpose");
        }

        //Attack
        if (pc.pos.subtract(playerPos).lengthSquared() < 3) {
            sc.setState(EnemyAttackState.class);
        }

        vc.vel = vc.vel.setZ(fall);
    }
}
