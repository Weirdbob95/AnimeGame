package player;

import core.AbstractSystem;
import core.Main;
import graphics.Graphics2D;
import util.Color4d;
import util.Vec2;

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
        double amt = phc.damage / 20;

        Graphics2D.fillRect(new Vec2(), Main.gameManager.rmc.viewSize, Color4d.RED.setA(amt));

        if (phc.health <= 0) {
            System.exit(0);
        }
    }

}
