package level;

import core.AbstractComponent;

public class LevelComponent extends AbstractComponent {

    public Tile[][] tileGrid;
    public int size;

    public LevelComponent(int size) {
        this.size = size;
        tileGrid = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tileGrid[i][j] = new Tile();
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    tileGrid[i][j].height = 1;
                }
            }
        }
    }
}
