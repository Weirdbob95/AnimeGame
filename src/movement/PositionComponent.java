package movement;

import core.AbstractComponent;
import util.Vec3;

public class PositionComponent extends AbstractComponent {

    public Vec3 pos;

    public PositionComponent() {
        this(new Vec3());
    }

    public PositionComponent(Vec3 pos) {
        this.pos = pos;
    }

}
