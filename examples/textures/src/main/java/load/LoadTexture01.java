import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;
import static org.bytedeco.javacpp.Loader.extractResource;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

void main() {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de carga de textura");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Las texturas se cargan en la GPU después de iniciar la ventana (contexto de OpenGL)
    Texture texture = LoadTexture("assets/textures/character.png");

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