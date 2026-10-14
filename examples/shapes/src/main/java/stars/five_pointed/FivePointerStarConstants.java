package stars.five_pointed;

public class FivePointerStarConstants {
    /** Número de puntas (picos) de la estrella */
    public static final int NUM_POINTS = 5;

    /**
     * Número de vertices de la estrella (el doble de puntas)
     * La mitad picos, la mitad valles
     */
    public static final int NUM_VERTICES = NUM_POINTS * 2;

    /**
     * Angulo desde donde se genera el primer pico de la estrella
     * 90 grados, indica la posición superior
     */
    public static final float START_ANGLE_DEGREES = 90f;

    /** Angulo entre dos puntas de la estrella consecutivas */
    public static final float ANGLE_BETWEEN_TIPS_DEGREES = 360f / NUM_POINTS; // 72º

    /** Angulo entre dos vertices consecutivos (la mitad que entre dos puntas) */
    public static final float ANGLE_BETWEEN_VERTICES_DEGREES = ANGLE_BETWEEN_TIPS_DEGREES / 2; // 36º

    private static final float RADIUS_RATIO_INNER_ANGLE_DEGREES = ANGLE_BETWEEN_VERTICES_DEGREES / 2; // 18º

    // 54º Triángulo isosceles entre 2 picos y el centro
    private static final float RADIUS_RATIO_OUTER_ANGLE_DEGREES = (180f - ANGLE_BETWEEN_TIPS_DEGREES) / 2;

    /** Ratio entre el valor del radio interno y el del radio externo */
    public static final float INNER_TO_OUTER_RADIUS_RATIO =
            (float) (Math.sin(Math.toRadians(RADIUS_RATIO_INNER_ANGLE_DEGREES)) /
            Math.sin(Math.toRadians(RADIUS_RATIO_OUTER_ANGLE_DEGREES)));
}
