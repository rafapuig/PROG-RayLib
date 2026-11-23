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

final float SCALE = 24f;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de sprite (varios sprites)");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Texture texture = AssetManager.loadTexture("character.png");

    Rectangle sprite0 = newRectangle(SPRITE_OFFSET_X, SPRITE_OFFSET_Y, SPRITE_WIDTH, SPRITE_HEIGHT);
    Rectangle sprite1 = newRectangle(SPRITE_OFFSET_X + SPRITE_WIDTH, SPRITE_OFFSET_Y, SPRITE_WIDTH, SPRITE_HEIGHT);
    Rectangle sprite2 = newRectangle(SPRITE_OFFSET_X + SPRITE_WIDTH * 2, SPRITE_OFFSET_Y, SPRITE_WIDTH, SPRITE_HEIGHT);
    Rectangle sprite3 = newRectangle(SPRITE_OFFSET_X + SPRITE_WIDTH * 3, SPRITE_OFFSET_Y, SPRITE_WIDTH, SPRITE_HEIGHT);

    Vector2 position = newVector2(0, 0);

    Rectangle actor0 = newRectangle(
            position.x(), position.y(),
            sprite0.width() * SCALE, sprite0.height() * SCALE
    );

    Rectangle actor1 = newRectangle(
            position.x() + actor0.width(), position.y(),
            sprite0.width() * SCALE, sprite0.height() * SCALE
    );

    Rectangle actor2 = newRectangle(
            position.x() + actor0.width() * 2, position.y(),
            sprite0.width() * SCALE, sprite0.height() * SCALE
    );

    Rectangle actor3 = newRectangle(
            position.x() + actor0.width() * 3, position.y(),
            sprite0.width() * SCALE, sprite0.height() * SCALE
    );


    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(LIGHTGRAY);

        DrawTexturePro(texture, sprite0, actor0, Vector2Zero(), 0f, WHITE);
        DrawTexturePro(texture, sprite1, actor1, Vector2Zero(), 0f, WHITE);
        DrawTexturePro(texture, sprite2, actor2, Vector2Zero(), 0f, WHITE);
        DrawTexturePro(texture, sprite3, actor3, Vector2Zero(), 0f, WHITE);

        EndDrawing();
    }
    UnloadTexture(texture);
    CloseWindow();
}