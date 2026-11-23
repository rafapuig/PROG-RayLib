import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int TILE_SIZE = 16;

final float SCALE = 5f;

final int TILE_ROWS = 15;
final int TILE_COLS = 15 * 16 / 9;

final int SCREEN_HEIGHT = TILE_SIZE * TILE_ROWS;
final int SCREEN_WIDTH = TILE_SIZE * TILE_COLS;

final int SPRITE_WIDTH = TILE_SIZE;
final int SPRITE_HEIGHT = TILE_SIZE * 3 / 2;
final int SPRITE_FRAMES = 4;

final int DIRECTIONS = 4;
final int DOWN = 0;
final int UP = 2;
final int RIGHT = 1;
final int LEFT = 3;

final int SPRITESHEET_Y_OFFSET = 5;

int FRAMES_ANIMATION_SPEED = 8; // En frames por segundo

float SPEED = 100 * SCALE;

void main() throws IOException {

    InitWindow((int) (SCREEN_WIDTH * SCALE), (int) (SCREEN_HEIGHT * SCALE), "Player con textura");
    final int frameRate = GetMonitorRefreshRate(1);
    SetTargetFPS(frameRate);

    // Cargar las spritesheet
    Texture texture = AssetManager.loadTexture("character.png");
    Texture worldTexture = AssetManager.loadTexture("Overworld.png");

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
    int currentFrame = 0;

    float positionX = 0;
    float positionY = 0;

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

        positionX += direction.x() * SPEED * GetFrameTime();
        positionY += direction.y() * SPEED * GetFrameTime();

        if (positionX + SPRITE_WIDTH * SCALE > GetScreenWidth()) {
            positionX = GetScreenWidth() - SPRITE_WIDTH * SCALE;
        } else if (positionX < 0) {
            positionX = 0;
        }
        if (positionY + SPRITE_HEIGHT * SCALE > GetScreenHeight()) {
            positionY = GetScreenHeight() - SPRITE_HEIGHT * SCALE;
        } else if (positionY < 0) {
            positionY = 0;
        }


        framesCounter++;

        if (framesCounter >= frameRate / FRAMES_ANIMATION_SPEED) {
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


        for (int i = 0; i < TILE_ROWS; i++) {
            for (int j = 0; j <= TILE_COLS; j++) {
                DrawTexturePro(
                        worldTexture,
                        newRectangle(0, 0, TILE_SIZE, TILE_SIZE),
                        newRectangle(j * TILE_SIZE * SCALE, i * TILE_SIZE * SCALE, TILE_SIZE * SCALE, TILE_SIZE * SCALE),
                        newVector2(0, 0),
                        0,
                        WHITE
                );
            }
        }


        var destination = newRectangle(
                positionX, positionY,
                SPRITE_WIDTH * SCALE,
                SPRITE_HEIGHT * SCALE
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