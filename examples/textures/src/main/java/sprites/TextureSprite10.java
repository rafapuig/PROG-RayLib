import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;


final float SCALE = 5f;

final int TILE_SIZE = 16;

final int TILE_ROWS = 15;
final int TILE_COLS = TILE_ROWS * 16 / 9;

final int SCREEN_WIDTH = TILE_SIZE * TILE_COLS;
final int SCREEN_HEIGHT = TILE_SIZE * TILE_ROWS;

final int SPRITE_OFFSET_X = 0;
final int SPRITE_OFFSET_Y = 5;
final int SPRITE_WIDTH = 16;
final int SPRITE_HEIGHT = 24;
final int SPRITE_SPACE = 8;

final int SPRITE_ANIMATION_FRAMES = 4;
int ANIMATION_FRAMES_SPEED = 8; // Frames por segundo de la animación

final int DIRECTIONS = 4;
final int DOWN = 0;
final int UP = 2;
final int RIGHT = 1;
final int LEFT = 3;

final float SPEED = 100 * SCALE;

void main() throws IOException {

    InitWindow((int) (SCREEN_WIDTH * SCALE), (int) (SCREEN_HEIGHT * SCALE), "Ejemplo de sprite");
    final int frameRate = GetMonitorRefreshRate(1);
    SetTargetFPS(frameRate);

    final Texture characterTexture = AssetManager.loadTexture("character.png");

    final Rectangle[][] sprites = new Rectangle[DIRECTIONS][SPRITE_ANIMATION_FRAMES];

    for (int direction = 0; direction < DIRECTIONS; direction++) {
        for (int frame = 0; frame < SPRITE_ANIMATION_FRAMES; frame++) {
            sprites[direction][frame] = newRectangle(
                    SPRITE_OFFSET_X + SPRITE_WIDTH * frame,
                    SPRITE_OFFSET_Y + (SPRITE_HEIGHT + SPRITE_SPACE) * direction,
                    SPRITE_WIDTH, SPRITE_HEIGHT
            );
        }
    }

    final float actorWidth = sprites[0][0].width() * SCALE;
    final float actorHeight = sprites[0][0].height() * SCALE;

    final Vector2 position = newVector2((SCREEN_WIDTH - actorWidth) / 2, (SCREEN_HEIGHT - actorHeight) / 2);

    final Rectangle player = newRectangle(
            position.x(), position.y(),
            actorWidth, actorHeight);

    final Texture overworldTexture = AssetManager.loadTexture("Overworld.png");
    final Rectangle grassSprite = newRectangle(TILE_SIZE * 0, TILE_SIZE * 0, TILE_SIZE, TILE_SIZE);
    final Rectangle floorSprite1 = newRectangle(TILE_SIZE * 12, TILE_SIZE * 13, TILE_SIZE, TILE_SIZE);
    final Rectangle floorSprite2 = newRectangle(TILE_SIZE * 12, TILE_SIZE * 14, TILE_SIZE, TILE_SIZE);

    final Rectangle grassSprite1 = newRectangle(TILE_SIZE * 12, TILE_SIZE * 5, TILE_SIZE, TILE_SIZE);
    final Rectangle grassSprite2 = newRectangle(TILE_SIZE * 12, TILE_SIZE * 6, TILE_SIZE, TILE_SIZE);

    final Rectangle mapTile = newRectangle(0, 0, TILE_SIZE * SCALE, TILE_SIZE * SCALE);


    int framesCounter = 0;
    int currentAnimationFrame = 0;
    int orientation = DOWN;

    final Vector2 vector2Zero = Vector2Zero();

    while (!WindowShouldClose()) {

        Vector2 input = Vector2Zero();

        if (IsKeyDown(KEY_UP)) {
            input.y(input.y() - 1);
            orientation = UP;
        }
        if (IsKeyDown(KEY_DOWN)) {
            input.y(input.y() + 1);
            orientation = DOWN;
        }
        if (IsKeyDown(KEY_LEFT)) {
            input.x(input.x() - 1);
            orientation = LEFT;
        }
        if (IsKeyDown(KEY_RIGHT)) {
            input.x(input.x() + 1);
            orientation = RIGHT;
        }

        final Vector2 direccion = Vector2Normalize(input);

        player.x(player.x() + direccion.x() * SPEED * GetFrameTime());
        player.y(player.y() + direccion.y() * SPEED * GetFrameTime());


        if (player.x() + player.width() > GetScreenWidth()) {
            player.x(GetScreenWidth() - player.width());
        } else if (player.x() < 0) {
            player.x(0);
        }
        if (player.y() + player.height() > GetScreenHeight()) {
            player.y(GetScreenHeight() - player.height());
        } else if (player.y() < 0) {
            player.y(0);
        }

        framesCounter++;

        if (framesCounter >= frameRate / ANIMATION_FRAMES_SPEED) {
            framesCounter = 0;

            boolean isMoving = Vector2Equals(Vector2Zeros(), input) == 0;
            boolean isIdleFrame = currentAnimationFrame % 2 != 0;

            if (isMoving || isIdleFrame) {

                currentAnimationFrame++;
                if (currentAnimationFrame >= SPRITE_ANIMATION_FRAMES) currentAnimationFrame = 0;
            }
        }

        var actorSprite = sprites[orientation][currentAnimationFrame];


        BeginDrawing();
        ClearBackground(DARKBROWN);

        for (int i = 0; i < TILE_ROWS; i++) {
            for (int j = 0; j <= TILE_COLS; j++) {

                mapTile.x(j * TILE_SIZE * SCALE);
                mapTile.y(i * TILE_SIZE * SCALE);

                var sprite = (i+j) % 2 == 0 ? floorSprite1 : floorSprite2;

                /*if(j < TILE_COLS  * 0.1 || j > TILE_COLS  * 0.85) {
                    sprite = grassSprite;
                }

                if(i > TILE_ROWS * 0.8) sprite = grassSprite;*/

                DrawTexturePro(
                        overworldTexture,
                        sprite, mapTile,
                        vector2Zero, 0f, WHITE);
            }
        }

        DrawTexturePro(characterTexture, actorSprite, player, vector2Zero, 0f, WHITE);

        EndDrawing();
    }

    UnloadTexture(characterTexture);
    CloseWindow();
}