package graphics;

import core.AbstractComponent;
import util.Vec2;
import util.Vec3;

public class RenderManagerComponent extends AbstractComponent {

    public Vec2 viewSize;
    public boolean startFullscreen;
    public Vec3 pos, lookAt;
    public double fov;
    public static final Vec3 UP = new Vec3(0, 0, 1);

    public RenderManagerComponent() {
        viewSize = new Vec2(1920, 1080);
        startFullscreen = true;

        pos = new Vec3(25, 15, 5);
        lookAt = new Vec3(0, 1, 5);
        fov = 70;
    }

    public double aspectRatio() {
        return viewSize.x / viewSize.y;
    }
}
