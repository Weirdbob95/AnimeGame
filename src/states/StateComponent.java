package states;

import core.AbstractComponent;
import core.AbstractEntity;

public class StateComponent extends AbstractComponent {

    public AbstractEntity e;
    public State state;

    public StateComponent(AbstractEntity e, Class<? extends State> c) {
        this.e = e;
        setState(c);
    }

    public void setState(Class<? extends State> c) {
        if (!c.isInstance(state)) {
            try {
                state = c.getConstructor(AbstractEntity.class, StateComponent.class).newInstance(e, this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
