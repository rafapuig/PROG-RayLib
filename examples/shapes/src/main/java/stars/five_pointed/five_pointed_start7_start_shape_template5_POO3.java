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

private class FivePointerStar implements AutoCloseable {

    private final Vector2 vertices = new Vector2(NUM_VERTICES);

    // Radio de la estrella (escala)
    private float radius = 150;

    // Angulo de rotación de la estrella en grados
    private float rotationAngle = 0;

    // Localización del centro de la estrella
    private final Vector2 center = Vector2Zero();


    private Color lightColor = WHITE;
    private Color shadowColor = RAYWHITE;
    private Color borderColor = GOLD;


    private boolean mustUpdate = true;

    FivePointerStar() {
    }

    float getRadius() {
        return radius;
    }

    float getRotation() {
        return rotationAngle;
    }

    Vector2 getPosition() {
        return center;
    }


    void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
    }

    void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }


    void draw(boolean debugMode) {

        // Dibujar los triángulos interiores de la estrella
        fill();

        // Dibujar los lados de la estrella
        drawBorder();

        if (debugMode) drawDebugGizmos();
    }

    private void drawDebugGizmos() {
        //Dibujar la circunferencia exterior
        DrawCircleLinesV(center, radius, LIGHTGRAY);

        // Dibujar los puntos de los vertices interiores y exteriores con círculos
        for (int i = 0; i < NUM_VERTICES; i += 2) {
            // Nos posicionamos en el punto que vamos a dibujar (exterior)
            vertices.position(i);
            // Usamos esa posición (antes de modificarla) para dibujar el punto exterior
            DrawCircleV(vertices, 8f, RED);
        }

        for (int i = 1; i < NUM_VERTICES; i += 2) {
            // Nos posicionamos en el punto que vamos a dibujar (interior)
            vertices.position(i);
            // Usamos la nueva posición para dibujar el círculo de un punto interior
            DrawCircleV(vertices, 8f, PURPLE);
        }

        // Dibujar la circunferencia interior
        DrawCircleLinesV(center, radius * INNER_TO_OUTER_RADIUS_RATIO, LIGHTGRAY);
    }

    private void drawBorder() {
        for (int i = 0; i < NUM_VERTICES; i++) {
            // Punto inicial de la linea a trazar
            Vector2 start = vertices.position(i).getPointer();

            // Punto final de la línea a trazar (No nos vamos a salir del rango porque hay 12 puntos) max i = 9
            Vector2 end = vertices.position((i + 1) % NUM_VERTICES).getPointer();

            // Dibuja la línea desde el punto inicial al final para dibujar un lado de dos 10 que tiene la estrella
            DrawLineEx(start, end, 3f, borderColor);
        }
    }

    private void fill() {
        for (int i = 0; i < NUM_VERTICES; i += 2) {
            final Vector2 outerVertex = vertices.position(i).getPointer();
            final Vector2 innerVertex = vertices.position(i + 1).getPointer();
            final Vector2 nextCounterclockwiseOuterVertex = vertices.position((i + 2) % (NUM_VERTICES)).getPointer();

            DrawTriangle(center, outerVertex, innerVertex, shadowColor);
            DrawTriangle(center, innerVertex, nextCounterclockwiseOuterVertex, lightColor);
        }
    }


    private void transform(Vector2 location, float rotation, float scale) {
        //if(rotation == rotationAngle && scale == radius && Vector2Equals(location, center)==1) return;
        reset();
        transformEx(vertices, location, rotation, scale);
    }

    void update(float deltaTime) {
        if (mustUpdate) {
            IO.println("Updating Five Pointer Star");
            transform(center, rotationAngle, radius);
        }
        mustUpdate = false;
    }

    private void reset() {

        // Angulo inicial del primer vértice exterior (punta)
        float angleDegrees = START_ANGLE_DEGREES;

        // Para cada vértice de la estrella
        for (int i = 0; i < NUM_VERTICES; i++) {

            // Obtener el ángulo equivalente en radianes
            final float angleRadians = (float) Math.toRadians(angleDegrees);

            // A partir del ángulo obtener la posición del vertice
            vertices.position(i);

            vertices
                    .x((float) Math.cos(angleRadians))
                    .y((float) -Math.sin(angleRadians));

            // Los puntos pares son exteriores y su radio normalizado es 1
            // Los puntos impares son interiores y su radio normalizado es la ratio entre radio interior y exterior
            final float vertexRadius = i % 2 == 0 ? 1 : INNER_TO_OUTER_RADIUS_RATIO;

            // Escalamos el vértice de la estrella según su radio normalizado
            vertices
                    .x(vertices.x() * vertexRadius)
                    .y(vertices.y() * vertexRadius);

            /* El siguiente vértice se encuentra avanzando un arco de 360/10 grados en sentido antihorario */
            angleDegrees += ANGLE_BETWEEN_VERTICES_DEGREES; // 36º
        }
    }

    void setRotation(float angle) {
        if (this.rotationAngle == angle) return;
        this.rotationAngle = angle;
        mustUpdate = true;
    }

    void setRadius(float radius) {
        if (this.radius == radius) return;
        this.radius = radius;
        mustUpdate = true;
    }

    void setPosition(Vector2 position) {
        if (Vector2Equals(center, position) != 0) return;
        center.put(position);
        mustUpdate = true;
    }

    @Override
    public void close() throws Exception {
        vertices.deallocate();
    }
}


