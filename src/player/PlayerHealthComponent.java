package player;

import core.AbstractComponent;

public class PlayerHealthComponent extends AbstractComponent {

    public double health;
    public double maxHealth;
    public double damage;

    public PlayerHealthComponent(double maxHealth) {
        this.maxHealth = maxHealth;
        health = maxHealth;
    }
}
