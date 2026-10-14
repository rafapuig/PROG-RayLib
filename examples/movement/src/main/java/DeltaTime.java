import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

void main() {

    InitWindow(screenWidth, screenHeight, "Ejemplo - Delta Time en Raylib");

    Vector2 deltaBall = new Vector2().x(0).y(screenHeight / 3f);
    Vector2 frameBall = new Vector2().x(0).y(screenHeight * 2f / 3f);

    final float speed = 10f;
    final float radius = 50f;
    final float initialFps = 60f; //GetMonitorRefreshRate(1) / 2f;

    int currentFps = (int) initialFps;

    SetTargetFPS(currentFps);

    while (!WindowShouldClose()) {
        // Update
        float mouseWheelInput = GetMouseWheelMove();
        if (mouseWheelInput != 0) {
            currentFps += (int) mouseWheelInput;
            if (currentFps < 0) currentFps = 0;
            SetTargetFPS(currentFps);
        }

        // Actualizar la posición de las bolas

        /**
         * Se incrementa la x en 10 unidades por cada frame
         * Si FPS es 60 se incrementa 60 veces en un segundo y cada vez 10 unidades
         * En un segundo habrá avanzado 10 * 60 = 600 unidades --> velocidad 600 unidades / segundo
         *
         * Si los FPS cambian el movimiento de la bola se acelerará o ralentizará
         */
        frameBall.x(frameBall.x() + speed);
        if (frameBall.x() > screenWidth) frameBall.x(0);

        if (IsKeyPressed(KEY_R)) {
            deltaBall.x(0);
            frameBall.x(0);
            currentFps = (int) initialFps;
            SetTargetFPS(currentFps);
        }

        /**
         * GetFrameTime() devuelve del tiempo que costó dibujar el frame anterior en segundos
         * Se usa este tiempo (delta time) para mover los objetos a una velocidad independiente del framerate
         */
        float deltaTime = GetFrameTime();

        /* Multiplicar la velocidad por initial FPS es para igualar la velocidad inicial de ambas bolas
         * FPS * deltaTime = FPS * 1 / FPS = 1 (se cancelan) initialFps * deltaTime debería ser 1
         */
        deltaBall.x(deltaBall.x() + speed * initialFps * deltaTime);
        if (deltaBall.x() > screenWidth) deltaBall.x(0);


        // Draw
        BeginDrawing();
        ClearBackground(DARKGRAY);

        // Dibujar ambas bolas
        DrawCircleV(deltaBall, radius, PINK);
        DrawCircleV(frameBall, radius, SKYBLUE);

        final int textOffsetX = 20;
        final int fontSize = 32;
        final int textLineSpace = 40;
        final int textMargin = 50;

        // Dibujar el texto overlay
        DrawText("FPS: " + GetFPS() + " [target: " + currentFps + "]",
                textOffsetX, 20, fontSize, LIME);

        DrawText(String.format("Frame Time: %.3f ms", GetFrameTime()),
                textOffsetX, 20 + textLineSpace, fontSize, BEIGE);

        DrawText("Usa la rueda del mouse para cambiar los FPS, r para resetear",
                textOffsetX, 20 + 2 * textLineSpace, fontSize, RAYWHITE);


        DrawText("FUNC: x += speed * GetFrameTime()",
                textOffsetX,
                (int) (screenHeight / 3f - radius - textMargin),
                fontSize, PINK);

        DrawText("FUNC: x += speed",
                textOffsetX,
                (int) (screenHeight * 2 / 3f - radius - textMargin),
                fontSize, SKYBLUE);

        EndDrawing();
    }
    CloseWindow();

}