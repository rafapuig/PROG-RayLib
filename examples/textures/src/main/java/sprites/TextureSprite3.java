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

final int SPRITE_FRAMES = 4;

final float SCALE = 24f;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de sprite (con bucle)");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Texture texture = AssetManager.loadTexture("character.png");

    Rectangle[] sprites = new Rectangle[SPRITE_FRAMES];

    for (int i = 0; i < SPRITE_FRAMES; i++) {
        sprites[i] = newRectangle(
                SPRITE_OFFSET_X + SPRITE_WIDTH * i,
                SPRITE_OFFSET_Y,
                SPRITE_WIDTH, SPRITE_HEIGHT
        );
    }

    Vector2 position = newVector2(0, 0);

    Rectangle[] actors = new Rectangle[SPRITE_FRAMES];

    for (int i = 0; i < SPRITE_FRAMES; i++) {
        float xOffset = (i > 0) ? actors[i - 1].width() : 0;
        actors[i] = newRectangle(
                position.x() + xOffset * i, position.y(),
                sprites[i].width() * SCALE, sprites[i].height() * SCALE
        );
    }

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(LIGHTGRAY);

        for (int i = 0; i < SPRITE_FRAMES; i++) {
            DrawTexturePro(texture, sprites[i], actors[i], Vector2Zero(), 0f, WHITE);
        }

        EndDrawing();
    }
    UnloadTexture(texture);
    CloseWindow();
}