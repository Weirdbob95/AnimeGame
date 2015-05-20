package player;

import collisions.CollisionComponent;
import collisions.CollisionSystem;
import core.AbstractEntity;
import graphics.ModelComponent;
import graphics.ModelSystem;
import graphics.loading.SpriteContainer;
import movement.*;
import states.PlayerWalkingState;
import states.StateComponent;
import states.StateSystem;
import static util.Color4d.WHITE;
import util.Vec3;

public class Player extends AbstractEntity {

    public Player(Vec3 pos) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent());
        RotationComponent rc = add(new RotationComponent());
        PreviousPositionComponent ppc = add(new PreviousPositionComponent(pos));
        GravityComponent gc = add(new GravityComponent());
        ModelComponent mc = add(new ModelComponent("pose", .15, WHITE));// new Color4d(.2, .4, 1)));
        mc.tex = SpriteContainer.loadSprite("tex_test3");
        CollisionComponent cc = add(new CollisionComponent(this, pc, 1, .4));
        PlayerHealthComponent hc = add(new PlayerHealthComponent(30));
        StaminaComponent stc = add(new StaminaComponent(100, .2));
        CameraComponent cac = add(new CameraComponent());
        StateComponent sc = add(new StateComponent(this, PlayerWalkingState.class));
        //Systems
        //add(new PlayerControlSystem(pc, vc, rc, cc, mc, stc));
        add(new StateSystem(sc));
        add(new VelocitySystem(pc, vc));
        add(new GravitySystem(vc, gc));
        add(new CollisionSystem(pc, vc, ppc, cc));
        add(new CameraSystem(pc, rc, cac));
        add(new ModelSystem(pc, rc, mc));
        add(new PreviousPositionSystem(pc, ppc));
        add(new PlayerHealthSystem(hc));
        add(new StaminaSystem(stc));
    }
}
