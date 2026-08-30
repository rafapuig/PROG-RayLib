/**
 * En esta versión
 * La información de la estrella la almacenamos en un solo array de vertices consecutivos (punta, interior, punta, ...)
 * Los vertices de la estrella se generan y almacenan en sentido antihorario
 */

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

// Five-pointed star
final Vector2 vertices = new Vector2(NUM_VERTICES);
final float radius = 300;
final float innerRadius = radius * INNER_TO_OUTER_RADIUS_RATIO;

final Color UNION_JACK_BLUE = newColor(1, 33, 105, 255);

void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Generar la estrella de 5 puntas en sentido antihorario

    // Angulo inicial del primer vértice exterior (punta)
    float angleDegrees = START_ANGLE_DEGREES;

    // Para cada punto de la estrella
    for (int i = 0; i < NUM_VERTICES; i++) {

        // Obtener el ángulo equivalente en radianes
        final float angleRadians = (float) Math.toRadians(angleDegrees);

        // Posiciones pares para las puntas e impares para los valles
        // El radio del punto depende de si es punta o valle
        final float pointRadius = radius * (i % 2 == 0 ? 1 : INNER_TO_OUTER_RADIUS_RATIO);

        vertices.position(i)
                .x((float) (pointRadius * Math.cos(angleRadians)))
                .y((float) (pointRadius * -Math.sin(angleRadians)));

        /* El siguiente punto se encuentra avanzando un arco de 360/10 grados
        en sentido antihorario (counter-clockwise) */
        angleDegrees += ANGLE_BETWEEN_VERTICES_DEGREES; // 36º
    }

    Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);


    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    while (!WindowShouldClose()) {

        // Sí se presiona la tecla D toggle (alternar) el modo depuración
        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        if (debugMode) DrawCircleLinesV(starCenterPosition, radius, LIGHTGRAY);

        for (int i = 0; i < NUM_POINTS; i++) {
            final Vector2 outerVertex = vertices.position(i * 2).getPointer();
            final Vector2 innerVertex = vertices.position(i * 2 + 1).getPointer();
            final Vector2 nextCounterclockwiseOuterVertex = vertices.position((i * 2 + 2) % (NUM_POINTS * 2)).getPointer();

            final Vector2 outerVertexPosition = Vector2Add(starCenterPosition, outerVertex);
            final Vector2 innerVertexPosition = Vector2Add(starCenterPosition, innerVertex);
            final Vector2 nextCounterclockwiseOuterVertexPosition = Vector2Add(starCenterPosition, nextCounterclockwiseOuterVertex);

            DrawTriangle(starCenterPosition, outerVertexPosition, innerVertexPosition, RAYWHITE);
            DrawTriangle(starCenterPosition, innerVertexPosition, nextCounterclockwiseOuterVertexPosition, WHITE);


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