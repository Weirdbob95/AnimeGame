package player;

import core.AbstractSystem;
import core.Color4d;
import core.Vec2;
import graphics.Graphics2D;

public class PlayerHealthSystem extends AbstractSystem {

    private PlayerHealthComponent phc;

    public PlayerHealthSystem(PlayerHealthComponent phc) {
        this.phc = phc;
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void update() {
        phc.health -= phc.damage * .1;
        phc.damage *= .9;
        double amt = phc.damage / 100;

        Graphics2D.fillRect(new Vec2(0, 0), new Vec2(1920, 1080), Color4d.RED.setA(amt));
    }

}
