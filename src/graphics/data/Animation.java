package graphics.data;

public enum Animation {

    ATTACK("attack", 21, false, .3),
    RUN("run", 10, true, .5),
    STAND("player_actionpose", true, 0);

    public String name;
    public int length;
    public boolean canMove;
    public double speed;

    private Animation(String name, int length, boolean canMove, double speed) {
        this.name = name;
        this.length = length;
        this.canMove = canMove;
        this.speed = speed;
    }

    private Animation(String name, boolean canMove, double speed) {
        this.name = name;
        this.length = -1;
        this.canMove = canMove;
        this.speed = speed;
    }
}
