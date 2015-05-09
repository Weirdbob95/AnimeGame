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
    }

    @Override
    public void update() {
        double fall = vc.vel.z;
        vc.vel = new Vec3();

        //Walk or run
        double walkSpeed;
        if (Keys.isPressed(Keyboard.KEY_LSHIFT) && stc.stamina >= 20) {
            sprinting = true;
        }
        if (Keys.isReleased(Keyboard.KEY_LSHIFT) || stc.stamina <= 0) {
            sprinting = false;
        }

        if (sprinting) {
            stc.stamina -= .5;
            walkSpeed = .5;
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
            vc.vel = new Vec3(Math.cos(cac.t) * fSpeed + Math.sin(cac.t) * sSpeed,
                    Math.sin(cac.t) * fSpeed - Math.cos(cac.t) * sSpeed, 0).setLength(walkSpeed);
        }

        //Jumping
        if (Keys.isPressed(KEY_SPACE)) {
            if (cc.onGround()) {
                fall = .2;
            }
        }

        //Rotation and animation
        if (cc.onGround()) {
            if (!vc.vel.equals(new Vec3())) {
                rc.rot = vc.vel.direction();
                mc.setAnim("run", 10);
            } else {
                mc.setModel("player_actionpose");
            }
        } else {
            mc.setModel("char_dash");
            if (!vc.vel.equals(new Vec3())) {
                rc.rot = vc.vel.direction();
            }
        }

        //Attack
        if (MouseInput.isPressed(0)) {
            sc.setState(PlayerAttackState.class);
        }

        vc.vel = vc.vel.setZ(fall);
    }
}
