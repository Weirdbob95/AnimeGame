package shapes;

public class ProjectionRange {

    public double min;
    public double max;
    public double min_low;
    public double min_high;
    public double max_low;
    public double max_high;

    public double collisionHeight(ProjectionRange other) {
//        if (min + 32 == max) {
//            System.out.println("Low: " + max_low + ", High: " + max_high);
//        }
        return (Math.max(max_low, other.min_low) + Math.min(max_high, other.min_high)) / 2;
    }

    public double intersection(ProjectionRange other) {
        if (min >= other.max || max <= other.min) {
            return 0;
        }
        return max - other.min;
    }

}
