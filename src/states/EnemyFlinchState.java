package states;

import core.AbstractEntity;
import graphics.ModelComponent;

public class EnemyFlinchState extends State {

    private ModelComponent mc;

    public EnemyFlinchState(AbstractEntity e, StateComponent sc) {
        super(e, sc);
        mc = e.getComponent(ModelComponent.class);

        mc.setModel("char_actionpose");
        mc.animSpeed = .05;
    }

    @Override
    public void update() {
        if (mc.animComplete()) {
            sc.setState(EnemyWalkingState.class);
        }
    }

}
