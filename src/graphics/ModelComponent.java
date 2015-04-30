package graphics;

import graphics.data.Animation;
import graphics.data.Model;
import graphics.loading.ModelContainer;
import core.AbstractComponent;
import util.Color4d;
import java.util.ArrayList;

public class ModelComponent extends AbstractComponent {

    public String name;
    public Animation anim;
    public ArrayList<Model> modelList;
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

    public Model getModel() {
        return modelList.get((int) animIndex % modelList.size());
    }

    public void setAnim(Animation anim) {
        if (this.anim == anim) {
            return;
        }
        name = anim.name;
        this.anim = anim;
        animIndex = 0;
        animSpeed = anim.speed;
        modelList.clear();
        if (anim.length == -1) {
            modelList.add(ModelContainer.loadModel(name));
        } else {
            for (int i = 1; i <= anim.length; i++) {
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
