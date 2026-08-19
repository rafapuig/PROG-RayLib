import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

final int speed = 5; //pixels / frame

void main() {

    var ballPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    InitWindow(screenWidth, screenHeight, "Entrada de teclado en RayLib");
    SetTargetFPS(GetMonitorRefreshRate(1));



    while (!WindowShouldClose()) {

        var input = Vector2Zero();
        // Actualizar
        if (IsKeyDown(KEY_UP)) input.y(-1); //  ballPosition.y(ballPosition.y() - speed);
        if (IsKeyDown(KEY_DOWN)) input.y(1); //ballPosition.y(ballPosition.y() + speed);
        if (IsKeyDown(KEY_LEFT)) input.x(-1); //ballPosition.x(ballPosition.x() - speed);
        if (IsKeyDown(KEY_RIGHT)) input.x(1); //ballPosition.x(ballPosition.x() + speed);

        input = Vector2Normalize(input);

        ballPosition = Vector2Add(ballPosition, Vector2Scale(input, speed));

        // Dibujar
        BeginDrawing();
        ClearBackground(DARKGRAY);

        DrawCircleV(ballPosition, 60f, PINK);

        DrawText("Mueve la bola con las flechas cursores", 20, 20, 40, RAYWHITE);
        DrawText(String.format("Vector de entrada: X= %.1f, Y=%.1f", input.x(), input.y())
                , 20, 80, 40, LIME);

        EndDrawing();
    }
    CloseWindow();

}