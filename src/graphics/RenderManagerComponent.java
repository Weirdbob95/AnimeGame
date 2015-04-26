package graphics;

import core.AbstractComponent;
import core.Vec3;

public class RenderManagerComponent extends AbstractComponent {

    public int displayWidth, displayHeight;
    public boolean startFullscreen;
    public int viewWidth, viewHeight;
    public Vec3 pos, lookAt;
    public double fov;
    public static final Vec3 UP = new Vec3(0, 0, 1);

    public RenderManagerComponent() {
        displayWidth = viewWidth = 1920;
        displayHeight = viewHeight = 1080;
        startFullscreen = true;

        pos = new Vec3(25, 15, 5);
        lookAt = new Vec3(0, 1, 5);
        fov = 70;
    }
}
