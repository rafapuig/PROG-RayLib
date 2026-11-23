import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int TILE_SIZE = 16;

final int SPRITE_TOP_MARGIN = 6;
final float SPRITE_SCALE = 4;
final int SPRITE_WIDTH = TILE_SIZE;
final int SPRITE_HEIGHT = TILE_SIZE * 2;
final int SPRITE_FRAMES = 4;

void main() throws IOException {

    InitWindow(1920, 1080, "");
    final int frameRate = GetMonitorRefreshRate(1);
    SetTargetFPS(frameRate);

    // Cargar las spritesheet
    Texture texture = AssetManager.loadTexture("character.png");

    Rectangle[] sprites = new Rectangle[SPRITE_FRAMES];

    for (int i = 0; i < SPRITE_FRAMES; i++) {
        sprites[i] = newRectangle(TILE_SIZE * i, SPRITE_TOP_MARGIN, SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    int framesCounter = 0;
    int framesSpeed = 8;
    int currentFrame = 0;

    float positionX = 0;
    float positionY = 0;

    float speed = 500;

    while (!WindowShouldClose()) {

        var input = Vector2Zero();

        if (IsKeyDown(KEY_DOWN)) {
            input.y(input.y() + 1);
        }
        if (IsKeyDown(KEY_UP)) {
            input.y(input.y() - 1);
        }

        positionY += input.y() * speed * GetFrameTime();

        framesCounter++;

        if (framesCounter >= frameRate / framesSpeed) {
            framesCounter = 0;

            boolean isMoving = Vector2Equals(Vector2Zero(), input) == 0;
            boolean isIdleFrame = currentFrame % 2 != 0;

            if (isMoving || isIdleFrame) {
                if (++currentFrame >= SPRITE_FRAMES) currentFrame = 0;
            }
        }

        BeginDrawing();
        ClearBackground(DARKBROWN);

        DrawTexturePro(
                texture,
                sprites[currentFrame],
                newRectangle(positionX, positionY, SPRITE_WIDTH * SPRITE_SCALE, SPRITE_HEIGHT * SPRITE_SCALE),
                newVector2(0, 0),
                0,
                WHITE);

        EndDrawing();
    }

    CloseWindow();

}