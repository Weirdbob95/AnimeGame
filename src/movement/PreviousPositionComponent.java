package movement;

import core.AbstractComponent;
import util.Vec3;

public class PreviousPositionComponent extends AbstractComponent {

    public Vec3 pos;

    public PreviousPositionComponent(Vec3 pos) {
        this.pos = pos;
    }
}
