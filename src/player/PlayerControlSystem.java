package player;

import collisions.CollisionComponent;
import collisions.CollisionUtil;
import core.*;
import enemies.Enemy;
import enemies.EnemyAnimation;
import enemies.EnemyHealthComponent;
import graphics.ModelComponent;
import movement.*;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;
import static player.PlayerAnimation.*;
import shapes.Polygon;
import util.Color4d;
import util.Util;
import util.Vec2;
import util.Vec3;
import util.Vec3Polar;

public class PlayerControlSystem extends AbstractSystem {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;
    private StaminaComponent sc;

    public PlayerControlSystem(PositionComponent pc, VelocityComponent vc, RotationComponent rc, CollisionComponent cc, ModelComponent mc, StaminaComponent sc) {
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
        this.cc = cc;
        this.mc = mc;
        this.sc = sc;
    }

    @Override
    public void update() {
        PlayerAnimation prevAnim = (PlayerAnimation) mc.anim;

        //Default
        mc.attemptAnim(RUN);

        //Charging
        if (MouseInput.isPressed(0) || prevAnim == CHARGING) {
            mc.attemptAnim(CHARGING);
        }
        //Attack
        if (MouseInput.isReleased(0) && mc.anim == CHARGING) {
            mc.attemptAnim(ATTACK);
        }

        //Animation code
        switch ((PlayerAnimation) mc.anim) {
            case ATTACK:
                if (cc.collisions.isEmpty()) {
                    if (mc.animIndex == 0) {
                        Vec3 mousePos = Util.mousePos();
                        if (mousePos != null) {
                            vc.vel = new Vec3Polar(1 - 1 * Math.pow(.98, MouseInput.getTime(0)), rc.rot, 0).toRect().setZ(vc.vel.z);
                        }
                    }
                } else {
                    vc.vel = new Vec3(0, 0, vc.vel.z);
                }
                if ((int) mc.animIndex == 5) {
                    vc.vel = new Vec3(0, 0, vc.vel.z);
                    Vec2[] shape = new Vec2[7];
                    shape[0] = pc.pos.toVec2();
                    for (int i = 1; i < 7; i++) {
                        shape[i] = shape[0].add(new Vec3Polar(2, rc.rot - Math.PI / 2 - Math.PI / 12 + i * Math.PI / 6, 0).toRect().toVec2());
                    }
                    for (CollisionComponent cc : CollisionUtil.listAt(new Polygon(shape))) {
                        if (cc.ae instanceof Enemy) {
                            EnemyHealthComponent ehc = cc.ae.getComponent(EnemyHealthComponent.class);
                            ehc.health -= 20;

                            cc.ae.getComponent(ModelComponent.class).attemptAnim(EnemyAnimation.FLINCH);

                            cc.ae.getComponent(VelocityComponent.class).vel = cc.pc.pos.subtract(pc.pos).setLength(.1);

                            if (ehc.health <= 0) {
                                cc.ae.destroySelf();
                            }
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
            case CHARGING:
                //Mouse move
                if (MouseInput.isDown(0)) {
                    Vec3 mousePos = Util.mousePos();
                    if (mousePos != null) {
                        vc.vel = mousePos.subtract(pc.pos).setZ(0).setLength(.1).setZ(vc.vel.z);
                        rc.rot = vc.vel.direction();
                    }
                }
                mc.attemptAnim(CHARGING);
                break;
            case RUN:
                double fall = vc.vel.z;
                vc.vel = new Vec3();
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
//                double speed = 0;
//                if (Keys.isDown(Keyboard.KEY_W)) {
//                    speed += .2;
//                }
//                if (Keys.isDown(Keyboard.KEY_S)) {
//                    speed -= .2;
//                }
//                if (Keys.isDown(Keyboard.KEY_A)) {
//                    rc.rot += .1;
//                }
//                if (Keys.isDown(Keyboard.KEY_D)) {
//                    rc.rot -= .1;
//                }
//                vc.vel = new Vec3Polar(speed, rc.rot, 0).toRect();

                //Mouse move
//                if (MouseInput.isDown(0)) {
//                    Vec3 mousePos = Util.mousePos();
//                    if (mousePos != null) {
//                        vc.vel = mousePos.subtract(pc.pos).setZ(0).setLength(.1);
//                    }
//                }
                if (MouseInput.isDown(1)) {
                    if (sc.stamina >= .5) {
                        Vec3 mousePos = Util.mousePos();
                        if (mousePos != null) {
                            sc.stamina -= .5;
                            vc.vel = mousePos.subtract(pc.pos).setZ(0).setLength(.5);
                        }
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
                    if (MouseInput.isDown(1)) {
                        mc.animSpeed = 1;
                    } else {
                        mc.animSpeed = .5;
                    }
                } else {
                    mc.attemptAnim(STAND);
                }
                vc.vel = vc.vel.setZ(fall);
                break;
        }
    }
}
