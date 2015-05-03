package graphics.data;

public enum Animation {

    ATTACK("attack", 9, false, .5),
    RUN("run", 10, true, .5),
    STAND("player_actionpose", true, 0);

    public final String name;
    public final int length;
    public final boolean canMove;
    public final double speed;

    private Animation(String name, boolean canMove, double speed) {
        this(name, -1, canMove, speed);
    }

    private Animation(String name, int length, boolean canMove, double speed) {
        this.name = name;
        this.length = length;
        this.canMove = canMove;
        this.speed = speed;
    }
}
