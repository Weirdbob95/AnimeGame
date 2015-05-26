package states;

import collisions.CollisionComponent;
import core.AbstractEntity;
import core.Keys;
import core.MouseInput;
import graphics.ModelComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.KEY_SPACE;
import player.CameraComponent;
import player.StaminaComponent;
import util.Vec3;

public class PlayerWalkingState extends State {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;
    private StaminaComponent stc;
    private CameraComponent cac;
    public boolean sprinting;

    public PlayerWalkingState(AbstractEntity e, StateComponent sc) {
        super(e, sc);
        pc = e.getComponent(PositionComponent.class);
        vc = e.getComponent(VelocityComponent.class);
        rc = e.getComponent(RotationComponent.class);
        cc = e.getComponent(CollisionComponent.class);
        mc = e.getComponent(ModelComponent.class);
        stc = e.getComponent(StaminaComponent.class);
        cac = e.getComponent(CameraComponent.class);

        sprinting = Keys.isDown(Keyboard.KEY_LSHIFT);
    }

    @Override
    public void update() {
        //double fall = vc.vel.z;
        //vc.vel = new Vec3();

        //Walk or run
        double walkSpeed;
//        if (Keys.isPressed(Keyboard.KEY_LSHIFT)) {
//            sprinting = true;
//        }
//        if (Keys.isReleased(Keyboard.KEY_LSHIFT) || stc.stamina <= 0) {
//            sprinting = false;
//        }
        if (Keys.isPressed(Keyboard.KEY_LSHIFT)) {
            sprinting = !sprinting;
        }
        if (stc.stamina <= 0) {
            sprinting = false;
        }

        if (sprinting) {
            walkSpeed = .4;
            mc.animSpeed = 1;
        } else {
            walkSpeed = .2;
            mc.animSpeed = .5;
        }

        //WASD
        double fSpeed = 0, sSpeed = 0;
        if (Keys.isDown(Keyboard.KEY_W)) {
            fSpeed += walkSpeed;
        }
        if (Keys.isDown(Keyboard.KEY_S)) {
            fSpeed -= walkSpeed;
        }
        if (Keys.isDown(Keyboard.KEY_A)) {
            sSpeed -= walkSpeed;
        }
        if (Keys.isDown(Keyboard.KEY_D)) {
            sSpeed += walkSpeed;
        }
        if (fSpeed != 0 || sSpeed != 0) {
            if (sprinting) {
                stc.stamina -= .5;
            }
            if (cc.onGround()) {
                vc.vel = new Vec3(Math.cos(cac.t) * fSpeed + Math.sin(cac.t) * sSpeed,
                        Math.sin(cac.t) * fSpeed - Math.cos(cac.t) * sSpeed, 0).setLength(walkSpeed).setZ(vc.vel.z);;
            } else {
                Vec3 nv = new Vec3(Math.cos(cac.t) * fSpeed + Math.sin(cac.t) * sSpeed,
                        Math.sin(cac.t) * fSpeed - Math.cos(cac.t) * sSpeed, 0).setLength(walkSpeed).setZ(vc.vel.z);
                if (nv.subtract(vc.vel).lengthSquared() > .0095 * .0095) {
                    vc.vel = vc.vel.add(nv.subtract(vc.vel).setLength(.0095));
                } else {
                    vc.vel = nv;
                }
            }
        } else {
            if (cc.onGround()) {
                vc.vel = new Vec3(0, 0, vc.vel.z);
            } else {
                Vec3 nv = new Vec3(0, 0, vc.vel.z);
                if (nv.subtract(vc.vel).lengthSquared() > .0095 * .0095) {
                    vc.vel = vc.vel.add(nv.subtract(vc.vel).setLength(.0095));
                } else {
                    vc.vel = nv;
                }
            }
        }

        //Jumping
        if (Keys.isPressed(KEY_SPACE)) {
            if (cc.onGround()) {
                //fall = .2;
                vc.vel = vc.vel.setZ(.2);
            }
        }

        //Rotation and animation
        if (cc.onGround()) {
            if (!vc.vel.setZ(0).equals(new Vec3())) {
                rc.rot = vc.vel.direction();
                mc.setAnim("player_run", 10);
            } else {
                mc.setModel("player_actionpose");
            }
        } else {
            mc.setModel("player_run/1");
            if (!vc.vel.setZ(0).equals(new Vec3())) {
                rc.rot = vc.vel.direction();
            }
        }

        //Wall run
        if ((cc.xHit != 0 || cc.yHit != 0) && sprinting) {
            if (cc.xHit != 0) {
                if (vc.vel.z < Math.abs(vc.vel.x)) {
                    vc.vel = vc.vel.setZ(Math.abs(vc.vel.x));
                }
            }
            if (cc.yHit != 0) {
                if (vc.vel.z < Math.abs(vc.vel.y)) {
                    vc.vel = vc.vel.setZ(Math.abs(vc.vel.y));
                }
            }
            sc.setState(PlayerWallrunState.class);
        }

        //Attack
        if (MouseInput.isPressed(0)) {
            sc.setState(PlayerAttackState.class);
        }
    }
}
