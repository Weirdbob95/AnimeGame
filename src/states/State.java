package states;

import core.AbstractEntity;

public abstract class State {

    public AbstractEntity e;

    public State(AbstractEntity e) {
        this.e = e;
    }

    public void update() {
    }
}
