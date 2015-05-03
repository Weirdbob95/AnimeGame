package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import level.Level;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class Main {

    public static void main(String[] args) throws IOException {
        System.setProperty("org.lwjgl.librarypath", new File("../../LWJGL/native/windows").getAbsolutePath());
        try {
            init();
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
        }
        System.exit(0);
    }

    public static final int speed = 60;
    public static final int LAYERS = 3;
    public static ArrayList<ArrayList<AbstractSystem>> systems;
    public static GameManager gameManager;
    public static boolean paused = false;

    public static void destroy() {
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    public static void init() throws LWJGLException {
        systems = new ArrayList();
        for (int i = 0; i < LAYERS; i++) {
            systems.add(new ArrayList());
        }
        gameManager = new GameManager();
        Keyboard.create();
        Mouse.create();

//        new Player(new Vec3(10, 10, 0));
//
//        for (int i = 0; i < 100; i++) {
//            new Enemy(Vec3.random(10).add(new Vec3(15, 15, 0)).setZ(0));
//        }
        new Level("level0");
    }

    public static void run() {
        while (!Display.isCloseRequested() && !Keys.isPressed(Keyboard.KEY_ESCAPE)) {
            //Input
            Keys.update();
            MouseInput.update();
            //Logic
            for (ArrayList<AbstractSystem> list : systems) {
                for (int i = 0; i < list.size(); i++) {
                    if (!paused || !list.get(i).pauseable()) {
                        list.get(i).update();
                    }
                }
            }
            //Graphics
            Display.update();
            Display.sync(speed);
        }
    }
}
