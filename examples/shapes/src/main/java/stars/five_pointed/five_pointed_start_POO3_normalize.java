import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;

private static class FivePointedStar {

    // Five-pointed star
    private final Vector2 outerVertices = new Vector2(NUM_POINTS);
    private final Vector2 innerVertices = new Vector2(NUM_POINTS);

    private final float startAngle;
    private final float radius;

    FivePointedStar(float radius, float startAngle) {
        this.startAngle = startAngle;
        this.radius = radius;
        generate();
    }

    FivePointedStar(float radius) {
        this(radius, START_ANGLE_DEGREES);
    }

    float getRadius() {
        return radius;
    }

    float getInnerRadius() {
        return radius * INNER_TO_OUTER_RADIUS_RATIO;
    }

    // Generar la estrella de 5 puntas
    private void generate() {

        // Angulo inicial del primer vértice exterior (punta)
        float outerVertexAngleDegrees = startAngle;

        // Para cada punta de la estrella
        for (int i = 0; i < NUM_POINTS; i++) {

            // Obtener el ángulo equivalente en radianes
            final float outerVertexAngleRadians = (float) Math.toRadians(outerVertexAngleDegrees);

            outerVertices.position(i)
                    .x((float) (Math.cos(outerVertexAngleRadians)))
                    .y((float) (-Math.sin(outerVertexAngleRadians)));

            /* El vértice interior o valle se encuentra avanzando en sentido horario 36º */
            final float innerVertexAngleDegrees = outerVertexAngleDegrees - ANGLE_BETWEEN_VERTICES_DEGREES;
            final float innerVertexAngleRadians = (float) Math.toRadians(innerVertexAngleDegrees);

            innerVertices.position(i)
                    .x(INNER_TO_OUTER_RADIUS_RATIO * (float) Math.cos(innerVertexAngleRadians))
                    .y(INNER_TO_OUTER_RADIUS_RATIO * (float) -Math.sin(innerVertexAngleRadians));

            /* La siguiente punta se encuentra avanzando un arco de 360/5 grados en sentido horario */
            outerVertexAngleDegrees -= ANGLE_BETWEEN_TIPS_DEGREES; // 72º
        }
    }

    void draw(Vector2 centerPosition, boolean debugMode) {

        //Dibujar la circunferencia externa
        if (debugMode) DrawCircleLinesV(centerPosition, this.getRadius(), LIGHTGRAY);

        // Dibujar la estrella
        for (int i = 0; i < NUM_POINTS; i++) {
            final Vector2 innerVertex = this.innerVertices.position(i).getPointer();
            final Vector2 outerVertex = this.outerVertices.position(i).getPointer();
            final Vector2 nextClockwiseOuterVertex = this.outerVertices.position((i + 1) % NUM_POINTS).getPointer();

            final Vector2 innerVertexPosition = Vector2Add(centerPosition, Vector2Scale(innerVertex, radius));
            final Vector2 outerVertexPosition = Vector2Add(centerPosition, Vector2Scale(outerVertex, radius));
            final Vector2 nextClockwiseOuterVertexPosition = Vector2Add(centerPosition, Vector2Scale(nextClockwiseOuterVertex, radius));

            DrawTriangle(centerPosition, innerVertexPosition, outerVertexPosition, WHITE);
            DrawTriangle(centerPosition, nextClockwiseOuterVertexPosition, innerVertexPosition, RAYWHITE);

            if (debugMode) {
                DrawCircleV(outerVertexPosition, 8f, GOLD);
                DrawCircleV(innerVertexPosition, 8f, BEIGE);
            }
        }

        // Dibujar la circunferencia interior
        if (debugMode) DrawCircleLinesV(centerPosition, this.getInnerRadius(), LIGHTGRAY);
    }

}

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

final Color UNION_JACK_BLUE = newColor(0, 40, 104, 255);

void main() {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Posición donde dibujar el centro de la estrella
    Vector2 starCenterPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    // Crear una estrella de radio 300
    FivePointedStar star = new FivePointedStar(300f);

    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    while (!WindowShouldClose()) {

        // Sí se presiona la tecla D toggle (alternar) el modo depuración
        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        star.draw(starCenterPosition, debugMode);

        EndDrawing();
    }
    CloseWindow();
}