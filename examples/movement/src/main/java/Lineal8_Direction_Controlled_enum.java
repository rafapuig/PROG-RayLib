import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

enum TurningSide {None, Left, Right}

void main() {

    // Dimensiones de la pantalla
    final int screenHeight = 1080;
    final int screenWidth = screenHeight * 16 / 9;

    // Box
    final Vector2 size = newVector2(80, 40);
    final float initialPosX = screenWidth / 2f;
    final float initialPosY = screenHeight / 2f;

    final float maxSpeed = 500f;
    final float acceleration = 15;
    final float deceleration = 1.5f;
    final float maxTurnSpeed = 1.5f;
    final Vector2 right = newVector2(1, 0);

    InitWindow(screenWidth, screenHeight, "Movimiento lineal con dirección mediante matemática de vectores 2D");
    SetTargetFPS(GetMonitorRefreshRate(1)); // Observar como NO cambia la velocidad cuando cambian los FPS

    Vector2 position = newVector2(initialPosX, initialPosY);

    float angle = 0f;
    float speed = 0f; // pixels por segundo

    float turnSpeed;
    boolean isTurningLeft;
    boolean isTurningRight;

    while (!WindowShouldClose()) {
        // Update
        float deltaTime = GetFrameTime();

        speed = IsKeyDown(KEY_UP) ?
                Math.min(maxSpeed, speed + acceleration) :
                Math.max(0, speed - deceleration);

        isTurningLeft = IsKeyDown(KEY_LEFT);
        isTurningRight = IsKeyDown(KEY_RIGHT);

        TurningSide side = isTurningLeft ?
                TurningSide.Left : isTurningRight ?
                TurningSide.Right : TurningSide.None;


        if (side != TurningSide.None) {

            int multiplier = switch (side) {
                case Left -> -1;
                case Right -> 1;
                case None -> 0;
            };

            turnSpeed = multiplier * speed / maxSpeed * maxTurnSpeed;
            angle += turnSpeed;
        }

        // Establecer la dirección a partir del ángulo de orientación
        Vector2 direction = Vector2Rotate(right, (float) Math.toRadians(angle));

        // Establecer el vector de velocidad a partir de la dirección y la magnitud de la velocidad (speed)
        Vector2 velocity = Vector2Scale(direction, speed);

        // Establecer la posición actual desde la posición anterior + velocidad * deltaTime
        position = Vector2Add(position, Vector2Scale(velocity, deltaTime));

        // Clampamos la posición para que no se salga de los límites
        position = Vector2Clamp(
                position,
                newVector2(0, 0),
                newVector2(screenWidth, screenHeight)
        );

        // Draw
        BeginDrawing();
        ClearBackground(BLACK);
        DrawFPS(20, 20);

        DrawRectanglePro(
                newRectangle(position.x(), position.y(), size.x(), size.y()),
                newVector2(size.x() * 0.60f, size.y() * .5f), //Vector2Scale(size,.5f),
                angle,
                BEIGE);

        EndDrawing();
    }
    CloseWindow();
}