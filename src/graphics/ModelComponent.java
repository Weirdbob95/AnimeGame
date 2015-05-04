package graphics;

import core.AbstractComponent;
import graphics.data.Animation;
import graphics.data.Model;
import graphics.data.Texture;
import graphics.loading.ModelContainer;
import java.util.ArrayList;
import util.Color4d;

public class ModelComponent extends AbstractComponent {

    public String name;
    public Animation anim;
    public ArrayList<Model> modelList;
    public Texture tex;
    public double animIndex;
    public double animSpeed;
    public boolean visible;
    public boolean shadow;
    public double scale;
    public Color4d color;

    public ModelComponent(Animation anim, double scale, Color4d color) {
        modelList = new ArrayList();
        setAnim(anim);
        visible = true;
        shadow = true;
        this.scale = scale;
        this.color = color;
    }

    public ModelComponent(String name, double scale, Color4d color) {
        modelList = new ArrayList();
        setModel(name);
        visible = true;
        shadow = true;
        this.scale = scale;
        this.color = color;
    }

    public boolean animComplete() {
        return animIndex >= modelList.size();
    }

    public void attemptAnim(Animation anim) {
        if (animComplete() || this.anim.getPriority() <= anim.getPriority()) {
            setAnim(anim);
        }
    }

    public Model getModel() {
        return modelList.get((int) animIndex % modelList.size());
    }

    private void setAnim(Animation anim) {
        if (this.anim == anim) {
            animIndex = animIndex % modelList.size();
            return;
        }
        name = anim.getName();
        this.anim = anim;
        animIndex = 0;
        animSpeed = anim.getSpeed();
        modelList.clear();
        if (anim.getLength() == -1) {
            modelList.add(ModelContainer.loadModel(name));
        } else {
            for (int i = 1; i <= anim.getLength(); i++) {
                modelList.add(ModelContainer.loadModel(name + "/" + i));
            }
        }
    }

    public void setModel(String name) {
        this.name = name;
        anim = null;
        animIndex = 0;
        animSpeed = 0;
        modelList.clear();
        modelList.add(ModelContainer.loadModel(name));
    }
}
