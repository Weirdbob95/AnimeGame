package player;

import core.AbstractComponent;

public class StaminaComponent extends AbstractComponent {
    public double stamina;
    public double maxStamina;
    public double staminaRegen;
    
    public StaminaComponent(double maxStamina) {
        this.maxStamina = maxStamina;
        stamina = maxStamina;
        staminaRegen = .1;
    }
}
