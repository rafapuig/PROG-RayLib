/**
 * Esta version dibuja la estrella mediante la función DrawTriangleFan
 * por eso generamos los puntos de los triángulos en sentido anti-horario
 */

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
final float interPointArcDegrees = interPeakArcDegrees / 2f;
final float angle18 = interPeakArcDegrees / 2 / 2;
final float angle54 = (180f - interPeakArcDegrees) / 2f; // Triángulo isosceles entre 2 picos y el centro
final float innerRadiusProportion = (float) (Math.sin(Math.toRadians(angle18)) / Math.sin(Math.toRadians(angle54)));
final float innerRadius = radius * innerRadiusProportion;

void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Generar la estrella de 5 puntas
    float angleDegrees = firstPeakAngle;
    // Para cada punto de la estrella
    for (int i = 0; i < NUM_POINTS * 2; i++) {
        final float angleRadians = (float) Math.toRadians(angleDegrees);
        final float pointRadius = radius * (i % 2 == 0 ? 1 : innerRadiusProportion);

        var point = points.position(i)
                .x((float) Math.cos(angleRadians))
                .y((float) -Math.sin(angleRadians));

        points.position(i).put(Vector2Scale(point, pointRadius));

        /* El siguiente punto se encuentra avanzando un arco de 360/5 grados
        en sentido anti-horario */
        angleDegrees += interPointArcDegrees; // 36º
    }

    Vector2 position = newVector2(screenWidth / 2f, screenHeight / 2f);

    /* Solamente tenemos que generar los puntos para TriangleFan una vez (antes del bucle) */
    var triangleFanPoints = new Vector2(NUM_POINTS * 2 + 2); // 2 * 5 = 10 + el punto central y el primer pico otra vez
    // El primer punto del TriangleFan (que será utilizará para generar todos los triángulos del fan */
    triangleFanPoints.position(0).put(position);

    // Ahora definimos los 11 puntos restantes (posiciones 1 a 11)
    for (int i = 0; i <= NUM_POINTS * 2; i++) {
        // Cuando i valga exactamente NUM_POINTS * 2 el resto será 0 (acceso al primer punto otra vez)
        var point = points.position((i) % (NUM_POINTS * 2)); // Al usar el módulo del tamaño nunca nos pasamos
        var pointPosition = Vector2Add(position, point);
        triangleFanPoints.position(i + 1).put(pointPosition);
    }

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(DARKGRAY);
        DrawCircleLinesV(position, radius, LIGHTGRAY);

        DrawTriangleFan(triangleFanPoints.position(0), (int) triangleFanPoints.capacity(), RAYWHITE);

        for (int i = 0; i < NUM_POINTS; i++) {
            DrawCircleV(triangleFanPoints.position(1 + i * 2), 8f, GOLD);
            DrawCircleV(triangleFanPoints.position(1 + i * 2 + 1), 8f, BEIGE);
        }

        DrawCircleLinesV(position, innerRadius, LIGHTGRAY);

        EndDrawing();
    }
    CloseWindow();
}