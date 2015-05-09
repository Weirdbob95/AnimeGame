package states;

import collisions.CollisionComponent;
import collisions.CollisionUtil;
import core.AbstractEntity;
import core.MouseInput;
import enemies.Enemy;
import enemies.EnemyHealthComponent;
import graphics.ModelComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import static org.lwjgl.opengl.GL11.*;
import player.CameraComponent;
import shapes.Polygon;
import util.*;

public class PlayerAttackState extends State {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;
    private CameraComponent cac;
    public boolean repeat;

    public PlayerAttackState(AbstractEntity e, StateComponent sc) {
        super(e, sc);
        pc = e.getComponent(PositionComponent.class);
        vc = e.getComponent(VelocityComponent.class);
        rc = e.getComponent(RotationComponent.class);
        cc = e.getComponent(CollisionComponent.class);
        mc = e.getComponent(ModelComponent.class);
        cac = e.getComponent(CameraComponent.class);

        rc.rot = cac.t;
        mc.setAnim("attack", 9);
        mc.animSpeed = .5;

        vc.vel = new Vec3Polar(.2, rc.rot, 0).toRect();
    }

    @Override
    public void update() {
        //Stop on hit
        if (!cc.collisions.isEmpty()) {
            vc.vel = new Vec3(0, 0, vc.vel.z);
        }

        //Damage enemies
        if ((int) mc.animIndex == 5) {
            Vec2[] shape = new Vec2[7];
            shape[0] = pc.pos.toVec2();
            for (int i = 1; i < 7; i++) {
                shape[i] = shape[0].add(new Vec3Polar(2, rc.rot - Math.PI / 2 - Math.PI / 12 + i * Math.PI / 6, 0).toRect().toVec2());
            }
            for (CollisionComponent cc : CollisionUtil.listAt(new Polygon(shape))) {
                if (cc.ae instanceof Enemy) {
                    //Knockback
                    cc.ae.getComponent(StateComponent.class).setState(EnemyFlinchState.class);
                    cc.ae.getComponent(VelocityComponent.class).vel = cc.pc.pos.subtract(pc.pos).setZ(0).setLength(.1);
                    //Damage
                    EnemyHealthComponent ehc = cc.ae.getComponent(EnemyHealthComponent.class);
                    ehc.health -= 20;
                    if (ehc.health <= 0) {
                        cc.ae.destroySelf();
                    }
                }
            }
            glDisable(GL_TEXTURE_2D);
            Color4d.WHITE.glColor();
            glBegin(GL_POLYGON);
            for (Vec2 v : shape) {
                v.toVec3().setZ(pc.pos.z + 1.3).glVertex();
            }
            glEnd();
        }

        //Repeat
        if (MouseInput.isPressed(0)) {
            repeat = true;
        }

//        if ((int) mc.animIndex == 3 && repeat) {
//            mc.animSpeed = Math.abs(mc.animSpeed);
//            repeat = false;
//        }
//        if ((int) mc.animIndex == 7 && repeat) {
//            mc.animSpeed = -Math.abs(mc.animSpeed);
//            vc.vel = new Vec3();
//        }
        if ((int) mc.animIndex == 8 && repeat) {
            mc.animIndex = 0;
            rc.rot = cac.t;
            vc.vel = new Vec3Polar(.2, rc.rot, 0).toRect();
            repeat = false;
        }

        //End anim
        if (mc.animComplete()) {
            sc.setState(PlayerWalkingState.class);
        }
    }

}
