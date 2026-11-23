import org.bytedeco.javacpp.Loader;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de carga de textura");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Carga las texturas desde la carpeta de recursos del módulo
    File file = Loader.extractResource("objects.png", null,null,null);
    Texture texture = LoadTexture(file.getAbsolutePath());

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(LIGHTGRAY);
        DrawTexture(texture, 0, 0, WHITE);
        EndDrawing();
    }
    UnloadTexture(texture);
    CloseWindow();
}