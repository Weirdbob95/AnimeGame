package shapes;

import util.Vec2;

public class AABB {

    public Vec2 p1;
    public Vec2 p2;

    public AABB(Vec2 p1, Vec2 p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean contains(double x, double y) {
        return p1.x < x && p1.y < y && x < p2.x && y < p2.y;
    }

    public boolean contains(double x, double y, double buffer) {
        return p1.x - buffer < x && p1.y - buffer < y && x - buffer < p2.x && y - buffer < p2.y;
    }

    public boolean intersects(AABB other) {
        return p1.x < other.p2.x && p1.y < other.p2.y && p2.x > other.p1.x && p2.y > other.p1.y;
    }
}
