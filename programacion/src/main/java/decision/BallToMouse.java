import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.ORANGE;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

void main() {
    final float ballRadius = 30f;

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Bola rebotando horizontalmente");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Posición inicial de la bola, centro de la pantalla
    float ballX = (float) (GetScreenWidth() / 2);
    float ballY = (float) (GetScreenHeight() / 2);

    float ballSpeed = 500f;

    while (!WindowShouldClose()) {

        if(IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            ballX = GetMouseX();
            ballY = GetMouseY();
        }

        // Actualizar la posición de la bola
        ballX += ballSpeed * GetFrameTime();

        if( ballX + ballRadius > SCREEN_WIDTH ) {
            ballX = SCREEN_WIDTH - ballRadius;
            ballSpeed = -ballSpeed;
        } else if( ballX - ballRadius < 0 ) {
            ballX = ballRadius;
            ballSpeed = -ballSpeed;
        }

        BeginDrawing();
        ClearBackground(BLACK);

        DrawCircle((int) ballX, (int) ballY, ballRadius, ORANGE);

        EndDrawing();
    }

    CloseWindow();
    
}