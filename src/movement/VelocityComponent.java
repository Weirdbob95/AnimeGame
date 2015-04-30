package movement;

import core.AbstractComponent;
import util.Vec3;

public class VelocityComponent extends AbstractComponent {

    public Vec3 vel;

    public VelocityComponent() {
        vel = new Vec3();
    }
}
