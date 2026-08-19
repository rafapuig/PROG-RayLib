import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.ORANGE;
import static com.raylib.Raylib.*;

// Dimensiones de la pantalla
final int screenHeight = 600;
final int screenWidth = screenHeight * 16 / 9;

// Ball
final float radius = 40f;
final float initialPosX = radius;
final float initialPosY = screenHeight / 2f;

/**
 * Velocidad en pixels / segundo
 * independiente del frame rate
 *
 *       1          200 pixels       2 pixels
 *    --------- x -------------- = ------------
 *     100 FPS       segundo          frame
 */
float speed = 200; // pixels por segundo

void main() {

    InitWindow(screenWidth, screenHeight,"Movimiento lineal");
    SetTargetFPS(GetMonitorRefreshRate(1)); // Observar como NO cambia la velocidad cuando cambian los FPS

    float posX = initialPosX;
    float posY = initialPosY;

    while (!WindowShouldClose()) {
        // Update
        IO.println(GetFrameTime());
        posX += speed * GetFrameTime(); // El tiempo que dura el frame, es la inversa de FPS (1 / FPS)
        posX %= screenWidth;

        // Draw
        BeginDrawing();
        ClearBackground(BLACK);
        DrawFPS(20, 20);
        DrawCircle((int) posX, (int) posY, radius, ORANGE);
        EndDrawing();
    }
    CloseWindow();
}