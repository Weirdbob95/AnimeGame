package util;

import static org.lwjgl.opengl.GL11.*;

public class Vec3 {

    public final double x;
    public final double y;
    public final double z;

    public Vec3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 add(Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    public Vec3 cross(Vec3 other) {
        return new Vec3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }

    public double direction() {
        return Math.atan2(y, x);
    }

    public double direction2() {
        return Math.atan2(z, Math.sqrt(x * x + y * y));
    }

    public double dot(Vec3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vec3) {
            Vec3 v = (Vec3) o;
            return v.x == x && v.y == y && v.z == z;
        }
        return false;
    }

    public void glNormal() {
        glNormal3d(x, y, z);
    }

    public void glTexCoord() {
        glTexCoord3d(x, y, z);
    }

    public void glVertex() {
        glVertex3d(x, y, z);
    }

    public Vec3 interpolate(Vec3 other, double amt) {
        return multiply(amt).add(other.multiply(1 - amt));
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vec3 multiply(double d) {
        return new Vec3(x * d, y * d, z * d);
    }

    public Vec3 normalize() {
        return multiply(1 / length());
    }

    public static Vec3 random(double r) {
        return new Vec3(Math.random() * 2 * r - r, Math.random() * 2 * r - r, Math.random() * 2 * r - r);
    }

    public Vec3 reverse() {
        return new Vec3(-x, -y, -z);
    }

    public Vec3 setLength(double l) {
        return multiply(l / length());
    }

    public Vec3 setX(double x) {
        return new Vec3(x, y, z);
    }

    public Vec3 setY(double y) {
        return new Vec3(x, y, z);
    }

    public Vec3 setZ(double z) {
        return new Vec3(x, y, z);
    }

    public Vec3 subtract(Vec3 other) {
        return new Vec3(x - other.x, y - other.y, z - other.z);
    }

    public Vec3Polar toPolar() {
        return new Vec3Polar(length(), direction(), direction2());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
