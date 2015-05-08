package states;

import collisions.CollisionComponent;
import core.AbstractEntity;
import core.Keys;
import core.MouseInput;
import static enemies.EnemyAnimation.STAND;
import graphics.ModelComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.KEY_SPACE;
import player.CameraComponent;
import player.StaminaComponent;
import util.Util;
import util.Vec3;

public class WalkingState extends State {

    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private CollisionComponent cc;
    private ModelComponent mc;
    private StaminaComponent sc;
    private CameraComponent cac;

    public WalkingState(AbstractEntity e) {
        super(e);
        pc = e.getComponent(PositionComponent.class);
        vc = e.getComponent(VelocityComponent.class);
        rc = e.getComponent(RotationComponent.class);
        cc = e.getComponent(CollisionComponent.class);
        mc = e.getComponent(ModelComponent.class);
        sc = e.getComponent(StaminaComponent.class);
        cac = e.getComponent(CameraComponent.class);
    }

    @Override
    public void update() {
        double fall = vc.vel.z;
        vc.vel = new Vec3();
        //WASD
//        if (Keys.isDown(Keyboard.KEY_W)) {
//            vc.vel = vc.vel.add(new Vec3(0, .2, 0));
//        }
//        if (Keys.isDown(Keyboard.KEY_A)) {
//            vc.vel = vc.vel.add(new Vec3(-.2, 0, 0));
//        }
//        if (Keys.isDown(Keyboard.KEY_S)) {
//            vc.vel = vc.vel.add(new Vec3(0, -.2, 0));
//        }
//        if (Keys.isDown(Keyboard.KEY_D)) {
//            vc.vel = vc.vel.add(new Vec3(.2, 0, 0));
//        }
//        if (vc.vel.lengthSquared() > .04) {
//            vc.vel.setLength(.2);
//        }
        
        double fSpeed = 0, sSpeed = 0;
        if (Keys.isDown(Keyboard.KEY_W)) {
            fSpeed += .2;
        }
        if (Keys.isDown(Keyboard.KEY_S)) {
            fSpeed -= .2;
        }
        if (Keys.isDown(Keyboard.KEY_A)) {
            sSpeed -= .2;
        }
        if (Keys.isDown(Keyboard.KEY_D)) {
            sSpeed += .2;
        }
        vc.vel = new Vec3(Math.cos(cac.t) * fSpeed + Math.sin(cac.t) * sSpeed,
                Math.sin(cac.t) * fSpeed - Math.cos(cac.t) * sSpeed, 0);

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
    }
}
