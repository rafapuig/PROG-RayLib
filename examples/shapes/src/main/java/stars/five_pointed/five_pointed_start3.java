/**
 * En esta versión
 * La información de la estrella la almacenamos en un solo array de vertices consecutivos (punta, interior, punta, ...)
 * Los vertices de la estrella se generan y almacenan en sentido antihorario
 */

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

// Five-pointed star
final Vector2 points = new Vector2(NUM_VERTICES);
final float radius = 300;
final float innerRadius = radius * INNER_TO_OUTER_RADIUS_RATIO;

void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Generar la estrella de 5 puntas en sentido antihorario
    float angleDegrees = START_ANGLE_DEGREES;
    // Para cada punto de la estrella
    for (int i = 0; i < NUM_VERTICES; i++) {

        final float angleRadians = (float) Math.toRadians(angleDegrees);
        final float pointRadius = radius * (i % 2 == 0 ? 1 : INNER_TO_OUTER_RADIUS_RATIO);

        points.position(i).x((float) (pointRadius * Math.cos(angleRadians))).y((float) (pointRadius * -Math.sin(angleRadians)));

        /* El siguiente punto se encuentra avanzando un arco de 360/5 grados
        en sentido antihorario (counter-clockwise) */
        angleDegrees += ANGLE_BETWEEN_VERTICES_DEGREES; // 36º
    }

    Vector2 position = newVector2(screenWidth / 2f, screenHeight / 2f);


    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    while (!WindowShouldClose()) {

        // Sí se presiona la tecla D toggle (alternar) el modo depuración
        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

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

            DrawTriangle(position, peakPosition, innerPosition, RAYWHITE);
            DrawTriangle(position, innerPosition, nextPeakPosition, WHITE);


            DrawCircleV(peakPosition, 8f, GOLD);
            DrawCircleV(innerPosition, 8f, BEIGE);
        }
        DrawCircleLinesV(position, innerRadius, LIGHTGRAY);
        EndDrawing();
    }
    CloseWindow();
}