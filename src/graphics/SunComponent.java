package graphics;

import core.AbstractComponent;
import static util.Util.floatBuffer;
import util.Vec3;
import util.Vec3Polar;
import static org.lwjgl.opengl.GL11.*;

public class SunComponent extends AbstractComponent {

    private double theta;
    private double phi;

    public SunComponent() {
        theta = -Math.PI / 4;
        phi = Math.PI / 3;
        Vec3 pos = new Vec3Polar(1, theta, phi).toRect();

        glLight(GL_LIGHT0, GL_POSITION, floatBuffer(pos.x, pos.y, pos.z, 0));
        glLight(GL_LIGHT0, GL_SPECULAR, floatBuffer(1, 1, 1, 1));
        glLight(GL_LIGHT0, GL_DIFFUSE, floatBuffer(.5, .5, .5, 1));
        glEnable(GL_LIGHT0);
    }

    public double getTheta() {
        return theta;
    }

    public double getPhi() {
        return phi;
    }

    public void setTheta(double theta) {
        this.theta = theta;
        Vec3 pos = new Vec3Polar(1, theta, phi).toRect();
        glLight(GL_LIGHT0, GL_POSITION, floatBuffer(pos.x, pos.y, pos.z, 0));
    }

    public void setPhi(double phi) {
        this.phi = phi;
        Vec3 pos = new Vec3Polar(1, theta, phi).toRect();
        glLight(GL_LIGHT0, GL_POSITION, floatBuffer(pos.x, pos.y, pos.z, 0));
    }
}
