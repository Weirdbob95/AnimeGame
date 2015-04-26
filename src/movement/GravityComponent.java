package movement;

import core.AbstractComponent;
import core.Vec3;

public class GravityComponent extends AbstractComponent {

    public Vec3 g;

    public GravityComponent() {
        g = new Vec3(0, 0, -.01);
    }
}
