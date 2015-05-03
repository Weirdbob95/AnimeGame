package shapes;

import java.util.ArrayList;
import util.Vec2;

public class Circle extends CollisionShape {

    public Vec2 pos;
    public double radius;

    public Circle(Vec2 pos, double radius) {
        this.pos = pos;
        this.radius = radius;
    }

    @Override
    public AABB getAABB() {
        return new AABB(pos.subtract(new Vec2(radius, radius)), pos.add(new Vec2(radius, radius)));
    }

    @Override
    public ArrayList<Vec2> getProjAxes(CollisionShape other) {
        ArrayList<Vec2> a = new ArrayList();
        if (other instanceof Circle) {
            Circle c = (Circle) other;
            a.add(pos.subtract(c.pos));
        } else if (other instanceof Polygon) {
            Polygon p = (Polygon) other;
            Vec2 point = p.pointList[0];
            for (Vec2 v : p.pointList) {
                if (v.subtract(pos).lengthSquared() < point.subtract(pos).lengthSquared()) {
                    point = v;
                }
            }
            a.add(pos.subtract(point).reverse());
        }
        return a;
    }

    @Override
    public ProjectionRange getProjRange(Vec2 axis) {
        double x = pos.dot(axis);
        double y = pos.dot(axis.normal());
        ProjectionRange r = new ProjectionRange();
        r.max = x + radius;
        r.min = x - radius;
        r.max_high = r.max_low = r.min_high = r.min_low = y;
        return r;
    }

}
