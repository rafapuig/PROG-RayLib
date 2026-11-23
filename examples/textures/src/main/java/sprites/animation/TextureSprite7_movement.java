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
final int SPRITE_SPACE = 8;

final float SCALE = 5f;

final int SPRITE_ANIMATION_FRAMES = 4;
int ANIMATION_FRAMES_SPEED = 8; // Frames por segundo de la animación

final int DIRECTIONS = 4;
final int DOWN = 0;
final int UP = 2;
final int RIGHT = 1;
final int LEFT = 3;

final float SPEED = 100 * SCALE;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de sprite");
    final int frameRate = GetMonitorRefreshRate(1);
    SetTargetFPS(frameRate);

    final Texture texture = AssetManager.loadTexture("character.png");

    final Rectangle[][] sprites = new Rectangle[DIRECTIONS][SPRITE_ANIMATION_FRAMES];

    for (int direction = 0; direction < DIRECTIONS; direction++) {
        for (int frame = 0; frame < SPRITE_ANIMATION_FRAMES; frame++) {
            sprites[direction][frame] = newRectangle(
                    SPRITE_OFFSET_X + SPRITE_WIDTH * frame,
                    SPRITE_OFFSET_Y +  (SPRITE_HEIGHT + SPRITE_SPACE) * direction,
                    SPRITE_WIDTH, SPRITE_HEIGHT
            );
        }
    }

    final float actorWidth = sprites[0][0].width() * SCALE;
    final float actorHeight = sprites[0][0].height() * SCALE;

    final Vector2 position = newVector2(
            (SCREEN_WIDTH - actorWidth) / 2,
            (SCREEN_HEIGHT - actorHeight) / 2
    );

    final Rectangle actor = newRectangle(
            position.x(), position.y(),
            actorWidth, actorHeight);


    int framesCounter = 0;
    int currentAnimationFrame = 0;
    int orientation = DOWN;

    while (!WindowShouldClose()) {

        try(Vector2 input = Vector2Zero()) {

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

            Vector2 direccion = Vector2Normalize(input);

            actor.x(actor.x() + direccion.x() * SPEED * GetFrameTime());
            actor.y(actor.y() + direccion.y() * SPEED * GetFrameTime());
        }

        framesCounter++;

        if (framesCounter >= frameRate / ANIMATION_FRAMES_SPEED) {
            framesCounter = 0;

            currentAnimationFrame++;
            if (currentAnimationFrame >= SPRITE_ANIMATION_FRAMES) currentAnimationFrame = 0;
        }

        var actorSprite = sprites[orientation][currentAnimationFrame];


        BeginDrawing();
        ClearBackground(DARKBROWN);

        DrawTexturePro(texture, actorSprite, actor, Vector2Zero(), 0f, WHITE);

        EndDrawing();
    }

    UnloadTexture(texture);
    CloseWindow();
}