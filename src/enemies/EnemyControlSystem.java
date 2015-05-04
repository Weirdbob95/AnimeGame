package enemies;

import collisions.CollisionComponent;
import collisions.CollisionUtil;
import core.AbstractSystem;
import core.Main;
import static enemies.EnemyAnimation.*;
import graphics.ModelComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import static org.lwjgl.opengl.GL11.*;
import player.Player;
import player.PlayerHealthComponent;
import shapes.Polygon;
import util.Color4d;
import util.Vec2;
import util.Vec3;
import util.Vec3Polar;

public class EnemyControlSystem extends AbstractSystem {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;
    private EnemyPathingComponent epc;

    public EnemyControlSystem(PositionComponent pc, VelocityComponent vc, RotationComponent rc, CollisionComponent cc, ModelComponent mc, EnemyPathingComponent epc) {
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
        this.cc = cc;
        this.mc = mc;
        this.epc = epc;
    }

    @Override
    public void update() {
        double fall = vc.vel.z;
        vc.vel = new Vec3();

        EnemyAnimation prevAnim = (EnemyAnimation) mc.anim;
        Vec3 playerPos = Main.gameManager.elc.getEntity(Player.class).getComponent(PositionComponent.class).pos;

        //Default
        mc.attemptAnim(RUN);

        //Attack
        if (pc.pos.subtract(playerPos).lengthSquared() < 3) {
            mc.attemptAnim(ATTACK);
        }

        //Animation code
        switch ((EnemyAnimation) mc.anim) {
            case ATTACK:
                if ((int) mc.animIndex == 0) {
                    rc.rot = playerPos.subtract(pc.pos).setZ(0).setLength(.1).direction();
                }
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
                        v.toVec3().setZ(pc.pos.z + .5).glVertex();
                    }
                    glEnd();
                }
                break;
            case RUN:
//                if (Math.random() < .01) {
//                    epc.dest = epc.post.add(Vec3.random(5));
//                }
                epc.dest = playerPos;

                if (pc.pos.subtract(epc.dest).setZ(0).lengthSquared() > .0025) {
                    vc.vel = epc.dest.subtract(pc.pos).setZ(0).setLength(.05);
                } else {
                    vc.vel = epc.dest.subtract(pc.pos).setZ(0);
                }

                if (!vc.vel.equals(new Vec3())) {
                    rc.rot = vc.vel.direction();
                    mc.attemptAnim(EnemyAnimation.RUN);
                } else {
                    mc.attemptAnim(EnemyAnimation.STAND);
                }
                break;

        }

        vc.vel = vc.vel.setZ(fall);

//        double fall = vc.vel.z;
//        mc.animSpeed = .2 + .1 * Math.random();
//
//        if (mc.animComplete() || mc.anim.canMove) {
//            Vec3 playerPos = Main.gameManager.elc.getEntity(Player.class).getComponent(PositionComponent.class).pos;
//            vc.vel = playerPos.subtract(pc.pos).setZ(0).setLength(.1);
//
//            if (!vc.vel.equals(new Vec3())) {
//                rc.rot = vc.vel.direction();
//                mc.setAnim(EnemyAnimation.RUN);
//            } else {
//                mc.setAnim(EnemyAnimation.STAND);
//            }
//
//            if (pc.pos.subtract(playerPos).lengthSquared() < 3) {
//                mc.setAnim(EnemyAnimation.ATTACK);
//            }
//        } else {
//            vc.vel = new Vec3();
//        }
//
//        switch ((EnemyAnimation) mc.anim) {
//            case ATTACK:
//                if ((int) mc.animIndex == 5) {
//                    Vec2[] shape = new Vec2[3];
//                    Vec2 side = new Vec2(Math.cos(rc.rot - Math.PI / 4), Math.sin(rc.rot - Math.PI / 4)).multiply(2);
//                    shape[0] = pc.pos.toVec2();
//                    shape[1] = shape[0].add(side);
//                    //shape[2] = shape[1].add(side.normal());
//                    shape[2] = shape[0].add(side.normal());
//                    for (CollisionComponent cc : CollisionUtil.listAt(new Polygon(shape))) {
//                        if (cc.ae instanceof Player) {
//                            PlayerHealthComponent ehc = cc.ae.getComponent(PlayerHealthComponent.class);
//                            ehc.damage += 1;
//                        }
//                    }
//                    glDisable(GL_TEXTURE_2D);
//                    Color4d.WHITE.glColor();
//                    glBegin(GL_TRIANGLES);
//                    {
//                        shape[0].toVec3().setZ(pc.pos.z + .5).glVertex();
//                        shape[1].toVec3().setZ(pc.pos.z + .5).glVertex();
//                        shape[2].toVec3().setZ(pc.pos.z + .5).glVertex();
//                    }
//                    glEnd();
//                }
//        }
//
//        vc.vel = vc.vel.setZ(fall);
    }

}
