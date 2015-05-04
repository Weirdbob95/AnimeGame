package enemies;

import core.AbstractComponent;
import util.Vec3;

public class EnemyPathingComponent extends AbstractComponent {

    public Vec3 post;
    public Vec3 dest;

    public EnemyPathingComponent(Vec3 post) {
        this.post = post;
        dest = post;
    }
}
