import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;

private static class FivePointedStar {

    // Five-pointed star
    final Vector2 outerVertices = new Vector2(NUM_POINTS);
    final Vector2 innerVertices = new Vector2(NUM_POINTS);

    private final float startAngle;
    private final float outerRadius;
    private final float innerRadius;

    FivePointedStar(float radius, float startAngle) {
        this.startAngle = startAngle;
        outerRadius = radius;
        innerRadius = outerRadius * INNER_TO_OUTER_RADIUS_RATIO;
        generate();
    }

    FivePointedStar(float radius) {
        this(radius, START_ANGLE_DEGREES);
    }

    float getRadius() {
        return outerRadius;
    }

    float getInnerRadius() {
        return innerRadius;
    }


    private void generate() {
        // Generar la estrella de 5 puntas

        // Angulo inicial del primer vértice exterior (punta)
        float outerVertexAngleDegrees = this.startAngle;

        // Para cada punta de la estrella
        for (int i = 0; i < NUM_POINTS; i++) {

            // Obtener el ángulo equivalente en radianes
            final float outerVertexAngleRadians = (float) Math.toRadians(outerVertexAngleDegrees);

            outerVertices.position(i)
                    .x((float) (outerRadius * Math.cos(outerVertexAngleRadians)))
                    .y((float) (outerRadius * -Math.sin(outerVertexAngleRadians)));

            /* El vértice interior o valle se encuentra avanzando en sentido horario 36º */
            final float innerVertexAngleDegrees = outerVertexAngleDegrees - ANGLE_BETWEEN_VERTICES_DEGREES;
            final float innerVertexAngleRadians = (float) Math.toRadians(innerVertexAngleDegrees);

            innerVertices.position(i)
                    .x(innerRadius * (float) Math.cos(innerVertexAngleRadians))
                    .y(innerRadius * (float) -Math.sin(innerVertexAngleRadians));

        /* La siguiente punta se encuentra avanzando un arco de 360/5 grados en sentido horario */
            outerVertexAngleDegrees -= ANGLE_BETWEEN_TIPS_DEGREES; // 72º
        }
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

        //Dibujar la circunferencia externa
        if (debugMode) DrawCircleLinesV(starCenterPosition, star.getRadius(), LIGHTGRAY);

        // Dibujar la estrella
        for (int i = 0; i < NUM_POINTS; i++) {
            final Vector2 innerVertex = star.innerVertices.position(i).getPointer();
            final Vector2 outerVertex = star.outerVertices.position(i).getPointer();
            final Vector2 nextClockwiseOuterVertex = star.outerVertices.position((i + 1) % NUM_POINTS).getPointer();

            final Vector2 innerVertexPosition = Vector2Add(starCenterPosition, innerVertex);
            final Vector2 outerVertexPosition = Vector2Add(starCenterPosition, outerVertex);
            final Vector2 nextClockwiseOuterVertexPosition = Vector2Add(starCenterPosition, nextClockwiseOuterVertex);

            DrawTriangle(starCenterPosition, innerVertexPosition, outerVertexPosition, WHITE);
            DrawTriangle(starCenterPosition, nextClockwiseOuterVertexPosition, innerVertexPosition, RAYWHITE);

            if (debugMode) {
                DrawCircleV(outerVertexPosition, 8f, GOLD);
                DrawCircleV(innerVertexPosition, 8f, BEIGE);
            }
        }

        // Dibujar la circunferencia interior
        if (debugMode) DrawCircleLinesV(starCenterPosition, star.getInnerRadius(), LIGHTGRAY);
        EndDrawing();
    }
    CloseWindow();
}