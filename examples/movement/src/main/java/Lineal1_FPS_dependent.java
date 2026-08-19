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
 * Con un frame rate de 100 frames / segundo
 * si la velocidad es 2 pixels / frame
 * Entonces la velocidad en pixels por segundo es
 *           2 pixels    100 frames
 * Speed = ---------- * ---------- = 200 pixels / segundo
 *           frame        segundo
 *
 */
final float speed = 2; // pixels por frame (dependiente del framerate)

void main() {

    InitWindow(screenWidth, screenHeight, "Movimiento lineal");
    SetTargetFPS(100); // Observar como cambia la velocidad cuando cambian los FPS

    float posX = initialPosX;
    float posY = initialPosY;

    while (!WindowShouldClose()) {
        // Update
        posX += speed;
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