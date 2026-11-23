import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Raylib.*;

final int TILE_SIZE = 16;

final float SCALE = 5f;

final int TILE_ROWS = 15;
final int TILE_COLS = 15 * 16 / 9;

final int SCREEN_HEIGHT = TILE_SIZE * TILE_ROWS;
final int SCREEN_WIDTH = TILE_SIZE * TILE_COLS;

final int SPRITE_WIDTH = TILE_SIZE;
final int SPRITE_HEIGHT = TILE_SIZE * 3 / 2;
final int SPRITE_ANIMATION_FRAMES = 4;

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
    Texture characterTexture = AssetManager.loadTexture("character.png");
    Texture overworldTexture = AssetManager.loadTexture("Overworld.png");

    Rectangle grassSprite = newRectangle(0, 0, TILE_SIZE, TILE_SIZE);
    Rectangle mapTile = newRectangle(0, 0, TILE_SIZE * SCALE, TILE_SIZE * SCALE);

    Rectangle[][] playerSprites = new Rectangle[DIRECTIONS][SPRITE_ANIMATION_FRAMES];

    for (int n = 0; n < DIRECTIONS; n++) {
        for (int i = 0; i < SPRITE_ANIMATION_FRAMES; i++) {
            playerSprites[n][i] = newRectangle(
                    TILE_SIZE * i,
                    SPRITESHEET_Y_OFFSET + n * TILE_SIZE * 2,
                    SPRITE_WIDTH,
                    SPRITE_HEIGHT);
        }
    }

    Rectangle player = newRectangle(0, 0, SPRITE_WIDTH * SCALE, SPRITE_HEIGHT * SCALE);


    //int framesCounter = 0;
    int currentAnimationFrame = 0;

    float animationTimer = 0f;

    float playerPositionX = 0;
    float playerPositionY = 0;

    int orientation = DOWN;
    int lastOrientation = orientation;

    //var playerSprite = playerSprites[orientation][currentAnimationFrame];

    var vector2Zero = Vector2Zero();

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

        if (lastOrientation != orientation) {
            currentAnimationFrame = 0;
        }
        lastOrientation = orientation;


        // Update

        try (var direction = Vector2Normalize(input)) {
            playerPositionX += direction.x() * SPEED * GetFrameTime();
            playerPositionY += direction.y() * SPEED * GetFrameTime();
        }

        if (playerPositionX + SPRITE_WIDTH * SCALE > GetScreenWidth()) {
            playerPositionX = GetScreenWidth() - SPRITE_WIDTH * SCALE;
        } else if (playerPositionX < 0) {
            playerPositionX = 0;
        }
        if (playerPositionY + SPRITE_HEIGHT * SCALE > GetScreenHeight()) {
            playerPositionY = GetScreenHeight() - SPRITE_HEIGHT * SCALE;
        } else if (playerPositionY < 0) {
            playerPositionY = 0;
        }

        if (animationTimer >= 1f / FRAMES_ANIMATION_SPEED) {

            animationTimer -= 1f / FRAMES_ANIMATION_SPEED;

            boolean isMoving = Vector2Equals(Vector2Zeros(), input) == 0;
            boolean isIdleFrame = currentAnimationFrame % 2 != 0;

            if (isMoving || isIdleFrame) {
                if (++currentAnimationFrame >= SPRITE_ANIMATION_FRAMES) currentAnimationFrame = 0;
            }
        }

        animationTimer += GetFrameTime();

        // Draw
        BeginDrawing();
        ClearBackground(DARKBROWN);

        for (int i = 0; i < TILE_ROWS; i++) {
            for (int j = 0; j <= TILE_COLS; j++) {

                mapTile.x(j * TILE_SIZE * SCALE);
                mapTile.y(i * TILE_SIZE * SCALE);

                DrawTexturePro(
                        overworldTexture,
                        grassSprite, mapTile,
                        vector2Zero, 0f, WHITE);
            }
        }

        player.x(playerPositionX);
        player.y(playerPositionY);

        var playerSprite = playerSprites[orientation][currentAnimationFrame];

        DrawTexturePro(
                characterTexture,
                playerSprite, player,
                vector2Zero, 0f, WHITE);

        EndDrawing();
    }

    UnloadTexture(characterTexture);
    UnloadTexture(overworldTexture);

    CloseWindow();
}