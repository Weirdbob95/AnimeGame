package level;

import graphics.data.Texture;
import graphics.loading.SpriteContainer;
import util.Vec3;

public class Tile {

    public int x, y;
    public double height;
    public Texture tex;

    public Tile(int x, int y, double height) {
        this(x, y, height, "stone");
    }

    public Tile(int x, int y, double height, String tex) {
        this.x = x;
        this.y = y;
        this.height = 2 * height;
        this.tex = SpriteContainer.loadSprite(tex);
    }

    public Vec3 LL() {
        return new Vec3(x, y, height);
    }

    public Vec3 LR() {
        return new Vec3(x + 1, y, height);
    }

    public Vec3 UL() {
        return new Vec3(x, y + 1, height);
    }

    public Vec3 UR() {
        return new Vec3(x + 1, y + 1, height);
    }
}
