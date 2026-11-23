import org.bytedeco.javacpp.Loader;

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newRectangle;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

final int VIRTUAL_HEIGHT = SCREEN_HEIGHT / 6;
final int VIRTUAL_WIDTH = VIRTUAL_HEIGHT * 4 / 3;

void main() throws IOException {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Ejemplo de escalado de pantalla virtual");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Texture texture = LoadTexture(Loader.cacheResource("log.png").getAbsolutePath());

    Vector2 position = Vector2Zero();

    RenderTexture renderTexture = LoadRenderTexture(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

    while (!WindowShouldClose()) {

        BeginTextureMode(renderTexture);
        ClearBackground(LIGHTGRAY);

        DrawTextureV(texture, position, WHITE);

        EndTextureMode();

        // Calcular el factor de escala
        float scale = Math.min(
                (float) GetScreenWidth() / VIRTUAL_WIDTH,
                (float) GetScreenHeight() / VIRTUAL_HEIGHT
        );

        // Calcular las dimensiones de destino
        // manteniendo la relación de aspecto
        float width = VIRTUAL_WIDTH * scale;
        float height = VIRTUAL_HEIGHT * scale;

        // Centrar la pantalla virtual
        float x = (GetScreenWidth() - width) / 2;
        float y = (GetScreenHeight() - height) / 2;

        BeginDrawing();
        ClearBackground(BLACK);

        /**
         * El eje vertical se invierte al dibujar un RenderTexture.
         * Por eso utilizamos -virtualHeight en el rectángulo source.
         * Es necesario para que la imagen aparezca correctamente orientada
         */
        DrawTexturePro(
                renderTexture.texture(),
                newRectangle(0, 0, VIRTUAL_WIDTH, -VIRTUAL_HEIGHT),
                newRectangle(x, y, width, height),
                Vector2Zero(),
                0f,
                WHITE
        );

        EndDrawing();
    }
    UnloadTexture(texture);
    CloseWindow();
}