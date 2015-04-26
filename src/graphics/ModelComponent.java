package graphics;

import core.AbstractComponent;
import core.Color4d;
import java.util.ArrayList;

public class ModelComponent extends AbstractComponent {

    public ArrayList<Model> modelList;
    public double animIndex;
    public double animSpeed;
    public boolean visible;
    public boolean shadow;
    public double scale;
    public Color4d color;

    public ModelComponent(double scale, Color4d color, String... names) {
        modelList = new ArrayList();
        setAnim(names);
        visible = true;
        shadow = true;
        this.scale = scale;
        this.color = color;
    }

    public Model getModel() {
        return modelList.get((int) animIndex % modelList.size());
    }

    public void setAnim(String... names) {
        for (String name : names) {
            modelList.add(ModelContainer.loadModel(name));
        }
    }
}
