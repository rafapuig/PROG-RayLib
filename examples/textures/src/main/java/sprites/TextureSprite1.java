import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

final int SPRITE_OFFSET_X = 0;
final int SPRITE_OFFSET_Y = 5;
final int SPRITE_WIDTH = 16;
final int SPRITE_HEIGHT = 24;

final float SCALE = 32f;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de sprite con escalado");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Texture texture = AssetManager.loadTexture("character.png");

    Rectangle sprite = newRectangle(SPRITE_OFFSET_X, SPRITE_OFFSET_Y, SPRITE_WIDTH, SPRITE_HEIGHT);

    Vector2 position = newVector2(0, 0);

    Rectangle actor = newRectangle(
            position.x(), position.y(),
            sprite.width() * SCALE, sprite.height() * SCALE
    );


    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(LIGHTGRAY);

        DrawTexturePro(texture, sprite, actor, Vector2Zero(), 0f, WHITE);

        EndDrawing();
    }
    UnloadTexture(texture);
    CloseWindow();
}