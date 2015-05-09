package states;

import collisions.CollisionComponent;
import collisions.CollisionUtil;
import core.AbstractEntity;
import graphics.ModelComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import static org.lwjgl.opengl.GL11.*;
import player.Player;
import player.PlayerHealthComponent;
import shapes.Polygon;
import util.*;

public class EnemyAttackState extends State {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;

    public EnemyAttackState(AbstractEntity e, StateComponent sc) {
        super(e, sc);
        pc = e.getComponent(PositionComponent.class);
        vc = e.getComponent(VelocityComponent.class);
        rc = e.getComponent(RotationComponent.class);
        cc = e.getComponent(CollisionComponent.class);
        mc = e.getComponent(ModelComponent.class);

        mc.setAnim("attack", 9);
        mc.animSpeed = .2;

        vc.vel = new Vec3(0, 0, vc.vel.z);
    }

    @Override
    public void update() {
        //Damage player
        if ((int) mc.animIndex == 5) {
            Vec2[] shape = new Vec2[7];
            shape[0] = pc.pos.toVec2();
            for (int i = 1; i < 7; i++) {
                shape[i] = shape[0].add(new Vec3Polar(2, rc.rot - Math.PI / 2 - Math.PI / 12 + i * Math.PI / 6, 0).toRect().toVec2());
            }
            for (CollisionComponent cc : CollisionUtil.listAt(new Polygon(shape))) {
                if (cc.ae instanceof Player) {
                    PlayerHealthComponent phc = cc.ae.getComponent(PlayerHealthComponent.class);
                    phc.damage += 1;
                }
            }
            glDisable(GL_TEXTURE_2D);
            Color4d.WHITE.glColor();
            glBegin(GL_POLYGON);
            for (Vec2 v : shape) {
                v.toVec3().setZ(pc.pos.z + 1.5).glVertex();
            }
            glEnd();
        }

        //End anim
        if (mc.animComplete()) {
            sc.setState(EnemyWalkingState.class);
        }
    }

}
