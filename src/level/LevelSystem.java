package level;

import core.AbstractSystem;
import static core.Color4d.WHITE;
import core.Vec3;
import graphics.Graphics;

public class LevelSystem extends AbstractSystem {

    private LevelComponent lc;

    public LevelSystem(LevelComponent lc) {
        this.lc = lc;
    }

    @Override
    public void update() {
        for (int i = 0; i < lc.size; i++) {
            for (int j = 0; j < lc.size; j++) {
                Tile t = lc.tileGrid[i][j];
                Graphics.fillRect(new Vec3(i, j, t.height), 1, 1, 0, 0, WHITE);
                if (i > 0) {
                    Tile t1 = lc.tileGrid[i - 1][j];
                    if (t1.height != t.height) {
                        Graphics.fillRect(new Vec3(i, j, t.height), 1, t1.height - t.height, 90, 90, WHITE);
                    }
                }
                if (j > 0) {
                    Tile t1 = lc.tileGrid[i][j - 1];
                    if (t1.height != t.height) {
                        Graphics.fillRect(new Vec3(i + 1, j, t.height), -1, t1.height - t.height, 90, 0, WHITE);
                    }
                }
            }
        }
    }
}
