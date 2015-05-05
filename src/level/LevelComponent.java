package level;

import core.AbstractComponent;
import enemies.Enemy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import player.Player;
import util.Vec3;

public class LevelComponent extends AbstractComponent {

    public Tile[][] tileGrid;
    public int width;
    public int height;
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
    }

    private Tile createTile(int x, int y, int color) {
        switch (color) {
            case 0xFF000000: //0 0 0
                return new Tile(x, y, 4, "wood");
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
