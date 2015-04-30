package player;

import core.AbstractComponent;
import core.AbstractEntity;
import core.Main;
import util.Vec3;
import level.LevelComponent;
import movement.PositionComponent;

public class CollisionComponent extends AbstractComponent {

    public AbstractEntity ae;
    public PositionComponent pc;
    public double height;
    public double width;
    public boolean heightCentered;

    public CollisionComponent(AbstractEntity ae, PositionComponent pc, double height, double width, boolean heightCentered) {
        this.ae = ae;
        this.pc = pc;
        this.height = height;
        this.width = width;
        this.heightCentered = heightCentered;
    }

    public boolean open(Vec3 pos) {
        LevelComponent lc = Main.gameManager.getComponent(LevelComponent.class);
        for (int i = (int) (pos.x - width); i < pos.x + width; i++) {
            for (int j = (int) (pos.y - width); j < pos.y + width; j++) {
                if (i >= 0 && i < lc.size && j >= 0 && j < lc.size) {
                    if (lc.tileGrid[i][j].height > pos.z) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
