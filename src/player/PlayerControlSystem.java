package player;

import core.*;
import graphics.ModelComponent;
import graphics.RenderManagerComponent;
import movement.PositionComponent;
import movement.RotationComponent;
import movement.VelocityComponent;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.KEY_SPACE;

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

    private Vec3 mousePos() {
        RenderManagerComponent rmc = Main.gameManager.rmc;
        Vec2 mouse2 = MouseInput.mouse().divide(new Vec2(rmc.viewWidth, rmc.viewHeight));
        Vec3 viewDir = rmc.lookAt.subtract(rmc.pos).normalize();
        Vec3 viewHor = viewDir.cross(new Vec3(0, 0, 1));
        Vec3 viewVer = viewDir.cross(viewHor).reverse();
        double height = Math.tan(rmc.fov * Math.PI / 360) * Math.sqrt(2); //The sqrt(2) doesn't make any sense, it should be a 1, but it works
        double width = height * rmc.viewWidth / rmc.viewHeight;
        Vec3 LL = rmc.pos.add(viewDir).subtract(viewHor.multiply(width)).subtract(viewVer.multiply(height));
        Vec3 mouse3 = LL.add(viewHor.multiply(width * 2 * mouse2.x)).add(viewVer.multiply(height * 2 * mouse2.y));
        Vec3 toMouse = mouse3.subtract(rmc.pos);
        if (toMouse.z >= 0) {
            return null;
        }
        return rmc.pos.add(toMouse.multiply(-rmc.pos.z / toMouse.z));
    }

    @Override
    public void update() {
        double fall = vc.vel.z;
        vc.vel = new Vec3();
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

        if (MouseInput.isDown(0)) {
            Vec3 mousePos = mousePos();
            if (mousePos != null) {
                vc.vel = mousePos.subtract(pc.pos).setLength(.2);
            }
        }

        if (MouseInput.isDown(1)) {
            Vec3 mousePos = mousePos();
            if (mousePos != null) {
                vc.vel = mousePos.subtract(pc.pos).setLength(.5);
            }
        }

        if (!vc.vel.equals(new Vec3())) {
            rc.rot = vc.vel.direction();
            mc.setAnim(10, "run");
            mc.animSpeed = .5;
        } else {
            mc.setAnim("player_actionpose");
            mc.animSpeed = 0;
        }

        if (Keys.isDown(KEY_SPACE)) {
            if (!cc.open(pc.pos.add(new Vec3(0, 0, -.1)))) {
                fall = .2;
            }
        }

        vc.vel = vc.vel.setZ(fall);
    }

}
