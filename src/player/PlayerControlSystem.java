package player;

import collisions.CollisionComponent;
import collisions.CollisionUtil;
import core.*;
import enemies.Enemy;
import enemies.EnemyHealthComponent;
import graphics.ModelComponent;
import graphics.data.Animation;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;
import shapes.Polygon;
import util.Color4d;
import util.Util;
import util.Vec2;
import util.Vec3;

public class PlayerControlSystem extends AbstractSystem {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;

    public PlayerControlSystem(PositionComponent pc, VelocityComponent vc, RotationComponent rc, CollisionComponent cc, ModelComponent mc) {
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
        this.cc = cc;
        this.mc = mc;
    }

    @Override
    public void update() {
        double fall = vc.vel.z;
        vc.vel = new Vec3();

        //Finish animations
        if (mc.animComplete()) {
            mc.setAnim(Animation.STAND);
        }

        //Start new animations
        if (mc.anim.canMove) {
            //Attack
            if (MouseInput.isReleased(0)) {
                mc.setAnim(Animation.ATTACK);
            }
        }

        //If your animation allows movement
        if (mc.anim.canMove) {
            //WASD
            if (Keys.isDown(Keyboard.KEY_W)) {
                vc.vel = vc.vel.add(new Vec3(0, .2, 0));
            }
            if (Keys.isDown(Keyboard.KEY_A)) {
                vc.vel = vc.vel.add(new Vec3(-.2, 0, 0));
            }
            if (Keys.isDown(Keyboard.KEY_S)) {
                vc.vel = vc.vel.add(new Vec3(0, -.2, 0));
            }
            if (Keys.isDown(Keyboard.KEY_D)) {
                vc.vel = vc.vel.add(new Vec3(.2, 0, 0));
            }
            if (vc.vel.lengthSquared() > .04) {
                vc.vel.setLength(.2);
            }

            //Mouse move
            if (MouseInput.isDown(0)) {
                Vec3 mousePos = Util.mousePos();
                if (mousePos != null) {
                    vc.vel = mousePos.subtract(pc.pos).setZ(0).setLength(.2);
                }
            }
            if (MouseInput.isDown(1)) {
                Vec3 mousePos = Util.mousePos();
                if (mousePos != null) {
                    vc.vel = mousePos.subtract(pc.pos).setZ(0).setLength(.5);
                }
            }

            //Jumping
            if (Keys.isDown(KEY_SPACE)) {
                if (!cc.onGround()) {
                    fall = .2;
                }
            }

            //Rotation and animation
            if (!vc.vel.equals(new Vec3())) {
                rc.rot = vc.vel.direction();
                mc.setAnim(Animation.RUN);
                if (MouseInput.isDown(1)) {
                    mc.animSpeed = 1;
                }
            } else {
                mc.setAnim(Animation.STAND);
            }
        }

        //Animation code
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
                        if (cc.ae instanceof Enemy) {
                            EnemyHealthComponent ehc = cc.ae.getComponent(EnemyHealthComponent.class);
                            ehc.health -= 20;
                            if (ehc.health <= 0) {
                                cc.ae.destroySelf();
                            }
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
