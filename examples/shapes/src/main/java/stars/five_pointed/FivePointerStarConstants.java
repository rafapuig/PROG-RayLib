package stars.five_pointed;

public class FivePointerStarConstants {
    public static final int NUM_POINTS = 5;
    public static final int NUM_VERTICES = NUM_POINTS * 2;

    public static final float START_ANGLE_DEGREES = 90f;
    public static final float ANGLE_BETWEEN_TIPS_DEGREES = 360f / NUM_POINTS; // 72º
    public static final float ANGLE_BETWEEN_VERTICES_DEGREES = ANGLE_BETWEEN_TIPS_DEGREES / 2f; // 36º

    static final float RADIUS_RATIO_INNER_ANGLE_DEGREES = ANGLE_BETWEEN_VERTICES_DEGREES / 2f; // 18º
    static final float RADIUS_RATIO_OUTER_ANGLE_DEGREES = (180f - ANGLE_BETWEEN_TIPS_DEGREES) / 2f; // 54º Triángulo isosceles entre 2 picos y el centro

    public static final float INNER_TO_OUTER_RADIUS_RATIO =
            (float) (Math.sin(Math.toRadians(RADIUS_RATIO_INNER_ANGLE_DEGREES)) /
            Math.sin(Math.toRadians(RADIUS_RATIO_OUTER_ANGLE_DEGREES)));
}
