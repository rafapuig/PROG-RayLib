import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de textura con DrawTextureV");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Texture texture = AssetManager.loadTexture("character.png");

    Vector2 position = newVector2(0, 0);

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(LIGHTGRAY);
        DrawTextureV(texture, position, WHITE);
        EndDrawing();
    }
    UnloadTexture(texture);
    CloseWindow();
}