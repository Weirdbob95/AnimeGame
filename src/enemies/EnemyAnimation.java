package enemies;

import graphics.data.Animation;

public enum EnemyAnimation implements Animation {

    ATTACK("attack", 9, 2, .15),
    FLINCH("char_actionpose", 10, .05),
    RUN("run", 10, 0, .25),
    STAND("player_actionpose", 0, 0);

    private final int length;
    private final String name;
    private final double priority;
    private final double speed;

    private EnemyAnimation(String name, double priority, double speed) {
        this(name, -1, priority, speed);
    }

    private EnemyAnimation(String name, int length, double priority, double speed) {
        this.length = length;
        this.name = name;
        this.priority = priority;
        this.speed = speed;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public double getSpeed() {
        return speed;
    }
}
