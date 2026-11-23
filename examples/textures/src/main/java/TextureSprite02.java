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

    var sprite0 = newRectangle(TILE_SIZE * 0, SPRITE_TOP_MARGIN, SPRITE_WIDTH, SPRITE_HEIGHT);
    var sprite1 = newRectangle(TILE_SIZE * 1, SPRITE_TOP_MARGIN, SPRITE_WIDTH, SPRITE_HEIGHT);
    var sprite2 = newRectangle(TILE_SIZE * 2, SPRITE_TOP_MARGIN, SPRITE_WIDTH, SPRITE_HEIGHT);
    var sprite3 = newRectangle(TILE_SIZE * 3, SPRITE_TOP_MARGIN, SPRITE_WIDTH, SPRITE_HEIGHT);


    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(RAYWHITE);

        DrawTexturePro(
                texture,
                newRectangle(0, SPRITE_TOP_MARGIN, TILE_SIZE, TILE_SIZE * 2),
                newRectangle(0, 0, TILE_SIZE * SPRITE_SCALE, TILE_SIZE * SPRITE_SCALE * 2),
                newVector2(0, 0),
                0,
                WHITE
        );

        DrawTexturePro(
                texture,
                newRectangle(TILE_SIZE, SPRITE_TOP_MARGIN, TILE_SIZE, TILE_SIZE * 2),
                newRectangle(TILE_SIZE * SPRITE_SCALE, 0, TILE_SIZE * SPRITE_SCALE, TILE_SIZE * SPRITE_SCALE * 2),
                newVector2(0, 0),
                0,
                WHITE
        );
        DrawTexturePro(
                texture,
                sprite2,
                newRectangle(SPRITE_WIDTH * SPRITE_SCALE * 2, 0, SPRITE_WIDTH * SPRITE_SCALE, SPRITE_HEIGHT * SPRITE_SCALE),
                newVector2(0 ,0),
                0,
                WHITE
        );
        DrawTexturePro(
                texture,
                sprite3,
                newRectangle(SPRITE_WIDTH * SPRITE_SCALE * 3, 0, SPRITE_WIDTH * SPRITE_SCALE, SPRITE_HEIGHT * SPRITE_SCALE),
                newVector2(0 ,0),
                0,
                WHITE
        );

        EndDrawing();
    }
    CloseWindow();

}