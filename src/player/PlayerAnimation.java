package player;

import graphics.data.Animation;

enum PlayerAnimation implements Animation {

    ATTACK("attack", 8, 2, .5),
    CHARGING("run", 10, 1, .2),
    RUN("run", 10, 0, .5),
    STAND("player_actionpose", 0, 0);

    private final int length;
    private final String name;
    private final double priority;
    private final double speed;

    private PlayerAnimation(String name, double priority, double speed) {
        this(name, -1, priority, speed);
    }

    private PlayerAnimation(String name, int length, double priority, double speed) {
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
