/**
 * En esta versión generamos una estrella normalizada,
 * es decir, de radio exterior 1
 * <p>
 * Cuando se dibuje se escalará a su tamaño indicado por el radio
 * y se posiciona siendo el punto de referencia el punto central de la estrella
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

        // A partir del ángulo obtener la posición del vertice
        var vertex = vertices.position(i)
                .x((float) Math.cos(angleRadians))
                .y((float) -Math.sin(angleRadians));

        // Los puntos pares son exteriores y su radio normalizado es 1
        // Los puntos impares son interiores y su radio normalizado es la ratio entre radio interior y exterior
        final float vertexRadius = i % 2 == 0 ? 1 : INNER_TO_OUTER_RADIUS_RATIO;

        // Escalamos el vértice de la estrella según su radio normalizado
        vertex.put(Vector2Scale(vertex, vertexRadius));

        /* El siguiente vértice se encuentra avanzando un arco de 360/10 grados
        en sentido antihorario (counter-clockwise) */
        angleDegrees += ANGLE_BETWEEN_VERTICES_DEGREES; // 36º
    }

    // Posición donde dibujar el centro de la estrella
    Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    // Vertices para dibujar un Triangle Fan
    var triangleFanPoints = new Vector2(NUM_VERTICES + 2);

    // El primer punto del TriangleFan (que se utiliza para generar TODOS los triángulos del fan)
    triangleFanPoints.position(0).put(starCenterPosition);

    // Establecer los 11 puntos restantes (posiciones 1 a 11 del array triangleFanPoints)
    for (int i = 0; i <= NUM_VERTICES; i++) {
        // Posicionar el puntero sobre el punto i-estimo de la estrella
        var starPoint = vertices.position(i % NUM_VERTICES);

        // Escalar el punto de la estrella según el radio exterior y trasladarlo relativamente al centro de la estrella
        var pointPosition = Vector2Add(starCenterPosition, Vector2Scale(starPoint, radius));

        // Establecer el punto correspondiente del triangle fan
        triangleFanPoints.position(i + 1).put(pointPosition);
    }

    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    while (!WindowShouldClose()) {

        // Sí se presiona la tecla D toggle (alternar) el modo depuración
        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);
        if (debugMode) DrawCircleLinesV(starCenterPosition, radius, LIGHTGRAY);

        // Dibujar los vertices del triangle fan para dibujar la estrella
        DrawTriangleFan(triangleFanPoints.position(0), (int) triangleFanPoints.capacity(), RAYWHITE);

        // Dibujar los lados de la estrella
        for (int i = 0; i < NUM_VERTICES; i++) {
            // Punto inicial de la linea a trazar
            Vector2 start = triangleFanPoints.position(1 + i).getPointer();

            // Punto final de la línea a trazar (No nos vamos a salir del rango porque hay 12 puntos) max i = 9
            Vector2 end = triangleFanPoints.position(1 + i + 1).getPointer();

            // Dibuja la línea desde el punto inicial al final para dibujar un lado de dos 10 que tiene la estrella
            DrawLineEx(start, end, 3f, SKYBLUE);
        }

        if (debugMode) {

            // Dibujar los puntos de los vertices interiores y exteriores con círculos
            for (int i = 0; i < NUM_POINTS; i++) {

                // Nos posicionamos en el punto que vamos a dibujar (exterior)
                triangleFanPoints.position(1 + i * 2);

                // Usamos esa posición (antes de modificarla) para dibujar el punto exterior
                DrawCircleV(triangleFanPoints, 8f, GOLD);

                // Modificamos la posición (para que triangleFanPoints apunte al siguiente punto)
                triangleFanPoints.position(1 + i * 2 + 1);

                // Usamos la nueva posición para dibujar el círculo de un punto interior
                DrawCircleV(triangleFanPoints, 8f, BEIGE);
            }
        }

        // Dibujar la circunferencia interior
        if(debugMode) DrawCircleLinesV(starCenterPosition, innerRadius, LIGHTGRAY);

        EndDrawing();
    }
    CloseWindow();
}