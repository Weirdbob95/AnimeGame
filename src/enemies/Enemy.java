package enemies;

import collisions.CollisionComponent;
import collisions.CollisionSystem;
import core.AbstractEntity;
import graphics.ModelComponent;
import graphics.ModelSystem;
import movement.*;
import states.EnemyWalkingState;
import states.StateComponent;
import states.StateSystem;
import util.Color4d;
import util.Vec3;

public class Enemy extends AbstractEntity {

    public Enemy(Vec3 pos) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent());
        RotationComponent rc = add(new RotationComponent());
        PreviousPositionComponent ppc = add(new PreviousPositionComponent(pos));
        GravityComponent gc = add(new GravityComponent());
        ModelComponent mc = add(new ModelComponent("player_actionpose", .14, new Color4d(1, .3, 0)));
        CollisionComponent cc = add(new CollisionComponent(this, pc, 1, .4));
        EnemyHealthComponent ehc = add(new EnemyHealthComponent(50));
        EnemyPathingComponent epc = add(new EnemyPathingComponent(pos));
        StateComponent sc = add(new StateComponent(this, EnemyWalkingState.class));
        //Systems
//        add(new EnemyControlSystem(pc, vc, rc, cc, mc, epc));
        add(new StateSystem(sc));
        add(new VelocitySystem(pc, vc));
        add(new GravitySystem(vc, gc));
        add(new CollisionSystem(pc, vc, ppc, cc));
        add(new ModelSystem(pc, rc, mc));
        add(new PreviousPositionSystem(pc, ppc));
    }
}
