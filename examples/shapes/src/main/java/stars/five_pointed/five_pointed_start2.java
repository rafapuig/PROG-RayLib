/**
 * En esta versión
 * La información de la estrella la almacenamos en un solo array de vertices consecutivos (punta, interior, punta, ...)
 */

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

// Five-pointed star
final int NUM_POINTS = 5;
final int NUM_VERTICES = NUM_POINTS * 2;
final Vector2 vertices = new Vector2(NUM_VERTICES);

final float START_ANGLE_DEGREES = 90f;
final float ANGLE_BETWEEN_TIPS_DEGREES = 360f / NUM_POINTS; // 72º
final float ANGLE_BETWEEN_VERTICES_DEGREES = ANGLE_BETWEEN_TIPS_DEGREES / 2f; // 36º
final float RADIUS_RATIO_INNER_ANGLE_DEGREES = ANGLE_BETWEEN_VERTICES_DEGREES / 2f; // 18º
final float RADIUS_RATIO_OUTER_ANGLE_DEGREES = (180f - ANGLE_BETWEEN_TIPS_DEGREES) / 2f; // 54º Triángulo isosceles entre 2 picos y el centro
final float INNER_TO_OUTER_RADIUS_RATIO = (float) (Math.sin(Math.toRadians(RADIUS_RATIO_INNER_ANGLE_DEGREES)) / Math.sin(Math.toRadians(RADIUS_RATIO_OUTER_ANGLE_DEGREES)));

final float outerRadius = 300;
final float innerRadius = outerRadius * INNER_TO_OUTER_RADIUS_RATIO;

final Color UNION_JACK_BLUE = newColor(1, 33, 105, 255);

void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Generar la estrella de 5 puntas en sentido horario

    float angleDegrees = START_ANGLE_DEGREES;
    // Para cada punto de la estrella, tanto puntas como valles
    for (int i = 0; i < NUM_VERTICES; i++) {

        final float angleRadians = (float) Math.toRadians(angleDegrees);

        // Posiciones pares para las puntas e impares para los valles
        // El radio del punto depende de si es pico o valle
        final float pointRadius = i % 2 == 0 ? outerRadius : innerRadius;

        vertices.position(i)
                .x((float) (pointRadius * Math.cos(angleRadians)))
                .y((float) (pointRadius * -Math.sin(angleRadians)));

        /* El siguiente punto se encuentra avanzando un arco de 360/5 grados
        en sentido horario */
        angleDegrees -= ANGLE_BETWEEN_VERTICES_DEGREES; // 36º
    }

    Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    boolean debugMode = false;
    while (!WindowShouldClose()) {

        // Update
        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

        // Draw
        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        if (debugMode) DrawCircleLinesV(starCenterPosition, outerRadius, LIGHTGRAY);

        for (int i = 0; i < NUM_POINTS; i++) {

            final Vector2 outerVertex = vertices.position(i * 2).getPointer();
            final Vector2 innerVertex = vertices.position(i * 2 + 1).getPointer();
            final Vector2 nextClockwiseOuterVertex = vertices.position((i * 2 + 2) % (NUM_VERTICES)).getPointer();

            final Vector2 outerVertexPosition = Vector2Add(starCenterPosition, outerVertex);
            final Vector2 innerVertexPosition = Vector2Add(starCenterPosition, innerVertex);
            final Vector2 nextClockwiseOuterVertexPosition = Vector2Add(starCenterPosition, nextClockwiseOuterVertex);

            DrawTriangle(starCenterPosition, innerVertexPosition, outerVertexPosition, WHITE);
            DrawTriangle(starCenterPosition, nextClockwiseOuterVertexPosition, innerVertexPosition, RAYWHITE);

            if (debugMode) {
                DrawCircleV(outerVertexPosition, 8f, GOLD);
                DrawCircleV(innerVertexPosition, 8f, BEIGE);
            }
        }
        if (debugMode) DrawCircleLinesV(starCenterPosition, innerRadius, LIGHTGRAY);
        EndDrawing();
    }
    CloseWindow();
}