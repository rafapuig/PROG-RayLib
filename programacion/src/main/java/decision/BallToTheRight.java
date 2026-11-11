import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.ORANGE;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

void main() {
    final float ballRadius = 30f;
    final float ballSpeed = 100f;

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Bola moviéndose horizontalmente hacia la derecha");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Posición inicial de la bola, centro de la pantalla
    float ballX = (float) (GetScreenWidth() / 2);
    float ballY = (float) (GetScreenHeight() / 2);

    while (!WindowShouldClose()) {
        // Distancia recorrida = velocidad * tiempo
        ballX += ballSpeed * GetFrameTime();

        BeginDrawing();
        ClearBackground(BLACK);

        DrawCircle((int) ballX, (int) ballY, ballRadius, ORANGE);

        EndDrawing();
    }

    CloseWindow();
    
}