/**
 * Esta version dibuja la estrella mediante la función DrawTriangleFan
 * por eso generamos los puntos de los triángulos en sentido antihorario
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

    // Generar la estrella de 5 puntas

    // Angulo inicial del primer vértice exterior (punta)
    float angleDegrees = START_ANGLE_DEGREES;

    // Para cada vértice de la estrella
    for (int i = 0; i < NUM_VERTICES; i++) {

        // Obtener el ángulo equivalente en radianes
        final float angleRadians = (float) Math.toRadians(angleDegrees);

        // Posiciones pares para las puntas e impares para los valles
        // El radio del punto depende de si es punta o valle
        final float pointRadius = radius * (i % 2 == 0 ? 1 : INNER_TO_OUTER_RADIUS_RATIO);

        var point = vertices.position(i)
                .x((float) Math.cos(angleRadians))
                .y((float) -Math.sin(angleRadians));

        // Escalamos el vértice de la estrella según su radio
        vertices.position(i).put(Vector2Scale(point, pointRadius));

        /* El siguiente punto se encuentra avanzando un arco de 360/10 grados
        en sentido antihorario (counter-clockwise) */
        angleDegrees += ANGLE_BETWEEN_VERTICES_DEGREES; // 36º
    }

    // Posición donde dibujar el centro de la estrella
    Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    /* Solamente tenemos que generar los puntos para TriangleFan una vez (antes del bucle) */
    // 2 * 5 = 10 + el punto central y el primer pico otra vez para cerrar la figura
    var triangleFanPoints = new Vector2(NUM_VERTICES + 2);

    // El primer punto del TriangleFan (que se utiliza para generar TODOS los triángulos del fan)
    triangleFanPoints.position(0).put(starCenterPosition);

    // Ahora definimos los 11 puntos restantes (posiciones 1 a 11 de triangleFanPoints)
    for (int i = 0; i <= NUM_VERTICES; i++) {
        // Cuando i valga exactamente NUM_VERTICES el resto será 0 (accede al primer punto otra vez)
        var point = vertices.position((i) % NUM_VERTICES); // Al usar el módulo del tamaño nunca nos pasamos
        var pointPosition = Vector2Add(starCenterPosition, point);
        triangleFanPoints.position(i + 1).put(pointPosition);
    }

    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    while (!WindowShouldClose()) {

        // Sí se presiona la tecla D toggle (alternar) el modo depuración
        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

        // Dibujar
        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        if (debugMode) DrawCircleLinesV(starCenterPosition, radius, LIGHTGRAY);

        // Dibujar los vertices del triangle fan para dibujar la estrella
        DrawTriangleFan(triangleFanPoints.position(0), (int) triangleFanPoints.capacity(), RAYWHITE);

        if (debugMode) {
            for (int i = 0; i < NUM_POINTS; i++) {
                DrawCircleV(triangleFanPoints.position(1 + i * 2), 8f, GOLD);
                DrawCircleV(triangleFanPoints.position(1 + i * 2 + 1), 8f, BEIGE);
            }

            DrawCircleLinesV(starCenterPosition, innerRadius, LIGHTGRAY);
        }

        EndDrawing();
    }
    CloseWindow();
}