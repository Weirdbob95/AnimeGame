package graphics;

import core.AbstractComponent;
import static core.Util.floatBuffer;
import core.Vec3;
import core.Vec3Polar;
import static org.lwjgl.opengl.GL11.*;

public class SunComponent extends AbstractComponent {

    public double theta;
    public double phi;

    public SunComponent() {
        theta = -Math.PI / 4;
        phi = Math.PI / 3;
        Vec3 pos = new Vec3Polar(1, theta, phi).toRect();

        glLight(GL_LIGHT0, GL_POSITION, floatBuffer(pos.x, pos.y, pos.z, 0));
        glLight(GL_LIGHT0, GL_SPECULAR, floatBuffer(1, 1, 1, 1));
        glLight(GL_LIGHT0, GL_DIFFUSE, floatBuffer(1, 1, 1, 1));									// enables lighting
        glEnable(GL_LIGHT0);
    }

}
