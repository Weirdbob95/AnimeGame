package level;

import core.AbstractComponent;
import enemies.Enemy;
import static graphics.Graphics3D.drawSpriteFast;
import graphics.data.Texture;
import static graphics.loading.SpriteContainer.loadSprite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static org.lwjgl.opengl.GL11.*;
import player.Player;
import util.Color4d;
import util.Vec3;

public class LevelComponent extends AbstractComponent {

    public Tile[][] tileGrid;
    public int width;
    public int height;
    public int list;
    private static String path = "levels/";
    private static String type = ".png";

    public LevelComponent(String name) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path + name + type));
        } catch (IOException ex) {
        }
        width = image.getWidth();
        height = image.getHeight();
        tileGrid = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileGrid[x][y] = createTile(x, y, image.getRGB(x, height - y - 1));
            }
        }

        //Prerender
        list = glGenLists(1);
        glNewList(list, GL_COMPILE);
        //Grid
//        for (int i = 0; i < lc.width; i++) {
//            Graphics3D.drawLine(new Vec3(i, 0, .01), new Vec3(i, lc.height, .01));
//        }
//        for (int i = 0; i < lc.height; i++) {
//            Graphics3D.drawLine(new Vec3(0, i, .01), new Vec3(lc.width, i, .01));
//        }
        glEnable(GL_TEXTURE_2D);
        Texture[] texList = {loadSprite("stone"), loadSprite("wood2")};

        for (Texture tex : texList) {
            tex.bind();
            glBegin(GL_QUADS);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Tile t = tileGrid[i][j];
                    if (t.tex != tex) {
                        continue;
                    }
                    new Color4d(.5 + t.height / 16, .5 + t.height / 16, .5 + t.height / 16).glColor();

                    drawSpriteFast(tex, t.LL(), t.LR(), t.UR(), t.UL(), new Vec3(0, 0, 1));

                    if (heightAt(i - 1, j) < t.height) {
                        for (int k = 0; k > heightAt(i - 1, j) - t.height; k--) {
                            drawSpriteFast(tex, t.UL().add(new Vec3(0, 0, k)), t.LL().add(new Vec3(0, 0, k)),
                                    t.LL().add(new Vec3(0, 0, k - 1)), t.UL().add(new Vec3(0, 0, k - 1)), new Vec3(-1, 0, 0));
                        }
                    }
                    if (heightAt(i, j - 1) < t.height) {
                        for (int k = 0; k > heightAt(i, j - 1) - t.height; k--) {
                            drawSpriteFast(tex, t.LL().add(new Vec3(0, 0, k)), t.LR().add(new Vec3(0, 0, k)),
                                    t.LR().add(new Vec3(0, 0, k - 1)), t.LL().add(new Vec3(0, 0, k - 1)), new Vec3(0, -1, 0));
                        }
                    }
                    if (heightAt(i + 1, j) < t.height) {
                        for (int k = 0; k > heightAt(i + 1, j) - t.height; k--) {
                            drawSpriteFast(tex, t.LR().add(new Vec3(0, 0, k)), t.UR().add(new Vec3(0, 0, k)),
                                    t.UR().add(new Vec3(0, 0, k - 1)), t.LR().add(new Vec3(0, 0, k - 1)), new Vec3(1, 0, 0));
                        }
                    }
                    if (heightAt(i, j + 1) < t.height) {
                        for (int k = 0; k > heightAt(i, j + 1) - t.height; k--) {
                            drawSpriteFast(tex, t.UR().add(new Vec3(0, 0, k)), t.UL().add(new Vec3(0, 0, k)),
                                    t.UL().add(new Vec3(0, 0, k - 1)), t.UR().add(new Vec3(0, 0, k - 1)), new Vec3(0, 1, 0));
                        }
                    }
                }
            }
            glEnd();
        }
        glEndList();
    }

    private Tile createTile(int x, int y, int color) {
        switch (color) {
            case 0xFF000000: //0 0 0
                return new Tile(x, y, 4, "wood2");
            case 0xFF7F7F7F: //127 127 127
                return new Tile(x, y, 3);
            case 0xFFC3C3C3: //185 185 185
                return new Tile(x, y, 2);
            case 0xFFB97A57: //185 122 87
                return new Tile(x, y, 1);
            case 0xFFFFFFFF:
                return new Tile(x, y, 0);
            case 0xFFFF7F27:
                new Enemy(new Vec3(x + .5, y + .5, 0));
                return new Tile(x, y, 0);
            case 0xFF00A2E8:
                new Player(new Vec3(x + .5, y + .5, 0));
                return new Tile(x, y, 0);
        }
        return new Tile(x, y, 10);
    }

    public double heightAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return 10;
        }
        return tileGrid[x][y].height;
    }
}
