import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

// Five-pointed star
final int NUM_POINTS = 5;
final float radius = 300;
final Vector2 points = new Vector2(NUM_POINTS * 2);

final float firstPeakAngle = 90f;
final float interPeakArcDegrees = 360f / NUM_POINTS; // 72º
final float interPointArcDegrees = interPeakArcDegrees / 2;
final float angle18 = interPeakArcDegrees / 2 / 2;
final float angle54 = (180 - interPeakArcDegrees) / 2; // Triángulo isosceles entre 2 picos y el centro
final float innerRadiusProportion = (float) (Math.sin(Math.toRadians(angle18)) / Math.sin(Math.toRadians(angle54)));
final float innerRadius = radius * innerRadiusProportion;

void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Generar la estrella de 5 puntas

    float angleDegrees = firstPeakAngle;
    // Para cada punto de la estrella, tanto picos como valles
    for (int i = 0; i < NUM_POINTS * 2; i++) {

        final float angleRadians = (float) Math.toRadians(angleDegrees);

        // Posiciones pares para los picos e impares para los valles
        // El radio del punto depende de si es pico o valle
        final float pointRadius = i % 2 == 0 ? radius : innerRadius;

        points.position(i)
                .x((float) (pointRadius * Math.cos(angleRadians)))
                .y((float) (pointRadius * -Math.sin(angleRadians)));

        /* El siguiente punto se encuentra avanzando un arco de 360/5 grados
        en sentido horario */
        angleDegrees -= interPointArcDegrees; // 36º
    }

    Vector2 position = newVector2(screenWidth / 2f, screenHeight / 2f);

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(DARKGRAY);
        DrawCircleLinesV(position, radius, LIGHTGRAY);

        for (int i = 0; i < NUM_POINTS; i++) {

            final Vector2 peak = points.position(i * 2).getPointer();
            final Vector2 inner = points.position(i * 2 + 1).getPointer();
            final Vector2 nextPeak = points.position((i * 2 + 2) % (NUM_POINTS * 2)).getPointer();

            final Vector2 peakPosition = Vector2Add(position, peak);
            final Vector2 innerPosition = Vector2Add(position, inner);
            final Vector2 nextPeakPosition = Vector2Add(position, nextPeak);

            DrawTriangle(position, innerPosition, peakPosition, WHITE);
            DrawTriangle(position, nextPeakPosition, innerPosition, RAYWHITE);

            DrawCircleV(peakPosition, 8f, GOLD);
            DrawCircleV(innerPosition, 8f, BEIGE);
        }
        DrawCircleLinesV(position, innerRadius, LIGHTGRAY);
        EndDrawing();
    }
    CloseWindow();
}