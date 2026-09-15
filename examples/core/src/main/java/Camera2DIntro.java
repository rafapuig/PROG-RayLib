import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

void main() {

    final int screenWidth = 1280;
    final int screenHeight = 720;

    InitWindow(screenWidth, screenHeight, "Camera 2D Intro");
    SetTargetFPS(60);

    final int playerSize = 50 * 2;

    Rectangle player = new Rectangle()
            .x(400)
            .y(280)
            .width(playerSize)
            .height(playerSize);

    Rectangle building = new Rectangle()
            .x(200)
            .y(180)
            .width(playerSize * 5)
            .height(playerSize * 2);

    Camera2D camera = new Camera2D()
            // Punto 0,0 para la cámara (centro de player)
            .target(newVector2(player.x() + playerSize / 2f, player.y() + playerSize / 2f))
            // En que punto de la pantalla pintar el 0,0 (en medio)
            .offset(newVector2(screenWidth / 2f, screenHeight / 2f))
            // Con qué nivel de escala
            .zoom(1.f)
            .rotation(5f);

    while (!WindowShouldClose()) {

        // Update
        if (IsKeyDown(KEY_RIGHT)) player.x(player.x() + 2);
        if (IsKeyDown(KEY_LEFT)) player.x(player.x() - 2);

        // La cámara sigue al player
        camera.target()
                .x(player.x() + playerSize / 2f)
                .y(player.y() + playerSize / 2f);


        BeginDrawing();
        ClearBackground(DARKGRAY);

        BeginMode2D(camera);
        DrawRectangleRec(building, YELLOW);
        DrawRectangleRec(player, RED);
        EndMode2D();

        EndDrawing();
    }

    CloseWindow();

}