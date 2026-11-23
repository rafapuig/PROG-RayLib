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

final int SPRITE_ANIMATION_FRAMES = 4;

final float SCALE = 24f;

int ANIMATION_FRAMES_SPEED = 8; // Frames por segundo de la animación

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de sprite");
    int frameRate = GetMonitorRefreshRate(1);
    SetTargetFPS(frameRate);

    Texture texture = AssetManager.loadTexture("character.png");

    Rectangle[] sprites = new Rectangle[SPRITE_ANIMATION_FRAMES];

    for (int i = 0; i < SPRITE_ANIMATION_FRAMES; i++) {
        sprites[i] = newRectangle(
                SPRITE_OFFSET_X + SPRITE_WIDTH * i, SPRITE_OFFSET_Y,
                SPRITE_WIDTH, SPRITE_HEIGHT
        );
    }

    Vector2 position = newVector2(0, 0);

    Rectangle actor = newRectangle(
            position.x(), position.y(),
            sprites[0].width() * SCALE, sprites[0].height() * SCALE);


    int framesCounter = 0;
    int currentAnimationFrame = 0;

    while (!WindowShouldClose()) {

        framesCounter++;

        if (framesCounter >= frameRate / ANIMATION_FRAMES_SPEED) {
            framesCounter = 0;

            currentAnimationFrame++;
            if (currentAnimationFrame >= SPRITE_ANIMATION_FRAMES) currentAnimationFrame = 0;
        }

        BeginDrawing();
        ClearBackground(LIGHTGRAY);

        DrawTexturePro(texture, sprites[currentAnimationFrame], actor, Vector2Zero(), 0f, WHITE);

        EndDrawing();
    }

    UnloadTexture(texture);
    CloseWindow();
}