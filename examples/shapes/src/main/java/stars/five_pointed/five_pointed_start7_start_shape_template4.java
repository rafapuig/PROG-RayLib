/**
 * En esta versión la estrella es una plantilla
 * con su radio (escala) normalizada
 * y rotación inicial 90º de la primera punta
 * <p>
 * Con esta plantilla podemos dibujar, estrellas en cualquier posición, de cualquier radio, y rotadas cualquier ángulo
 */

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;
import static stars.five_pointed.helpers.Vector2Helper.transformEx;


void resetStar(Vector2 vertices) {

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

        /* El siguiente vértice se encuentra avanzando un arco de 360/10 grados en sentido antihorario */
        angleDegrees += ANGLE_BETWEEN_VERTICES_DEGREES; // 36º
    }
}


boolean setRotationAngle(float newRotationAngle) {
    if (rotationAngle == newRotationAngle) return false;
    rotationAngle = newRotationAngle;
    return true;
}

boolean setRadius(float newRadius) {
    if (radius == newRadius) return false;
    radius = newRadius;
    return true;
}

boolean setPosition(Vector2 newPosition) {
    //if (starCenterPosition.x() == newPosition.x() && starCenterPosition.y() == newPosition.y()) return false;
    if (Vector2Equals(starCenterPosition, newPosition) != 0) return false;
    starCenterPosition.put(newPosition);
    return true;
}


final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

final Color UNION_JACK_BLUE = newColor(1, 33, 105, 255);

final float rotationSpeed = 90; // 90 grados / segundo
final float zoomSpeed = 0.5f;
final float speed = 500f; // pixels / segundo

Vector2 vertices = new Vector2(NUM_VERTICES);
float radius = 150;

// Rotación de la estrella
float rotationAngle = 0;

// Posición donde dibujar el centro de la estrella
Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);


void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));


    resetStar(vertices);
    transformEx(vertices, starCenterPosition, rotationAngle, radius);

    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    while (!WindowShouldClose()) {

        // Sí se presiona la tecla D toggle (alternar) el modo depuración
        if (IsKeyPressed(KEY_SPACE)) debugMode = !debugMode;

        boolean modified = false;

        if (IsKeyDown(KEY_LEFT)) {
            modified |= setRotationAngle(rotationAngle - rotationSpeed * GetFrameTime());
        }
        if (IsKeyDown(KEY_RIGHT)) {
            modified |= setRotationAngle(rotationAngle + rotationSpeed * GetFrameTime());
        }
        if (IsKeyDown(KEY_UP)) {
            modified |= setRadius(radius * (1 + zoomSpeed * GetFrameTime()));
        }
        if (IsKeyDown(KEY_DOWN)) {
            modified |= setRadius(radius / (1 + zoomSpeed * GetFrameTime()));
        }

        Vector2 input = Vector2Zero();
        if (IsKeyDown(KEY_A)) input.x(-1);
        if (IsKeyDown(KEY_D)) input.x(1);
        if (IsKeyDown(KEY_W)) input.y(-1);
        if (IsKeyDown(KEY_S)) input.y(1);

        if (Vector2Equals(input, Vector2Zero()) != 1) {
            Vector2 direction = Vector2Normalize(input);
            Vector2 velocity = Vector2Scale(direction, speed);

            modified |= setPosition(Vector2Add(starCenterPosition, Vector2Scale(velocity, GetFrameTime())));
        }

        //modified = modified || input.x() != 0 || input.y() != 0;

        if (modified) {

            //starCenterPosition = Vector2Add(starCenterPosition, Vector2Scale(velocity, GetFrameTime()));

            resetStar(vertices);
            transformEx(vertices, starCenterPosition, rotationAngle, radius);
            IO.println(GetTime() + " modificado!!!");
        }

        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        // Dibujar la estrella
        for (int i = 0; i < NUM_VERTICES; i += 2) {
            final Vector2 outerVertex = vertices.position(i).getPointer();
            final Vector2 innerVertex = vertices.position(i + 1).getPointer();
            final Vector2 nextCounterclockwiseOuterVertex = vertices.position((i + 2) % (NUM_VERTICES)).getPointer();

            DrawTriangle(starCenterPosition, outerVertex, innerVertex, RAYWHITE);
            DrawTriangle(starCenterPosition, innerVertex, nextCounterclockwiseOuterVertex, WHITE);
        }

        // Dibujar los lados de la estrella
        for (int i = 0; i < NUM_VERTICES; i++) {
            // Punto inicial de la linea a trazar
            Vector2 start = vertices.position(i).getPointer();

            // Punto final de la línea a trazar (No nos vamos a salir del rango porque hay 12 puntos) max i = 9
            Vector2 end = vertices.position((i + 1) % NUM_VERTICES).getPointer();

            // Dibuja la línea desde el punto inicial al final para dibujar un lado de dos 10 que tiene la estrella
            DrawLineEx(start, end, 3f, GOLD);
        }


        if (debugMode) {
            //Dibujar la circunferencia exterior
            DrawCircleLinesV(starCenterPosition, radius, LIGHTGRAY);


            // Dibujar los puntos de los vertices interiores y exteriores con círculos
            for (int i = 0; i < NUM_VERTICES; i += 2) {
                // Nos posicionamos en el punto que vamos a dibujar (exterior)
                vertices.position(i);
                // Usamos esa posición (antes de modificarla) para dibujar el punto exterior
                DrawCircleV(vertices, 8f, GOLD);
            }

            for (int i = 1; i < NUM_VERTICES; i += 2) {
                // Nos posicionamos en el punto que vamos a dibujar (interior)
                vertices.position(i);
                // Usamos la nueva posición para dibujar el círculo de un punto interior
                DrawCircleV(vertices, 8f, BEIGE);
            }

            // Dibujar la circunferencia interior
            DrawCircleLinesV(starCenterPosition, radius * INNER_TO_OUTER_RADIUS_RATIO, LIGHTGRAY);
        }

        DrawFPS(20, 20);
        EndDrawing();
    }

    vertices.deallocate();

    CloseWindow();
}