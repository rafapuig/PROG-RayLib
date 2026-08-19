import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

final Vector2 topLeftCorner = Vector2Zero();
final Vector2 bottomRightCorner = newVector2(screenWidth, screenHeight);

final float ballRadius = 60f;
final int speed = 500; //pixels / segundo
final Vector2 ballMinPosition = Vector2Add(topLeftCorner, newVector2(ballRadius, ballRadius));
final Vector2 ballMaxPosition = Vector2Subtract(bottomRightCorner, newVector2(ballRadius, ballRadius));

void main() {

    var ballPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    InitWindow(screenWidth, screenHeight, "Entrada de teclado en RayLib");
    //SetTargetFPS(GetMonitorRefreshRate(1));

    while (!WindowShouldClose()) {

        var input = Vector2Zero();
        // Actualizar
        if (IsKeyDown(KEY_UP)) input.y(-1); //  ballPosition.y(ballPosition.y() - speed);
        if (IsKeyDown(KEY_DOWN)) input.y(1); //ballPosition.y(ballPosition.y() + speed);
        if (IsKeyDown(KEY_LEFT)) input.x(-1); //ballPosition.x(ballPosition.x() - speed);
        if (IsKeyDown(KEY_RIGHT)) input.x(1); //ballPosition.x(ballPosition.x() + speed);

        input = Vector2Normalize(input);

        ballPosition = Vector2Add(ballPosition, Vector2Scale(input, speed * GetFrameTime()));

        // Clampar para no superar los limites de la pantalla
        ballPosition = Vector2Clamp(ballPosition, ballMinPosition, ballMaxPosition);

        // Dibujar
        BeginDrawing();
        ClearBackground(DARKGRAY);

        DrawCircleV(ballPosition, ballRadius, PINK);

        DrawText("Mueve la bola con las flechas cursores", 20, 20, 40, RAYWHITE);
        DrawText(String.format("Vector de entrada: X= %.1f, Y=%.1f", input.x(), input.y())
                , 20, 80, 40, LIME);

        DrawFPS(screenWidth - 120, screenHeight - 30);
        EndDrawing();
    }
    CloseWindow();

}