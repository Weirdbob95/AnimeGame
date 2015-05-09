package enemies;

import collisions.CollisionComponent;
import core.AbstractSystem;
import graphics.ModelComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;

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
//        EnemyAnimation prevAnim = (EnemyAnimation) mc.anim;
//        Vec3 playerPos = Main.gameManager.elc.getEntity(Player.class).getComponent(PositionComponent.class).pos;
//
//        //Default
//        mc.attemptAnim(RUN);
//
//        //Attack
//        if (pc.pos.subtract(playerPos).lengthSquared() < 3) {
//            mc.attemptAnim(ATTACK);
//        }
//
//        //Animation code
//        switch ((EnemyAnimation) mc.anim) {
//            case ATTACK:
//                if ((int) mc.animIndex == 0) {
//                    vc.vel = new Vec3(0, 0, vc.vel.z);
//                    rc.rot = playerPos.subtract(pc.pos).setZ(0).setLength(.1).direction();
//                }
//                if ((int) mc.animIndex == 5) {
//                    Vec2[] shape = new Vec2[7];
//                    shape[0] = pc.pos.toVec2();
//                    for (int i = 1; i < 7; i++) {
//                        shape[i] = shape[0].add(new Vec3Polar(2, rc.rot - Math.PI / 2 - Math.PI / 12 + i * Math.PI / 6, 0).toRect().toVec2());
//                    }
//                    for (CollisionComponent cc : CollisionUtil.listAt(new Polygon(shape))) {
//                        if (cc.ae instanceof Player) {
//                            PlayerHealthComponent phc = cc.ae.getComponent(PlayerHealthComponent.class);
//                            phc.damage += 1;
//                        }
//                    }
//                    glDisable(GL_TEXTURE_2D);
//                    Color4d.WHITE.glColor();
//                    glBegin(GL_POLYGON);
//                    for (Vec2 v : shape) {
//                        v.toVec3().setZ(pc.pos.z + .5).glVertex();
//                    }
//                    glEnd();
//                }
//                break;
//            case RUN:
//                double fall = vc.vel.z;
//                vc.vel = new Vec3();
////                if (Math.random() < .01) {
////                    epc.dest = epc.post.add(Vec3.random(5));
////                }
//                epc.dest = playerPos;
//
//                if (pc.pos.subtract(epc.dest).setZ(0).lengthSquared() > .0025) {
//                    vc.vel = epc.dest.subtract(pc.pos).setZ(0).setLength(.05);
//                } else {
//                    vc.vel = epc.dest.subtract(pc.pos).setZ(0);
//                }
//
//                if (!vc.vel.equals(new Vec3())) {
//                    rc.rot = vc.vel.direction();
//                    mc.attemptAnim(EnemyAnimation.RUN);
//                } else {
//                    mc.attemptAnim(EnemyAnimation.STAND);
//                }
//                vc.vel = vc.vel.setZ(fall);
//                break;
//        }
    }

}
