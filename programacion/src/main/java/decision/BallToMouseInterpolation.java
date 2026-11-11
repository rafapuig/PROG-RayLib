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

    float ballSpeed = 800f;

    float totalTime = 0f;
    float elapsedTime = 0f;

    float originX = 0, originY = 0;
    float targetX = 0, targetY = 0;

    while (!WindowShouldClose()) {

        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            // Establecemos como destino la posición donde se ha hecho clic
            targetX = GetMouseX();
            targetY = GetMouseY();
            // Memorizamos la posición de la bola cuando se produjo el clic
            originX = ballX;
            originY = ballY;

            // Iniciamos el temporizador de tiempo transcurrido
            elapsedTime = 0f;

            // Calculamos la distancia a recorrer para llegar al destino
            double distance = Math.sqrt(
                    Math.pow(targetX - originX, 2) + Math.pow(targetY - originY, 2)
            );

            // Calculamos el tiempo que costará llegar al destino
            // según la velocidad de desplazamiento
            totalTime = (float) (distance / ballSpeed);
        }

        if (elapsedTime < totalTime) {
            IO.println("Elapsed Time: " + elapsedTime + " / " + totalTime);

            // Qué proporción del tiempo total ha trascurrido
            float timePercent = elapsedTime / totalTime;

            // Lerp (interpolación lineal)
            ballX = originX + (targetX - originX) * timePercent;
            ballY = originY + (targetY - originY) * timePercent;

            // Incrementamos el tiempo trascurrido
            elapsedTime += GetFrameTime();
        }

        BeginDrawing();
        ClearBackground(BLACK);

        DrawCircle((int) ballX, (int) ballY, ballRadius, ORANGE);

        EndDrawing();
    }

    CloseWindow();

}