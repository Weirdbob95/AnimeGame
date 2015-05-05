package player;

import core.AbstractSystem;
import graphics.Graphics2D;
import util.Color4d;
import util.Vec2;

public class StaminaSystem extends AbstractSystem {

    private StaminaComponent sc;

    public StaminaSystem(StaminaComponent sc) {
        this.sc = sc;
    }

    @Override
    protected int getLayer() {
        return 2;
    }

    @Override
    public void update() {
        sc.stamina += sc.staminaRegen;
        if (sc.stamina > sc.maxStamina) {
            sc.stamina = sc.maxStamina;
        }
        Graphics2D.fillRect(new Vec2(10, 10), new Vec2(200, 20), Color4d.BLACK);
        Graphics2D.fillRect(new Vec2(10, 10), new Vec2(200 * sc.stamina / sc.maxStamina, 20), Color4d.GREEN);
    }

}
