import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

final float GRAVITY = 9.81f * 300;

final float BALL_RADIUS = 30f;

void main() {
    SetConfigFlags(FLAG_VSYNC_HINT);
    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Bola que cae");

    // Posicional la bola inicialmente en el centro de la pantalla
    float ballX = (float) (SCREEN_WIDTH / 2);
    float ballY = (float) (SCREEN_HEIGHT / 2);

    float velocityX = 0f;
    float velocityY = 0f;

    boolean started = false;

    while (!WindowShouldClose()) {

        /** Procesar la entrada */
        if (IsKeyPressed(KEY_SPACE)) {
            started = true;
        }

        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            float targetX = GetMouseX();
            float targetY = GetMouseY();

            // Si tenemos que subir
            if (targetY < ballY) {
                // Tiempo que durará la subida hasta alcanzar el punto del mouse
                // En ese punto la velocidad Y debe ser cero
                float time = (float) Math.sqrt(
                        2 * Math.abs(targetY - ballY) /
                        GRAVITY
                );
                velocityY = -GRAVITY * time;
                velocityX = (targetX - ballX) / time;
            } else {
                // Si está subiendo, cuanto tiempo le costará subir hasta el pico y bajar hasta targetY
                // La velocidad inicial es la que lleva en este momento del click
                // Y si ya está bajando cuanto le costará llegar hasta targetY
                //Yf - Yo = Vo * t + 1/2 g * t^2 -> resolver ecuación cuadrática
                float A = GRAVITY / 2;
                float B = velocityY;
                float C = -Math.abs(targetY - ballY);

                float time = (float) ((-B + Math.sqrt(B * B - 4 * A * C)) / (2 * A));
                velocityX = (targetX - ballX) / time;
            }
        }

        /** Actualizar */
        if (started) {
            // Actualizar la posición de la bola en función de su velocidad
            ballX += velocityX * GetFrameTime();
            ballY += velocityY * GetFrameTime();

            // Si la bola se va a salir por la parte inferior de la ventana
            // y está bajando -> rebotar
            if (ballY + BALL_RADIUS > SCREEN_HEIGHT && velocityY > 0) {
                velocityY = -velocityY;
            } else { // Si no, actualizar la velocidad vertical
                velocityY += GRAVITY * GetFrameTime();
            }

            // Si la bola se va a salir por la derecha
            // y va en dirección de izquierda a derecha -> rebotar
            if (ballX + BALL_RADIUS > SCREEN_WIDTH && velocityX > 0) {
                velocityX = -velocityX;
            }

            // Si la bola se va a salir por la izquierda
            // y va en dirección de derecha a izquierda -> rebotar
            if (ballX - BALL_RADIUS < 0 && velocityX < 0) {
                velocityX = -velocityX;
            }
        }

        /** Dibujar el frame */
        BeginDrawing();
        ClearBackground(DARKBLUE);

        DrawCircle((int) ballX, (int) ballY, BALL_RADIUS, ORANGE);

        DrawFPS(20, 20);
        EndDrawing();
    }

    CloseWindow();
}