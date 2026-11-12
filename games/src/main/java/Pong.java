import static com.raylib.Colors.*;
import static com.raylib.Helpers.*;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

// Colores
final Color GREEN = newColor(38, 185, 154, 255);
final Color DARK_GREEN = newColor(20, 160, 133, 255);
final Color LIGHT_GREEN = newColor(129, 204, 184, 255);
final Color YELLOWISH = newColor(243, 213, 91, 255);

// Constantes para el dibujado del marcador de puntuación
final Color SCORE_COLOR = WHITE;
final int SCORE_FONT_SIZE = 100;
final int SCORE_VERTICAL_OFFSET = 20;

// Medidas de un paddle
final float PADDLE_SPEED = 7f * 60;
final float PADDLE_WIDTH = 25;
final float PADDLE_HEIGHT = 150;
final int PADDLE_SCREEN_MARGIN = 20;

final float BALL_RADIUS = 25f;
final float INITIAL_BALL_X = (float) (SCREEN_WIDTH / 2);
final float INITIAL_BALL_Y = (float) (SCREEN_HEIGHT / 2);


void main() {
    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Pong");
    SetTargetFPS(GetMonitorRefreshRate(1));

    int playerScore = 0;
    int cpuScore = 0;

    float ballX = INITIAL_BALL_X;
    float ballY = INITIAL_BALL_Y;

    float ballVelocityX = 9f * 60;
    float ballVelocityY = ballVelocityX;

    float playerPaddleX = SCREEN_WIDTH - (PADDLE_WIDTH + PADDLE_SCREEN_MARGIN);
    float playerPaddleY = (SCREEN_HEIGHT - PADDLE_HEIGHT) / 2;


    float cpuPaddleX = PADDLE_SCREEN_MARGIN;
    float cpuPaddleY = (SCREEN_HEIGHT - PADDLE_HEIGHT) / 2;

    boolean started = false;

    while (!WindowShouldClose()) {
        // Input
        int input = 0;
        if (IsKeyDown(KEY_UP)) input--;
        if (IsKeyDown(KEY_DOWN)) input++;

        if (IsKeyDown(KEY_ENTER)) started = true;

        /* Update */

        if (input != 0) {
            // Movel el paddle del jugador en función de la entrada
            playerPaddleY += input * PADDLE_SPEED * GetFrameTime();

            // Si el paddle del jugador se saliera por arriba
            if (playerPaddleY < 0) playerPaddleY = 0;

            // Si el paddle del jugador se saliera por abajo
            if (playerPaddleY + PADDLE_HEIGHT > SCREEN_HEIGHT) {
                playerPaddleY = SCREEN_HEIGHT - PADDLE_HEIGHT;
            }
        }

        // Actualizar la posición de la bola
        if (started) {
            ballX += ballVelocityX * GetFrameTime();
            ballY += ballVelocityY * GetFrameTime();
        }

        /* CPU Paddle Update */
        // Calcular la posición central en el eje Y del paddle
        float cpuPaddleCenterY = cpuPaddleY + PADDLE_HEIGHT / 2;
        // Si está más abajo que la bola --> subir
        if (cpuPaddleCenterY > ballY) {
            cpuPaddleY -= PADDLE_SPEED * GetFrameTime();
        } else if (cpuPaddleCenterY < ballY) { // si está más arriba --> bajar
            cpuPaddleY += PADDLE_SPEED * GetFrameTime();
        }

        // Rebotar la bola si choca arriba o abajo
        if (ballY + BALL_RADIUS > SCREEN_HEIGHT || ballY - BALL_RADIUS < 0) {
            ballVelocityY = -ballVelocityY;
        }

        // Punto para el jugador si la bola sale por la izquierda
        if (ballX - BALL_RADIUS < 0) {
            playerScore++;
        }

        // Punto para la CPU si la bola sale por la derecha
        if (ballX + BALL_RADIUS > SCREEN_WIDTH) {
            cpuScore++;
        }

        // Llevar a la posición inicial tanto si la bola sale por izquierda o derecha
        if (ballX + BALL_RADIUS > SCREEN_WIDTH || ballX - BALL_RADIUS < 0) {
            ballX = INITIAL_BALL_X;
            ballY = INITIAL_BALL_Y;
            // 50% de probabilidad de cambiar de dirección
            ballVelocityX *= Math.random() > 0.5 ? -1 : 1;
            ballVelocityY *= Math.random() > 0.5 ? -1 : 1;
        }


        /* Comprobando colisiones */
        // Si la bola choca con el paddle del jugador
        if (CheckCollisionCircleRec(
                newVector2(ballX, ballY), BALL_RADIUS,
                newRectangle(playerPaddleX, playerPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT))) {
            // Dejar la bola tocando en el borde del paddle del jugador
            ballX = SCREEN_WIDTH - (PADDLE_WIDTH + PADDLE_SCREEN_MARGIN + BALL_RADIUS);
            // Cambiar la dirección en el eje X
            ballVelocityX *= -1;
        }
        // Si la bola choca con el paddle de la CPU
        if (CheckCollisionCircleRec(
                newVector2(ballX, ballY), BALL_RADIUS,
                newRectangle(cpuPaddleX, cpuPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT))) {
            // Dejar la bola tocando en el borde del paddle de la CPU
            ballX = PADDLE_WIDTH + PADDLE_SCREEN_MARGIN + BALL_RADIUS;
            // Cambiar la dirección en el eje X
            ballVelocityX *= -1;
        }


        /**
         * Draw (dibujado del frame)
         */
        BeginDrawing();
        ClearBackground(DARK_GREEN);

        // Mitad derecha del campo
        DrawRectangle(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT, GREEN);

        // Círculo central
        DrawCircle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 200, LIGHT_GREEN);

        // Línea separadora en medio de los dos campos
        DrawLine(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT, WHITE);

        /*
         * Dibujar los marcadores de puntuación
         */

        // Marcador de la CPU
        final String cpuScoreText = String.format("%d", cpuScore);
        final int cpuScoreTextWidth = MeasureText(cpuScoreText, SCORE_FONT_SIZE);
        DrawText(cpuScoreText,
                SCREEN_WIDTH / 4 - cpuScoreTextWidth / 2,
                SCORE_VERTICAL_OFFSET, SCORE_FONT_SIZE, SCORE_COLOR);

        // Marcador del player
        final String playerScoreText = String.format("%d", playerScore);
        final int playerScoreTextWidth = MeasureText(playerScoreText, SCORE_FONT_SIZE);
        DrawText(playerScoreText,
                SCREEN_WIDTH * 3 / 4 - playerScoreTextWidth / 2,
                SCORE_VERTICAL_OFFSET, SCORE_FONT_SIZE, SCORE_COLOR);

        // Dibujar paddle CPU
        DrawRectangleRounded(
                newRectangle(cpuPaddleX, cpuPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT),
                0.8f,
                0,
                WHITE
        );

        // Dibujar paddle Player
        DrawRectangleRounded(
                newRectangle(playerPaddleX, playerPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT),
                0.8f,
                0,
                WHITE
        );

        // Dibujar la bola
        DrawCircle((int) ballX, (int) ballY, BALL_RADIUS, YELLOWISH);

        if (!started) {
            String startText = "Pulsa ENTER para empezar!";
            final int startTextFontSize = 80;
            final int startTextWidth = MeasureText(startText, startTextFontSize);
            DrawText(startText,
                    (SCREEN_WIDTH - startTextWidth) / 2,
                    (SCREEN_HEIGHT - startTextFontSize) * 4 / 5,
                    startTextFontSize, RAYWHITE);
        }

        DrawFPS(20, 20);

        EndDrawing();
    }
    CloseWindow();
}