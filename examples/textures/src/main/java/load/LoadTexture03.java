import org.bytedeco.javacpp.Loader;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;
import static org.bytedeco.javacpp.Loader.extractResource;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de carga de textura");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Carga las texturas desde la carpeta de recursos del módulo
    File file = Loader.cacheResource("Overworld.png");
    Texture texture = LoadTexture(file.getAbsolutePath());

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(LIGHTGRAY);
        DrawTexture(texture, 0, 0, WHITE);
        EndDrawing();
    }
    // Las texturas se descargan de la GPU cuando termina el programa
    UnloadTexture(texture);
    CloseWindow();
}