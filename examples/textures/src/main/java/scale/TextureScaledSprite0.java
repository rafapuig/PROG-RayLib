import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

final int VIRTUAL_HEIGHT = SCREEN_HEIGHT / 32;
final int VIRTUAL_WIDTH = VIRTUAL_HEIGHT * 4 / 3;

final int SPRITE_OFFSET_X = 0;
final int SPRITE_OFFSET_Y = 5;
final int SPRITE_WIDTH = 16;
final int SPRITE_HEIGHT = 24;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de sprite");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Texture texture = AssetManager.loadTexture("character.png");

    Rectangle sprite = newRectangle(SPRITE_OFFSET_X, SPRITE_OFFSET_Y, SPRITE_WIDTH, SPRITE_HEIGHT);

    Vector2 position = newVector2(0, 0);
    RenderTexture renderTexture = LoadRenderTexture(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

    while (!WindowShouldClose()) {

        BeginTextureMode(renderTexture);
        ClearBackground(LIGHTGRAY);
        DrawTextureRec(texture, sprite, position, WHITE);
        EndTextureMode();

        // Calculate scale
        float scale = Math.min(
                (float) GetScreenWidth() / VIRTUAL_WIDTH,
                (float) GetScreenHeight() / VIRTUAL_HEIGHT
        );

        // Calculate destination dimensions
        float width = VIRTUAL_WIDTH * scale;
        float height = VIRTUAL_HEIGHT * scale;

        // Center the virtual screen
        float x = (GetScreenWidth() - width) / 2;
        float y = (GetScreenHeight() - height) / 2;

        BeginDrawing();
        ClearBackground(BLACK);

        /**
         * El eje vertical se invierte al dibujar un RenderTexture.         *
         * Por eso utilizamos -virtualHeight en el rectángulo source. Es necesario para que la imagen aparezca correctamente orientada
         */
        DrawTexturePro(
                renderTexture.texture(),
                newRectangle(0, 0, VIRTUAL_WIDTH, -VIRTUAL_HEIGHT),
                newRectangle(x, y, width, height),
                newVector2(0, 0),
                0,
                WHITE
        );

        EndDrawing();
    }
    UnloadTexture(texture);
    CloseWindow();
}