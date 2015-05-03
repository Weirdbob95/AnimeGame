package enemies;

import collisions.CollisionComponent;
import collisions.CollisionUtil;
import core.AbstractSystem;
import core.Main;
import graphics.ModelComponent;
import graphics.data.Animation;
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

public class EnemyControlSystem extends AbstractSystem {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;

    public EnemyControlSystem(PositionComponent pc, VelocityComponent vc, RotationComponent rc, CollisionComponent cc, ModelComponent mc) {
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
        this.cc = cc;
        this.mc = mc;
    }

    @Override
    public void update() {
        double fall = vc.vel.z;
        mc.animSpeed = .2 + .1 * Math.random();

        if (mc.animComplete() || mc.anim.canMove) {
            Vec3 playerPos = Main.gameManager.elc.getEntity(Player.class).getComponent(PositionComponent.class).pos;
            vc.vel = playerPos.subtract(pc.pos).setZ(0).setLength(.1);

            if (!vc.vel.equals(new Vec3())) {
                rc.rot = vc.vel.direction();
                mc.setAnim(Animation.RUN);
            } else {
                mc.setAnim(Animation.STAND);
            }

            if (pc.pos.subtract(playerPos).lengthSquared() < 3) {
                mc.setAnim(Animation.ATTACK);
            }
        } else {
            vc.vel = new Vec3();
        }

        switch (mc.anim) {
            case ATTACK:
                if ((int) mc.animIndex == 5) {
                    Vec2[] shape = new Vec2[3];
                    Vec2 side = new Vec2(Math.cos(rc.rot - Math.PI / 4), Math.sin(rc.rot - Math.PI / 4)).multiply(2);
                    shape[0] = pc.pos.toVec2();
                    shape[1] = shape[0].add(side);
                    //shape[2] = shape[1].add(side.normal());
                    shape[2] = shape[0].add(side.normal());
                    for (CollisionComponent cc : CollisionUtil.listAt(new Polygon(shape))) {
                        if (cc.ae instanceof Player) {
                            PlayerHealthComponent ehc = cc.ae.getComponent(PlayerHealthComponent.class);
                            ehc.damage += 1;
                        }
                    }
                    glDisable(GL_TEXTURE_2D);
                    Color4d.WHITE.glColor();
                    glBegin(GL_TRIANGLES);
                    {
                        shape[0].toVec3().setZ(pc.pos.z + .5).glVertex();
                        shape[1].toVec3().setZ(pc.pos.z + .5).glVertex();
                        shape[2].toVec3().setZ(pc.pos.z + .5).glVertex();
                    }
                    glEnd();
                }
        }

        vc.vel = vc.vel.setZ(fall);
    }

}
