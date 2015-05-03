package level;

import core.AbstractSystem;
import static graphics.Graphics3D.drawSpriteFast;
import graphics.data.Texture;
import static graphics.loading.SpriteContainer.loadSprite;
import static org.lwjgl.opengl.GL11.*;
import util.Color4d;
import util.Util;
import util.Vec2;
import util.Vec3;

public class LevelSystem extends AbstractSystem {

    private LevelComponent lc;

    public LevelSystem(LevelComponent lc) {
        this.lc = lc;
    }

    @Override
    public void update() {
        double bottom = Util.screenPos(new Vec2(0, -1)).y;
        Vec3 UL = Util.screenPos(new Vec2(-1, 1));
        Vec3 UR = Util.screenPos(new Vec2(1, 1));

        glEnable(GL_TEXTURE_2D);

        Texture[] texList = {loadSprite("stone"), loadSprite("wood")};

        for (Texture tex : texList) {
            tex.bind();

            glBegin(GL_QUADS);

            for (int i = 0; i < lc.width; i++) {
                for (int j = 0; j < lc.height; j++) {
                    Tile t = lc.tileGrid[i][j];
                    if (t.tex != tex || j < bottom || j > UL.y + .5 || i < UL.x || i > UR.x + .5) {
                        continue;
                    }
                    new Color4d(.5 + t.height / 16, .5 + t.height / 16, .5 + t.height / 16).glColor();

                    drawSpriteFast(tex, t.LL(), t.LR(), t.UR(), t.UL(), new Vec3(0, 0, 1));

                    if (lc.heightAt(i - 1, j) < t.height) {
                        Vec3 down = new Vec3(0, 0, lc.heightAt(i - 1, j) - t.height);
                        drawSpriteFast(tex, t.UL(), t.LL(), t.LL().add(down), t.UL().add(down), new Vec3(-1, 0, 0));
                    }
                    if (lc.heightAt(i, j - 1) < t.height) {
                        Vec3 down = new Vec3(0, 0, lc.heightAt(i, j - 1) - t.height);
                        drawSpriteFast(tex, t.LL(), t.LR(), t.LR().add(down), t.LL().add(down), new Vec3(0, -1, 0));
                    }
                    if (lc.heightAt(i + 1, j) < t.height) {
                        Vec3 down = new Vec3(0, 0, lc.heightAt(i + 1, j) - t.height);
                        drawSpriteFast(tex, t.LR(), t.UR(), t.UR().add(down), t.LR().add(down), new Vec3(1, 0, 0));
                    }
                    if (lc.heightAt(i, j + 1) < t.height) {
                        Vec3 down = new Vec3(0, 0, lc.heightAt(i, j + 1) - t.height);
                        drawSpriteFast(tex, t.UR(), t.UL(), t.UL().add(down), t.UR().add(down), new Vec3(0, 1, 0));
                    }
                }
            }

            glEnd();
        }
    }
}
