package states;

import core.AbstractEntity;

public abstract class State {

    public AbstractEntity e;
    public StateComponent sc;

    public State(AbstractEntity e, StateComponent sc) {
        this.e = e;
        this.sc = sc;
    }

    public abstract void update();
}
