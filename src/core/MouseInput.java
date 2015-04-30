package core;

import util.Vec2;
import graphics.RenderManagerComponent;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class MouseInput {

    private static ArrayList<Integer> down = new ArrayList();
    private static ArrayList<Integer> pressed = new ArrayList();
    private static ArrayList<Integer> released = new ArrayList();
    private static HashMap<Integer, Integer> time = new HashMap();
    private static int wheel;
    private static Vec2 mouse;
    private static Vec2 mouseDelta;

    public static boolean isDown(int button) {
        return down.contains(button);
    }

    public static boolean isPressed(int button) {
        return pressed.contains(button);
    }

    public static boolean isReleased(int button) {
        return released.contains(button);
    }

    public static int getTime(int button) {
        if (!time.containsKey(button)) {
            return 0;
        }
        return time.get(button);
    }

    public static Vec2 mouse() {
        return mouse;
    }

    public static Vec2 mouseDelta() {
        return mouseDelta;
    }

    public static void update() {
        wheel = Mouse.getDWheel() / 120;
        pressed.clear();
        released.clear();
        while (Mouse.next()) {
            Integer button = Mouse.getEventButton();
            if (Mouse.getEventButtonState()) {
                down.add(button);
                pressed.add(button);
                time.put(button, 0);
            } else {
                down.remove(button);
                released.add(button);
            }
        }
        for (Integer i : down) {
            time.put(i, time.get(i) + 1);
        }

        RenderManagerComponent rmc = Main.gameManager.rmc;
        double w = Display.getWidth();
        double h = Display.getHeight();
        double ar = rmc.aspectRatio();
        double vw, vh;

        if (w / h > ar) {
            vw = ar * h;
            vh = h;
        } else {
            vw = w;
            vh = w / ar;
        }
        double left = (w - vw) / 2;
        double bottom = (h - vh) / 2;

        mouse = new Vec2((Mouse.getX() - left) / vw, (Mouse.getY() - bottom) / vh).multiply(rmc.viewSize);
        mouseDelta = new Vec2(Mouse.getDX() / vw, Mouse.getDY() / vh).multiply(rmc.viewSize);
    }

    public static int wheel() {
        return wheel;
    }
}
