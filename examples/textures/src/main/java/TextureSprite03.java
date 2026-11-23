import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Colors.WHITE;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int TILE_SIZE = 16;

final int SPRITE_TOP_MARGIN = 6;
final float SPRITE_SCALE = 4;
final int SPRITE_WIDTH = TILE_SIZE;
final int SPRITE_HEIGHT = TILE_SIZE * 2;

void main() throws IOException {

    InitWindow(1920, 1080, "");
    final int frameRate = GetMonitorRefreshRate(1);
    SetTargetFPS(frameRate);

    Texture texture = AssetManager.loadTexture("character.png");

    Rectangle[] sprites = new Rectangle[4];

    for (int i = 0; i < 4; i++) {
        sprites[i] = newRectangle(TILE_SIZE * i, SPRITE_TOP_MARGIN, SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(RAYWHITE);

        for (var i = 0; i < 4; i++) {
            DrawTexturePro(
                    texture,
                    sprites[i],
                    newRectangle(SPRITE_WIDTH * SPRITE_SCALE * i, 0, SPRITE_WIDTH * SPRITE_SCALE, SPRITE_HEIGHT * SPRITE_SCALE),
                    newVector2(0 ,0),
                    0,
                    WHITE
            );
        }

        EndDrawing();
    }
    CloseWindow();

}