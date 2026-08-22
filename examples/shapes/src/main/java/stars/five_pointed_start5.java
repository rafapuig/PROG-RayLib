/**
 * En esta versión generamos una estrella normalizada,
 * es decir, de radio 1
 *
 * Cuando se dibuje se escalará a su tamaño indicado por el radio
 * y se posiciona siendo el punto de referencia el punto central de la estrella
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
        final float pointRadius = i % 2 == 0 ? 1 : innerRadiusProportion;

        var point = points.position(i)
                .x((float) Math.cos(angleRadians))
                .y((float) -Math.sin(angleRadians));

        point.put(Vector2Scale(point, pointRadius));

        /* El siguiente punto se encuentra avanzando un arco de 360/5 grados
        en sentido anti-horario */
        angleDegrees += interPointArcDegrees; // 36º
    }

    Vector2 position = newVector2(screenWidth / 2f, screenHeight / 2f);

    var triangleFanPoints = new Vector2(NUM_POINTS * 2 + 2);
    triangleFanPoints.position(0).put(position);

    for (int i = 1; i <= NUM_POINTS * 2 + 1; i++) {
        var point = points.position((i - 1) % (NUM_POINTS * 2));
        var pointPosition = Vector2Add(position, Vector2Scale(point, radius));
        triangleFanPoints.position(i).put(pointPosition);
    }

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(DARKGRAY);
        DrawCircleLinesV(position, radius, LIGHTGRAY);

        DrawTriangleFan(triangleFanPoints.position(0), (int) triangleFanPoints.capacity(), RAYWHITE);

        for (int i = 0; i < NUM_POINTS * 2; i++) {
            Vector2 start = triangleFanPoints.position(1 + i).getPointer();
            Vector2 end = triangleFanPoints.position(1 + i + 1).getPointer();
            DrawLineEx(start, end, 3f, SKYBLUE);
        }

        for (int i = 0; i < NUM_POINTS; i++) {
            triangleFanPoints.position(1 + i * 2); // Nos posicionamos en el punto que vamos a dibujar
            DrawCircleV(triangleFanPoints, 8f, GOLD); // Usamos la posición antes de modificarla

            triangleFanPoints.position(1 + i * 2 + 1); // Cambiamos la posición al siguiente punto
            DrawCircleV(triangleFanPoints, 8f, BEIGE); // Usamos la nueva
        }

        DrawCircleLinesV(position, innerRadius, LIGHTGRAY);

        EndDrawing();
    }
    CloseWindow();
}