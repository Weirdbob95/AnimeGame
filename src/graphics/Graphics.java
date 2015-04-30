package graphics;

import core.Color4d;
import core.Vec3;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

public abstract class Graphics {

    public static void drawLine(Vec3 start, Vec3 end) {
        drawLine(start, end, Color4d.BLACK);
    }

    public static void drawLine(Vec3 start, Vec3 end, Color4d color) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glLineWidth(2);
        color.glColor();
        glBegin(GL_LINES);
        {
            glVertex3d(start.x, start.y, start.z);
            glVertex3d(end.x, end.y, end.z);
        }
        glEnd();
        glPopMatrix();
    }

    public static void drawSprite(Texture s, Vec3 pos, double hor, double ver, double tilt, double angle, Color4d color) {
        glPushMatrix();
        glEnable(GL_TEXTURE_2D);
        s.bind();

        color.glColor();
        glTranslated(pos.x, pos.y, pos.z);
        glRotated(angle, 0, 0, 1);
        glRotated(tilt, 1, 0, 0);

        glBegin(GL_QUADS);
        {
            glNormal3d(0, 0, 1);
            glTexCoord2d(0, s.getHeight());
            glVertex3d(0, 0, 0);
            glNormal3d(0, 0, 1);
            glTexCoord2d(s.getWidth(), s.getHeight());
            glVertex3d(hor, 0, 0);
            glNormal3d(0, 0, 1);
            glTexCoord2d(s.getWidth(), 0);
            glVertex3d(hor, ver, 0);
            glNormal3d(0, 0, 1);
            glTexCoord2d(0, 0);
            glVertex3d(0, ver, 0);
        }
        glEnd();
        glPopMatrix();
    }

    public static void drawText(String s, Vec3 pos) {
        drawText(s, "Default", pos, Color.black);
    }

    public static void drawText(String s, String font, Vec3 pos, Color c) {
        TextureImpl.bindNone();
        FontContainer.get(font).drawString((float) pos.x, (float) pos.y, s, c);
    }

    public static void fillRect(Vec3 pos, double hor, double ver, double tilt, double angle, Color4d color) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        color.glColor();
        glTranslated(pos.x, pos.y, pos.z);
        glRotated(angle, 0, 0, 1);
        glRotated(tilt, 1, 0, 0);
        double dir = Math.signum(hor * ver);
        glBegin(GL_QUADS);
        {
            glNormal3d(0, 0, dir);
            glVertex3d(0, 0, 0);
            glNormal3d(0, 0, dir);
            glVertex3d(hor, 0, 0);
            glNormal3d(0, 0, dir);
            glVertex3d(hor, ver, 0);
            glNormal3d(0, 0, dir);
            glVertex3d(0, ver, 0);
        }
        glEnd();
        glPopMatrix();
    }
}
