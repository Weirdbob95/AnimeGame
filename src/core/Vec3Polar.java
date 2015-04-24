package core;

public class Vec3Polar {

    public final double r;
    public final double t;
    public final double p;

    public Vec3Polar(double r, double t, double p) {
        this.r = r;
        this.t = t;
        this.p = p;
    }

    public Vec3Polar setR(double r) {
        return new Vec3Polar(r, t, p);
    }

    public Vec3Polar setT(double t) {
        return new Vec3Polar(r, t, p);
    }

    public Vec3Polar setP(double p) {
        return new Vec3Polar(r, t, p);
    }

    public Vec3 toRect() {
        return new Vec3(r * Math.cos(t) * Math.cos(p), r * Math.sin(t) * Math.cos(p), r * Math.sin(p));
    }

    @Override
    public String toString() {
        return "(" + r + ", " + t + ", " + p + ")";
    }
}
