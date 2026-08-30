import com.raylib.Raylib;

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

// Five-pointed star (estrella de cinco puntas)
final int NUM_POINTS = 5;
final Vector2 outerVertices = new Vector2(NUM_POINTS);
final Vector2 innerVertices = new Vector2(NUM_POINTS);

final float START_ANGLE_DEGREES = 90f;
final float ANGLE_BETWEEN_TIPS_DEGREES = 360f / NUM_POINTS; // 72º
final float ANGLE_BETWEEN_VERTICES_DEGREES = ANGLE_BETWEEN_TIPS_DEGREES / 2f; // 36º
final float RADIUS_RATIO_INNER_ANGLE_DEGREES = ANGLE_BETWEEN_VERTICES_DEGREES / 2f; // 18º
final float RADIUS_RATIO_OUTER_ANGLE_DEGREES = (180f - ANGLE_BETWEEN_TIPS_DEGREES) / 2f; // 54º Triángulo isosceles entre 2 picos y el centro
final float INNER_TO_OUTER_RADIUS_RATIO =
        (float) (Math.sin(Math.toRadians(RADIUS_RATIO_INNER_ANGLE_DEGREES))
                / Math.sin(Math.toRadians(RADIUS_RATIO_OUTER_ANGLE_DEGREES)));

final float outerRadius = 300f;
final float innerRadius = outerRadius * INNER_TO_OUTER_RADIUS_RATIO;

final Color UNION_JACK_BLUE = newColor(1, 33, 105, 255);


void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Generar la estrella de 5 puntas en el punto origen (0,0) como centro

    // Angulo inicial del primer vértice exterior (punta)
    float outerVertexAngle = -START_ANGLE_DEGREES;

    // Para cada punta de la estrella
    for (int i = 0; i < NUM_POINTS; i++) {

        outerVertices.position(i).put(
                Vector2Rotate(Vector2UnitX(), (float) Math.toRadians(outerVertexAngle)));

        /* El punto interior o valle se encuentra avanzando en sentido horario 36º */
        final float innerVertexAngle = outerVertexAngle + ANGLE_BETWEEN_VERTICES_DEGREES;

        innerVertices.position(i).put(
                Vector2Rotate(Vector2UnitX(), (float) Math.toRadians(innerVertexAngle)));

        /* El siguiente punto se encuentra avanzando un arco de 360/5 grados
        entre dos puntas de estrella consecutivas en sentido horario (En Raylib se suma) */
        outerVertexAngle += ANGLE_BETWEEN_TIPS_DEGREES; // 72º
    }

    // Angulo de rotation de la figura de la estrella
    float rotationAngle = 20;

    // Rotar
    for (int i = 0; i < NUM_POINTS; i++) {
        outerVertices.position(i).put(Vector2Rotate(outerVertices, (float) Math.toRadians(rotationAngle)));
        innerVertices.position(i).put(Vector2Rotate(innerVertices, (float) Math.toRadians(rotationAngle)));
    }

    // Escalar
    for (int i = 0; i < NUM_POINTS; i++) {
        outerVertices.position(i).put(Vector2Scale(outerVertices, outerRadius));
        innerVertices.position(i).put(Vector2Scale(innerVertices, innerRadius));
    }

    // Posición donde dibujar el centro de la estrella
    Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    // Trasladar los vertices de la estrella para que su centro sea starCenterPosition
    for (int i = 0; i < NUM_POINTS; i++) {
        outerVertices.position(i).put(Vector2Add(starCenterPosition, outerVertices));
        innerVertices.position(i).put(Vector2Add(starCenterPosition, innerVertices));
    }

    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    while (!WindowShouldClose()) {

        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        //Dibujar la circunferencia exterior
        if (debugMode) DrawCircleLinesV(starCenterPosition, outerRadius, LIGHTGRAY);

        // Dibujar la estrella en sentido horario
        for (int i = 0; i < NUM_POINTS; i++) { // Por cada punto (pico) de la estrella
            final Vector2 innerVertex = innerVertices.position(i).getPointer();
            final Vector2 outerVertex = outerVertices.position(i).getPointer();
            final Vector2 nextClockwiseOuterVertex = outerVertices.position((i + 1) % NUM_POINTS).getPointer();

            DrawTriangle(starCenterPosition, innerVertex, outerVertex, WHITE);
            DrawTriangle(starCenterPosition, nextClockwiseOuterVertex, innerVertex, RAYWHITE);

            if (debugMode) {
                DrawCircleV(outerVertex, 8f, GOLD);
                DrawCircleV(innerVertex, 8f, BEIGE);
            }
        }

        // Dibujar la circunferencia interior
        if (debugMode) DrawCircleLinesV(starCenterPosition, innerRadius, LIGHTGRAY);

        EndDrawing();
    }
    CloseWindow();
}