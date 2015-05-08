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
        try {
            state = c.getConstructor(AbstractEntity.class).newInstance(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
