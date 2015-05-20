package states;

import collisions.CollisionComponent;
import core.AbstractEntity;
import core.Keys;
import graphics.ModelComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import org.lwjgl.input.Keyboard;
import player.CameraComponent;
import player.StaminaComponent;
import util.Vec2;
import util.Vec3;
import util.Vec3Polar;

public class PlayerWallrunState extends State {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;
    private StaminaComponent stc;
    private CameraComponent cac;
    public double onWall;

    public PlayerWallrunState(AbstractEntity e, StateComponent sc) {
        super(e, sc);
        pc = e.getComponent(PositionComponent.class);
        vc = e.getComponent(VelocityComponent.class);
        rc = e.getComponent(RotationComponent.class);
        cc = e.getComponent(CollisionComponent.class);
        mc = e.getComponent(ModelComponent.class);
        stc = e.getComponent(StaminaComponent.class);
        cac = e.getComponent(CameraComponent.class);

        onWall = -1;
        pc.pos = pc.pos.add(new Vec2(cc.xHit, cc.yHit).multiply(.1).toVec3());
    }

    @Override
    public void update() {
        stc.stamina = stc.maxStamina;
        //End wall run
        if (Keys.isPressed(Keyboard.KEY_LSHIFT) || (cc.onGround() && vc.vel.z < 0)) {
            sc.setState(PlayerWalkingState.class);
        }

        //Collisions
        if (cc.xHit != 0 || cc.yHit != 0) {
            pc.pos = pc.pos.add(new Vec2(cc.xHit, cc.yHit).multiply(.1).toVec3());
            onWall = new Vec2(cc.xHit, cc.yHit).direction();
            //stc.stamina -= 1;
            //Go up
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
            stc.stamina -= vc.vel.z * 15;
            if (stc.stamina <= 0) {
                sc.setState(PlayerWalkingState.class);
            }
        } else {
            onWall = -1;
            //sc.setState(PlayerWalkingState.class);
        }

        //WASD
        double walkSpeed = .2;
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

        if (onWall == -1) {
            if (fSpeed != 0 || sSpeed != 0) {
                Vec3 nv = new Vec3(Math.cos(cac.t) * fSpeed + Math.sin(cac.t) * sSpeed,
                        Math.sin(cac.t) * fSpeed - Math.cos(cac.t) * sSpeed, 0).setLength(walkSpeed).setZ(vc.vel.z);
                vc.vel = vc.vel.add(nv.subtract(vc.vel).setLength(.0095));
            }
//            else {
//                vc.vel = new Vec3(0, 0, vc.vel.z);
//            }
        } else {
            stc.stamina -= stc.staminaRegen;
            double walkAngle = cac.t - onWall;
            double f = fSpeed;
            fSpeed = Math.cos(walkAngle) * fSpeed + Math.sin(walkAngle) * sSpeed;
            sSpeed = -Math.sin(walkAngle) * f + Math.cos(walkAngle) * sSpeed;
            if (fSpeed != 0 || sSpeed != 0) {
                vc.vel = new Vec3(Math.sin(onWall) * sSpeed, -Math.cos(onWall) * sSpeed, fSpeed);
            } else {
                vc.vel = new Vec3();
            }
        }

        //Jump off
        if (onWall != -1) {
            if (Keys.isPressed(Keyboard.KEY_SPACE)) {
                vc.vel = new Vec3Polar(.4, onWall + Math.PI, Math.PI / 4).toRect();
            }
        }

        //Rotation and animation
        mc.setModel("player_actionpose");
        if (onWall != -1) {
            rc.rot = Math.PI + onWall;
        } else if (!vc.vel.setZ(0).equals(new Vec3())) {
            rc.rot = vc.vel.direction();
        }
    }
}
