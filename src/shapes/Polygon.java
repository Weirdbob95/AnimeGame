package shapes;

import java.util.ArrayList;
import util.Vec2;

public class Polygon extends CollisionShape {

    public final int size;
    public final Vec2[] pointList;
    public AABB aabb;
    private final static double SMALL = .01;

    public Polygon(Vec2[] pointList) {
        this.pointList = pointList;
        size = pointList.length;
        update();
    }

    public Polygon(int vertices, double size) {
        this(vertices, size, false);
    }

    public Polygon(int vertices, double size, boolean pointRight) {
        pointList = new Vec2[vertices];
        double i = 0;
        if (!pointRight) {
            i = .5;
            size /= Math.cos(Math.PI / vertices);
        }
        for (; i < vertices; i++) {
            pointList[(int) i] = new Vec2(size * Math.cos(Math.PI * 2 * i / vertices), size * Math.sin(Math.PI * 2 * i / vertices));
        }
        this.size = pointList.length;
        update();
    }

    public boolean contains(Vec2 point) {
        for (Edge e : getEdgeList()) {
            if (point.subtract(e.p1).dot(e.getDisplacement()) > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AABB getAABB() {
        return aabb;
    }

    public ArrayList<Edge> getEdgeList() {
        ArrayList<Edge> r = new ArrayList();
        for (int i = 0; i < size; i++) {
            r.add(new Edge(pointList[i], pointList[(i + 1) % size]));
        }
        return r;
    }

    @Override
    public ArrayList<Vec2> getProjAxes(CollisionShape other) {
        ArrayList<Vec2> r = new ArrayList();
        for (Edge edge : getEdgeList()) {
            r.add(edge.getNormal());
        }
        return r;
    }

    @Override
    public ProjectionRange getProjRange(Vec2 axis) {
        ProjectionRange r = new ProjectionRange();
        r.max = r.min = pointList[0].dot(axis);
        r.max_low = r.max_high = r.min_low = r.min_high = pointList[0].dot(axis.normal());
        for (int i = 1; i < size; i++) {
            double x = pointList[i].dot(axis);
            if (x > r.max - SMALL && x < r.max + SMALL) {
                double y = pointList[i].dot(axis.normal());
                if (y > r.max_high) {
                    r.max_high = y;
                } else if (y < r.max_low) {
                    r.max_low = y;
                }
            } else if (x > r.max) {
                r.max = x;
                r.max_high = r.max_low = pointList[i].dot(axis.normal());
            }
            if (x > r.min - SMALL && x < r.min + SMALL) {
                double y = pointList[i].dot(axis.normal());
                if (y > r.min_high) {
                    r.min_high = y;
                } else if (y < r.min_low) {
                    r.min_low = y;
                }
            } else if (x < r.min) {
                r.min = x;
                r.min_high = r.min_low = pointList[i].dot(axis.normal());

            }
        }
        return r;
    }

    private void update() {
        Vec2 min = pointList[0];
        Vec2 max = pointList[0];
        for (int i = 1; i < size; i++) {
            if (pointList[i].x < min.x) {
                min = min.setX(pointList[i].x);
            }
            if (pointList[i].y < min.y) {
                min = min.setY(pointList[i].y);
            }
            if (pointList[i].x > max.x) {
                max = max.setX(pointList[i].x);
            }
            if (pointList[i].y > max.y) {
                max = max.setY(pointList[i].y);
            }
        }
        aabb = new AABB(min, max);
    }
}
