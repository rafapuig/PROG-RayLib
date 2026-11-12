import static com.raylib.Colors.WHITE;
import static com.raylib.Colors.YELLOW;
import static com.raylib.Helpers.newColor;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

final Color GREEN = newColor(38, 185, 154, 255);
final Color DARK_GREEN = newColor(20, 160, 133, 255);
final Color LIGHT_GREEN = newColor(129, 204, 184, 255);
final Color YELLOWISH = newColor(243, 213, 91, 255);

// Constantes para el dibujado del marcador de puntuación
final Color SCORE_COLOR = WHITE;
final int SCORE_FONT_SIZE = 80;
final int SCORE_VERTICAL_OFFSET = 20;



void main() {
    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Pong");
    SetTargetFPS(GetMonitorRefreshRate(1));

    int playerScore = 0;
    int cpuScore = 0;

    final float ballRadius = 30;
    float ballX = SCREEN_WIDTH / 2;
    float ballY = SCREEN_HEIGHT / 2;
    float velocityX = 420;
    float velocityY = 420;

    float playerPaddleX = 0;
    float playerPaddleY = 0;
    float width = 0;
    float height = 0;
    int playerPaddleSpeed = 0;



    while (!WindowShouldClose()) {
        // Input


        // Update
        ballX += velocityX * GetFrameTime();
        ballY += velocityY * GetFrameTime();

        // Rebotar si choca arriba o abajo
        if(ballY + ballRadius > SCREEN_HEIGHT || ballY - ballRadius < 0) {
            velocityY = -velocityY;
        }

        // Llevar a la posicion inicial si sale por izquierda o derecha
        if(ballX + ballRadius > SCREEN_WIDTH || ballX - ballRadius < 0) {
            ballX =  SCREEN_WIDTH / 2;
            ballY =  SCREEN_HEIGHT / 2;
            velocityX *= Math.random() > 0.5 ? -1 : 1;
            velocityY *= Math.random() > 0.5 ? -1 : 1;
        }

        // Punto para el jugador si sale por la izquierda
        if(ballX - ballRadius < 0) {
            playerScore++;
        }

        // Punto para la CPU si sale por la derecha
        if(ballX + ballRadius > SCREEN_WIDTH) {
            cpuScore++;
        }


        // Draw
        BeginDrawing();
        ClearBackground(DARK_GREEN);

        // Mitad derecha del campo
        DrawRectangle(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT, GREEN);

        // Círculo central
        DrawCircle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 150, LIGHT_GREEN);

        // Línea separadora en medio de los dos campos
        DrawLine(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT, WHITE);

        /*
         * Dibujar los marcadores de puntuación
         */

        final String cpuScoreText = String.format("%d", cpuScore);
        final int cpuScoreTextWidth = MeasureText(cpuScoreText, SCORE_FONT_SIZE);

        final String playerScoreText = String.format("%d", playerScore);
        final int playerScoreTextWidth = MeasureText(playerScoreText, SCORE_FONT_SIZE);

        DrawText(cpuScoreText, SCREEN_WIDTH / 4 - cpuScoreTextWidth / 2, SCORE_VERTICAL_OFFSET, SCORE_FONT_SIZE, SCORE_COLOR);
        DrawText(playerScoreText, SCREEN_WIDTH * 3 / 4 - playerScoreTextWidth / 2, SCORE_VERTICAL_OFFSET, SCORE_FONT_SIZE, SCORE_COLOR);

        // Dibujar la bola
        DrawCircle((int) ballX, (int) ballY, ballRadius, YELLOW);

        EndDrawing();
    }
    CloseWindow();
}