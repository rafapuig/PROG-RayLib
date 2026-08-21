import static com.raylib.Colors.*;
import static com.raylib.Colors.GRAY;
import static com.raylib.Colors.RED;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

// Bola
final float radius = 50f;
final Color ballColor = newColor(200, 150, 50, 255);

final float speedX = 400f; // pixels / segundo
final float speedY = 500f; // pixels / segundo
final float gravity = 2000f; // pixels / segundo ^ 2

void main() {

    InitWindow(screenWidth, screenHeight, "Gravedad");
    //SetTargetFPS(GetMonitorRefreshRate(1));

    Vector2 ballPosition = newVector2(screenWidth / 2f, screenHeight * .2f);

    // Velocidad inicial
    Vector2 velocity = newVector2(speedX, speedY);

    boolean paused = true;
    boolean useGravity = false;

    while (!WindowShouldClose()) {
        // Update
        float deltaTime = GetFrameTime();

        if (IsKeyPressed(KEY_SPACE)) paused = !paused;
        if (IsKeyPressed(KEY_G)) useGravity = !useGravity;

        if (!paused) {
            // Calculamos la nueva posición de la bola
            // ∆Ball(x,y) = velocity * ∆(t) --> ball(x,y) += velocity * deltaTime
            ballPosition = Vector2Add(ballPosition, Vector2Scale(velocity, deltaTime));

            // Incrementamos la velocidad en Y si se usa la gravedad
            // ∆(V.y) = gravity * ∆(t) --> V.y = V.y + gravity * deltaTime
            if (useGravity) velocity.y(velocity.y() + gravity * deltaTime);

            if (ballPosition.x() <= radius || ballPosition.x() >= GetScreenWidth() - radius) {
                velocity.x(velocity.x() * -1f);
            }

            if (ballPosition.y() <= radius || ballPosition.y() >= GetScreenHeight() - radius) {
                velocity.y(velocity.y() * -0.95f);
            }

            ballPosition = Vector2Clamp(
                    ballPosition,
                    newVector2(radius, radius),
                    newVector2(GetScreenWidth() - radius, GetScreenHeight() - radius)
            );
        }


        BeginDrawing();
        ClearBackground(DARKGRAY);
        DrawCircleV(ballPosition, radius, ballColor);


        DrawText("Presiona ESPACIO para pausar el movimiento de la bola", 10, GetScreenHeight() - 25, 20, LIGHTGRAY);

        if (useGravity)
            DrawText("GRAVEDAD: ON (Presiona G para deshabilitar)", 10, GetScreenHeight() - 50, 20, LIME);
        else
            DrawText("GRAVEDAD: OFF (Presiona G para habilitar)", 10, GetScreenHeight() - 50, 20, RED);

        // En pausa mostrar el mensaje de pausa
        String message = "PAUSA";
        int fontSize = 70;
        int messageWidth = MeasureText(message, fontSize);
        if (paused)
            DrawText(message,
                    (GetScreenWidth() - messageWidth) / 2,
                    (GetScreenHeight() - fontSize) / 2, fontSize, RAYWHITE);

        DrawFPS(GetScreenWidth() - 110, 20);
        EndDrawing();
    }
    CloseWindow();
}