package states;

import core.AbstractSystem;

public class StateSystem extends AbstractSystem {

    public StateComponent sc;

    public StateSystem(StateComponent sc) {
        this.sc = sc;
    }

    @Override
    public void update() {
        sc.state.update();
    }
}
