import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

private static class FivePointedStar {
    // Five-pointed star
    static final int NUM_POINTS = 5;

    static final float DEFAULT_FIRST_PEAK_ANGLE = 90f;
    static final float interPeakArcDegrees = 360f / NUM_POINTS; // 72º
    static final float angle18 = interPeakArcDegrees / 2f / 2f;
    static final float angle54 = (180f - interPeakArcDegrees) / 2f; // Triángulo isosceles entre 2 picos y el centro
    static final float innerRadiusProportion = (float) (Math.sin(Math.toRadians(angle18)) / Math.sin(Math.toRadians(angle54)));

    private final float radius;
    final Vector2 peaks = new Vector2(NUM_POINTS);
    final Vector2 inners = new Vector2(NUM_POINTS);

    private final float firstPeakAngle;
    private final float innerRadius;

    FivePointedStar(float radius, float firstPeakAngle) {
        this.firstPeakAngle = DEFAULT_FIRST_PEAK_ANGLE;
        this.radius = radius;
        this.innerRadius = radius * innerRadiusProportion;
        generate();
    }

    FivePointedStar(float radius) {
        this(radius, DEFAULT_FIRST_PEAK_ANGLE);
    }

    float getRadius() {
        return radius;
    }

    float getInnerRadius() {
        return innerRadius;
    }

    private void generate() {
        // Generar la estrella de 5 puntas
        float peakAngleDegrees = firstPeakAngle;
        // Para cada punto de la estrella
        for (int i = 0; i < NUM_POINTS; i++) {
            final float peakAngleRadians = (float) Math.toRadians(peakAngleDegrees);
            peaks.position(i)
                    .x((float) (radius * Math.cos(peakAngleRadians)))
                    .y((float) (radius * -Math.sin(peakAngleRadians)));

            /* El punto interior o valle se encuentra avanzando en sentido horario 36º */
            final float innerAngleDegrees = peakAngleDegrees - interPeakArcDegrees / 2f;
            final float innerAngleRadians = (float) Math.toRadians(innerAngleDegrees);

            inners.position(i)
                    .x(innerRadius * (float) Math.cos(innerAngleRadians))
                    .y(innerRadius * (float) -Math.sin(innerAngleRadians));

        /* El siguiente punto se encuentra avanzando un arco de 360/5 grados
        en sentido horario */
            peakAngleDegrees -= interPeakArcDegrees; // 72º
        }
    }

}

void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));


    // Posición donde ubicar el centro de la estrella
    Vector2 position = newVector2(screenWidth / 2f, screenHeight / 2f);

    FivePointedStar star = new FivePointedStar(300f);

    boolean debugMode = false;
    while (!WindowShouldClose()) {

        if(IsKeyPressed(KEY_D)) debugMode = !debugMode;

        BeginDrawing();
        ClearBackground(DARKGRAY);

        //Dibujar la circunferencia externa
        if (debugMode) DrawCircleLinesV(position, star.getRadius(), LIGHTGRAY);

        // Dibujar la estrella
        for (int i = 0; i < FivePointedStar.NUM_POINTS; i++) {
            final Vector2 inner = star.inners.position(i).getPointer();
            final Vector2 peak = star.peaks.position(i).getPointer();
            final Vector2 nextPeak = star.peaks.position((i + 1) % FivePointedStar.NUM_POINTS).getPointer();

            final Vector2 innerPosition = Vector2Add(position, inner);
            final Vector2 peakPosition = Vector2Add(position, peak);
            final Vector2 nextPeakPosition = Vector2Add(position, nextPeak);

            DrawTriangle(position, innerPosition, peakPosition, WHITE);
            DrawTriangle(position, nextPeakPosition, innerPosition, RAYWHITE);

            if(debugMode) {
                DrawCircleV(peakPosition, 8f, GOLD);
                DrawCircleV(innerPosition, 8f, BEIGE);
            }
        }

        // Dibujar la circunferencia interior
        if(debugMode) DrawCircleLinesV(position, star.getInnerRadius(), LIGHTGRAY);
        EndDrawing();
    }
    CloseWindow();
}