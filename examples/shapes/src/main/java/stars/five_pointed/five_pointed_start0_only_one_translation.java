/**
 * En esta version la estrella se genera centrada en el punto 0,0
 * Pero luego se traslada a la posición donde se dibujará
 * Esta traslación se realiza una sola vez antes del bucle del juego
 * durante la inicialización
 */

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

// Five-pointed star, constantes:
final int NUM_POINTS = 5;
final float START_ANGLE_DEGREES = 90f;
final float ANGLE_BETWEEN_TIPS_DEGREES = 360f / NUM_POINTS; // 72º
final float ANGLE_BETWEEN_VERTICES_DEGREES = ANGLE_BETWEEN_TIPS_DEGREES / 2f; // 36º
final float RADIUS_RATIO_INNER_ANGLE_DEGREES = ANGLE_BETWEEN_VERTICES_DEGREES / 2f; // 18º
final float RADIUS_RATIO_OUTER_ANGLE_DEGREES = (180f - ANGLE_BETWEEN_TIPS_DEGREES) / 2f; // 54º Triángulo isosceles entre 2 picos y el centro
final float INNER_TO_OUTER_RADIUS_RATIO =
        (float) (Math.sin(Math.toRadians(RADIUS_RATIO_INNER_ANGLE_DEGREES))
                / Math.sin(Math.toRadians(RADIUS_RATIO_OUTER_ANGLE_DEGREES)));


// Five-pointed star (estrella de cinco puntas)
final Vector2 outerVertices = new Vector2(NUM_POINTS);
final Vector2 innerVertices = new Vector2(NUM_POINTS);

final float outerRadius = 300f;
final float innerRadius = outerRadius * INNER_TO_OUTER_RADIUS_RATIO;

// Color del cantón de la Union Jack
final Color UNION_JACK_BLUE = newColor(1, 33, 105, 255);


void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Generar la estrella de 5 puntas en el punto origen (0,0) como centro

    // Angulo inicial del primer vértice exterior (punta)
    float outerVertexAngleDegrees = START_ANGLE_DEGREES;

    // Para cada punta de la estrella
    for (int i = 0; i < NUM_POINTS; i++) {

        // Obtener el ángulo equivalente en radianes
        final float outerVertexAngleRadians = (float) Math.toRadians(outerVertexAngleDegrees);

        // Rotar desde (1,0) outerVertexAngle en sentido antihorario
        outerVertices.position(i)
                .x((float) (outerRadius * Math.cos(outerVertexAngleRadians)))
                .y((float) (outerRadius * -Math.sin(outerVertexAngleRadians)));

        /* El punto interior o valle se encuentra avanzando en sentido horario 36º */
        final float innerVertexAngleDegrees = outerVertexAngleDegrees - ANGLE_BETWEEN_VERTICES_DEGREES;
        final float innerVertexAngleRadians = (float) Math.toRadians(innerVertexAngleDegrees);

        // Rotar desde (1,0) innerVertexAngle en sentido antihorario
        innerVertices.position(i)
                .x(innerRadius * (float) Math.cos(innerVertexAngleRadians))
                .y(innerRadius * (float) -Math.sin(innerVertexAngleRadians));

        /* El siguiente punto se encuentra avanzando un arco de 360/5 grados
        entre dos puntas de estrella consecutivas en sentido horario (por eso se resta) */
        outerVertexAngleDegrees -= ANGLE_BETWEEN_TIPS_DEGREES; // 72º
    }

    // Posición donde dibujar el centro de la estrella
    Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    // Trasladar todos los vertices de la estrella para que su centro sea starCenterPosition
    // (Solamente se trasladan una vez en la inicialización del programa, no en el bucle while)
    for (int i = 0; i < NUM_POINTS; i++) {
        outerVertices.position(i).put(Vector2Add(starCenterPosition, outerVertices.position(i)));
        innerVertices.position(i).put(Vector2Add(starCenterPosition, innerVertices.position(i)));
    }

    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    // Bucle del juego
    while (!WindowShouldClose()) {

        // Si se pulsa la tecla D alternar el modo depuración
        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

        /* Dibujar */
        BeginDrawing();
        // Limpiamos el frame buffer con un color de fondo UNION_JACK_BLUE
        ClearBackground(UNION_JACK_BLUE);

        //Dibujar la circunferencia exterior
        if (debugMode) DrawCircleLinesV(starCenterPosition, outerRadius, LIGHTGRAY);

        // Dibujar la estrella recorriendo las puntas en sentido horario
        for (int i = 0; i < NUM_POINTS; i++) { // Por cada punto (pico) de la estrella

            /* Obtenemos tres vertices para dibujar dos triángulos */

            // El vértice exterior o pico
            final Vector2 outerVertex = outerVertices.position(i).getPointer();

            // El vértice interior en sentido horario
            final Vector2 innerVertex = innerVertices.position(i).getPointer();

            // El siguiente vértice exterior en sentido horario
            final Vector2 nextClockwiseOuterVertex = outerVertices.position((i + 1) % NUM_POINTS).getPointer();

            // Para dibujar un triángulo hay que especificar los vertices en sentido antihorario
            DrawTriangle(starCenterPosition, innerVertex, outerVertex, WHITE);
            DrawTriangle(starCenterPosition, nextClockwiseOuterVertex, innerVertex, RAYWHITE);

            // Si estamos en modo depuración
            if (debugMode) {
                // Dibujar un circulo que representa el vértice exterior
                DrawCircleV(outerVertex, 8f, GOLD);

                // Dibujar un círculo que representa el vértice interior (en sentido horario)
                DrawCircleV(innerVertex, 8f, BEIGE);
            }
        }

        // Dibujar la circunferencia interior
        if (debugMode) DrawCircleLinesV(starCenterPosition, innerRadius, LIGHTGRAY);

        EndDrawing();
    }
    CloseWindow();
}