final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

final Color UNION_JACK_BLUE = newColor(1, 33, 105, 255);

final float rotationSpeed = 90; // 90 grados / segundo
final float zoomSpeed = 0.5f;
final float speed = 500f; // pixels / segundo

// Posición donde dibujar el centro de la estrella
Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);


void main() throws Exception {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    try (var star = new FivePointerStar()) {

        star.setPosition(starCenterPosition);
        star.setLightColor(GOLD);
        star.setShadowColor(ORANGE);

        // Inicializar el modo depuración (a falso)
        boolean debugMode = false;

        while (!WindowShouldClose()) {

            /* Manejar la entrada */

            // Sí se presiona la tecla D toggle (alternar) el modo depuración
            if (IsKeyPressed(KEY_SPACE)) debugMode = !debugMode;

            float rotationInput = 0f;

            if (IsKeyDown(KEY_LEFT)) rotationInput = rotationInput - 1f;
            if (IsKeyDown(KEY_RIGHT)) rotationInput = rotationInput + 1f;

            float zoomFactor = 1f;
            float zoomStep = zoomSpeed * GetFrameTime();

            if (IsKeyDown(KEY_UP)) zoomFactor *= 1f + zoomStep;

            if (IsKeyDown(KEY_DOWN)) zoomFactor /= 1f + zoomStep;


            Vector2 input = Vector2Zero();
            if (IsKeyDown(KEY_A)) input.x(input.x() - 1);
            if (IsKeyDown(KEY_D)) input.x(input.x() + 1);
            if (IsKeyDown(KEY_W)) input.y(input.y() - 1);
            if (IsKeyDown(KEY_S)) input.y(input.y() + 1);

            /* Update */
            Vector2 direction = Vector2Normalize(input);
            Vector2 velocity = Vector2Scale(direction, speed);

            star.setPosition(Vector2Add(star.getPosition(), Vector2Scale(velocity, GetFrameTime())));

            star.setRadius(star.getRadius() * zoomFactor);

            star.setRotation(star.getRotation() + rotationInput * rotationSpeed * GetFrameTime());

            star.update(GetFrameTime());

            // Dibujar
            BeginDrawing();
            ClearBackground(UNION_JACK_BLUE);

            // Dibujar la estrella
            star.draw(debugMode);

            DrawFPS(20, 20);
            EndDrawing();
        }
    }

    CloseWindow();
}