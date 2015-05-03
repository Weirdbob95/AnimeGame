package level;

import core.AbstractEntity;

public class Level extends AbstractEntity {

    public Level(String name) {
        LevelComponent lc = add(new LevelComponent(name));
        add(new LevelSystem(lc));
    }
}
