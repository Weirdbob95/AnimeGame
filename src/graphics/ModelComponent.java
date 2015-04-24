package graphics;

import core.AbstractComponent;
import core.Color4d;

public class ModelComponent extends AbstractComponent {

    public Model model;
    public double scale;
    public Color4d color;

    public ModelComponent(String name, double scale) {
        model = ModelContainer.loadModel(name);
        this.scale = scale;
        color = Color4d.WHITE;
    }
}
