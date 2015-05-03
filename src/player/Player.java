package player;

import collisions.CollisionComponent;
import collisions.CollisionSystem;
import core.AbstractEntity;
import graphics.ModelComponent;
import graphics.ModelSystem;
import graphics.data.Animation;
import movement.*;
import util.Color4d;
import util.Vec3;

public class Player extends AbstractEntity {

    public Player(Vec3 pos) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent());
        RotationComponent rc = add(new RotationComponent());
        PreviousPositionComponent ppc = add(new PreviousPositionComponent(pos));
        GravityComponent gc = add(new GravityComponent());
        ModelComponent mc = add(new ModelComponent(Animation.RUN, .2, new Color4d(.2, .4, 1)));
        CollisionComponent cc = add(new CollisionComponent(this, pc, 1, .4));
        PlayerHealthComponent hc = add(new PlayerHealthComponent(100));
        //Systems
        add(new PlayerControlSystem(pc, vc, rc, cc, mc));
        add(new VelocitySystem(pc, vc));
        add(new GravitySystem(vc, gc));
        add(new CollisionSystem(pc, vc, ppc, cc));
        add(new CameraSystem(pc));
        add(new ModelSystem(pc, rc, mc));
        add(new PreviousPositionSystem(pc, ppc));
        add(new PlayerHealthSystem(hc));
    }
}
