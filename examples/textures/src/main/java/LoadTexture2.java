import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;
import static org.bytedeco.javacpp.Loader.extractResource;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de carga de textura");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Las texturas se cargan después de iniciar la ventana (contexto de OpenGL)
    File file = extractResource("objects.png", null,null,null);
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