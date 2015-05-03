package collisions;

import core.Main;
import java.util.ArrayList;
import shapes.Circle;
import shapes.CollisionShape;
import util.Vec3;

public abstract class CollisionUtil {

    public static ArrayList<CollisionComponent> listAt(Vec3 point) {
        ArrayList<CollisionComponent> r = new ArrayList();
        for (CollisionComponent cc : Main.gameManager.elc.getComponentList(CollisionComponent.class)) {
            Vec3 diff = point.subtract(cc.pc.pos);
            if (diff.z > 0 && diff.z < cc.height && diff.setZ(0).lengthSquared() < cc.width * cc.width) {
                r.add(cc);
            }
        }
        return r;
    }

    public static ArrayList<CollisionComponent> listAt(CollisionShape shape) {
        ArrayList<CollisionComponent> r = new ArrayList();
        for (CollisionComponent cc : Main.gameManager.elc.getComponentList(CollisionComponent.class)) {
            Circle c = new Circle(cc.pc.pos.toVec2(), cc.width);
            if (shape.intersects(c)) {
                r.add(cc);
            }
        }
        return r;
    }
}
