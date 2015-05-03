package enemies;

import core.AbstractComponent;

public class EnemyHealthComponent extends AbstractComponent {

    public double health;
    public double maxHealth;

    public EnemyHealthComponent(double maxHealth) {
        this.maxHealth = maxHealth;
        health = maxHealth;
    }
}
