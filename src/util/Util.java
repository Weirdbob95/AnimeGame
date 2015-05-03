package util;

import core.Main;
import core.MouseInput;
import graphics.RenderManagerComponent;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public abstract class Util {

    public static FloatBuffer floatBuffer(double... vals) {
        FloatBuffer r = BufferUtils.createFloatBuffer(vals.length);
        for (double d : vals) {
            r.put((float) d);
        }
        r.flip();
        return r;
    }

    public static Vec3 mousePos() {
        return screenPos(MouseInput.mouse().divide(Main.gameManager.rmc.viewSize));
    }

    public static Vec3 screenPos(Vec2 screen) {
        RenderManagerComponent rmc = Main.gameManager.rmc;
        Vec3 viewDir = rmc.lookAt.subtract(rmc.pos).normalize();
        Vec3 viewHor = viewDir.cross(new Vec3(0, 0, 1));
        Vec3 viewVer = viewDir.cross(viewHor).reverse();
        double height = Math.tan(rmc.fov * Math.PI / 360) / viewHor.length();
        double width = height * rmc.aspectRatio();
        Vec3 LL = rmc.pos.add(viewDir).subtract(viewHor.multiply(width)).subtract(viewVer.multiply(height));
        Vec3 mouse3 = LL.add(viewHor.multiply(width * 2 * screen.x)).add(viewVer.multiply(height * 2 * screen.y));
        Vec3 toMouse = mouse3.subtract(rmc.pos);
        if (toMouse.z >= 0) {
            return null;
        }
        return rmc.pos.add(toMouse.multiply(-rmc.pos.z / toMouse.z));
    }
}
