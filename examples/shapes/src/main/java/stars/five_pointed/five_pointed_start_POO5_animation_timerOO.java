import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;

private static class FivePointedStar {

    // Five-pointed star
    private final Vector2 outerVertices = new Vector2(NUM_POINTS);
    private final Vector2 innerVertices = new Vector2(NUM_POINTS);

    private float startAngle;
    private float radius;

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

    void setRadius(float radius) {
        if (this.radius == radius) return;
        this.radius = radius;
        generate();
    }

    float getInnerRadius() {
        return radius * INNER_TO_OUTER_RADIUS_RATIO;
    }

    float getStartAngle() {
        return startAngle;
    }

    void setStartAngle(float startAngle) {
        if (this.startAngle == startAngle) return;
        this.startAngle = startAngle;
        generate();

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

private static class Timer {

    private final float duration;
    private float elapsedTime;
    private boolean ticking;
    private boolean enabled;

    boolean isEnabled() {
        return enabled;
    }

    boolean isTicking() {
        return ticking;
    }

    Timer(float duration) {
        this.duration = duration;
    }

    float getDuration() {
        return duration;
    }

    public void reset() {
        enabled = true;
        elapsedTime = 0f;
    }

    public void start() {
        this.ticking = true;
    }

    public void restart() {
        reset();
        start();
    }

    public void stop() {
        this.ticking = false;
    }

    public void toggle() {
        if (!enabled) return;
        if (isTicking()) stop();
        else start();
    }

    private void disable() {
        this.enabled = false;
        stop();
    }

    public void tick(float deltaTime) {
        if (ticking) {
            if (elapsedTime >= duration) disable();
            elapsedTime += deltaTime;
        }
    }

    float getElapsedTime() {
        return elapsedTime;
    }

}

final int screenHeight = 1080;
final int screenWidth = screenHeight; // * 16 / 9;

final Color UNION_JACK_BLUE = newColor(0, 40, 104, 255);


void main(String[] args) {
    InitWindow(screenWidth, screenHeight, "Estrella de 5 puntas");
    SetTargetFPS(GetMonitorRefreshRate(1));


    // Inicializar el modo depuración (a falso)
    boolean debugMode = false;

    float rotationSpeed = 90f; // degrees / segundo
    float zoomSpeed = 1.25f;

    var timer = new Timer(2);

    final float startAngle = 90f;
    final float endAngle = startAngle - 72 * timer.getDuration() * 1.5f;

    final float startRadius = 50f;
    final float endRadius = 450; //startRadius * 1.5f * totalAnimationTime;

    // Crear una estrella de radio 300 y ángulo inicial 90
    FivePointedStar star = new FivePointedStar(startRadius, startAngle);

    Vector2 starCenterStartPosition = newVector2(startRadius + 30, startRadius + 30);
    Vector2 starCenterEndPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    // Posición donde dibujar el centro de la estrella
    Vector2 starCenterPosition = starCenterStartPosition;
    star.setRadius(startRadius);
    star.setStartAngle(startAngle);


    while (!WindowShouldClose()) {

        // Sí se presiona la tecla D toggle (alternar) el modo depuración
        if (IsKeyPressed(KEY_D)) debugMode = !debugMode;

        // Si el timer está deshabilitado (ha acabado la animación)
        if(!timer.isEnabled()) {
            if (IsKeyPressed(KEY_UP)) star.setRadius(star.getRadius() * zoomSpeed);
            if (IsKeyPressed(KEY_DOWN)) star.setRadius(star.getRadius() / zoomSpeed);
            if (IsKeyDown(KEY_LEFT)) star.setStartAngle(star.getStartAngle() + rotationSpeed * GetFrameTime());
            if (IsKeyDown(KEY_RIGHT)) star.setStartAngle(star.getStartAngle() - rotationSpeed * GetFrameTime());
        }

        // Si se pulsa ENTER se inicia el temporizador, y con ello la animación de nuevo
        if (IsKeyPressed(KEY_ENTER)) {
            // Inicializar el temporizador y activarlo
            timer.restart();
        }

        // Si se pulsa ESPACE alternamos el temporizador entre start y stop
        if (IsKeyPressed(KEY_SPACE)) timer.toggle();

        // Update

        // Si está activo el temporizador registrar el tick incrementando el tiempo transcurrido con el tiempo de frame
        if (timer.isTicking()) {

            // Interpolación linear entre min y max valores según el % del tiempo total trascurrido
            float amount = Math.min(timer.getElapsedTime() / timer.getDuration(), 1f);

            float starRadius = Lerp(startRadius, endRadius, amount);
            float starAngle = Lerp(startAngle, endAngle, amount);
            starCenterPosition = Vector2Lerp(starCenterStartPosition, starCenterEndPosition, amount);

            star.setRadius(starRadius);
            star.setStartAngle(starAngle);

            timer.tick(GetFrameTime());
        }

        // Draw
        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        star.draw(starCenterPosition, debugMode);

        // Dibujar los FPS arriba a la izquierda
        DrawFPS(20, 20);

        EndDrawing();
    }
    CloseWindow();
}