import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int TILE_SIZE = 16;

final float SPRITE_SCALE = 4;
final int SPRITE_WIDTH = TILE_SIZE;
final int SPRITE_HEIGHT = TILE_SIZE * 3 / 2;
final int SPRITE_FRAMES = 4;

final int DIRECTIONS = 4;
final int DOWN = 0;
final int UP = 2;
final int RIGHT = 1;
final int LEFT = 3;

final int SPRITESHEET_Y_OFFSET = 5;
final int SPRITE_DOWN_Y_OFFSET = SPRITESHEET_Y_OFFSET + TILE_SIZE * 2 * 0;
final int SPRITE_RIGHT_Y_OFFSET = SPRITESHEET_Y_OFFSET + TILE_SIZE * 2 * 1;
final int SPRITE_UP_Y_OFFSET = SPRITESHEET_Y_OFFSET + TILE_SIZE * 2 * 2;
final int SPRITE_LEFT_Y_OFFSET = SPRITESHEET_Y_OFFSET + TILE_SIZE * 2 * 3;

void main() throws IOException {

    InitWindow(1920, 1080, "");
    final int frameRate = GetMonitorRefreshRate(1);
    SetTargetFPS(frameRate);

    // Cargar las spritesheet
    Texture texture = AssetManager.loadTexture("character.png");

    Rectangle[][] sprites = new Rectangle[DIRECTIONS][SPRITE_FRAMES];

    for (int n = 0; n < DIRECTIONS; n++) {
        for (int i = 0; i < SPRITE_FRAMES; i++) {
            sprites[n][i] = newRectangle(
                    TILE_SIZE * i,
                    SPRITESHEET_Y_OFFSET + n * TILE_SIZE * 2,
                    SPRITE_WIDTH,
                    SPRITE_HEIGHT);
        }
    }

    int framesCounter = 0;
    int framesSpeed = 8;
    int currentFrame = 0;

    float positionX = 0;
    float positionY = 0;

    float speed = 400;
    int orientation = DOWN;
    //int lastOrientation = orientation;

    while (!WindowShouldClose()) {

        var input = Vector2Zero();

        if (IsKeyDown(KEY_UP)) {
            input.y(input.y() - 1);
            orientation = UP;
        }
        if (IsKeyDown(KEY_DOWN)) {
            input.y(input.y() + 1);
            orientation = DOWN;
        }
        if (IsKeyDown(KEY_RIGHT)) {
            input.x(input.x() + 1);
            orientation = RIGHT;
        }
        if (IsKeyDown(KEY_LEFT)) {
            input.x(input.x() - 1);
            orientation = LEFT;
        }


        // Update

        var direction = Vector2Normalize(input);

        positionX += direction.x() * speed * GetFrameTime();
        positionY += direction.y() * speed * GetFrameTime();


        framesCounter++;

        if (framesCounter >= frameRate / framesSpeed) {
            framesCounter = 0;

            boolean isMoving = Vector2Equals(Vector2Zeros(), input) == 0;
            boolean isIdleFrame = currentFrame % 2 != 0;

            if (isMoving || isIdleFrame) {
                if (++currentFrame >= SPRITE_FRAMES) currentFrame = 0;
            }
        }

        // Draw
        BeginDrawing();
        ClearBackground(DARKBROWN);

        var destination = newRectangle(
                positionX, positionY,
                SPRITE_WIDTH * SPRITE_SCALE,
                SPRITE_HEIGHT * SPRITE_SCALE
        );

        DrawTexturePro(
                texture,
                sprites[orientation][currentFrame],
                destination,
                newVector2(0, 0),
                0,
                WHITE);

        EndDrawing();
    }

    CloseWindow();

}