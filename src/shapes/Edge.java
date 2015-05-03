package shapes;

import java.util.ArrayList;
import java.util.Arrays;
import util.Vec2;

public class Edge extends CollisionShape {

    public Vec2 p1;
    public Vec2 p2;

    public Edge(Vec2 p1, Vec2 p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public AABB getAABB() {
        return new AABB(new Vec2(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y)), new Vec2(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y)));
    }

    public Vec2 getDisplacement() {
        return p2.subtract(p1);
    }

    public Vec2 getNormal() {
        return getDisplacement().normal();
    }

    @Override
    public ArrayList<Vec2> getProjAxes(CollisionShape other) {
        return new ArrayList(Arrays.asList(getNormal()));
    }

    @Override
    public ProjectionRange getProjRange(Vec2 axis) {
        ProjectionRange r = new ProjectionRange();
        r.max = r.min = p1.dot(axis);
        r.max_low = r.max_high = r.min_low = r.min_high = p1.dot(axis.normal());
        double x = p2.dot(axis);
        if (x >= r.max) {
            r.max = x;
            double y = p2.dot(axis.normal());
            if (y > r.max_high) {
                r.max_high = y;
            } else if (y < r.max_low) {
                r.max_low = y;
            }
        }
        if (x <= r.min) {
            r.min = x;
            double y = p2.dot(axis.normal());
            if (y > r.min_high) {
                r.min_high = y;
            } else if (y < r.min_low) {
                r.min_low = y;
            }
        }
        return r;
    }
}
