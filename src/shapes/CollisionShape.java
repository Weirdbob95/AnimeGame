package shapes;

import java.util.ArrayList;
import util.Vec2;

public abstract class CollisionShape {

    public abstract AABB getAABB();

    public abstract ArrayList<Vec2> getProjAxes(CollisionShape other);

    public abstract ProjectionRange getProjRange(Vec2 axis);

    public boolean intersects(CollisionShape other) {
        if (!getAABB().intersects(other.getAABB())) {
            return false;
        }
        //Get all the axes to check along
        ArrayList<Vec2> projAxes = getProjAxes(other);
        //Reverse because the normals should p1 the direction for obj2 to travel
        for (int i = 0; i < projAxes.size(); i++) {
            projAxes.set(i, projAxes.get(i).reverse());
        }
        projAxes.addAll(other.getProjAxes(this));
        //Check each axis
        for (Vec2 axis : projAxes) {
            axis = axis.normalize();
            //Get the projected range for each object along the axis
            ProjectionRange pr1 = getProjRange(axis);
            ProjectionRange pr2 = other.getProjRange(axis);
            //Check how much the ranges are penetrating
            double depth = pr1.intersection(pr2);
            //If they are not penetrating, the objects aren't colliding
            if (depth > 0) {
                return true;
            }
        }
        return false;
    }
}
