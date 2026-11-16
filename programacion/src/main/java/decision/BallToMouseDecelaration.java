import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

void main() {
    final float ballRadius = 30f;
    final float MAX_SPEED = 1200f;

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Bola a la posición del mouse con desaceleración");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Posición inicial de la bola, centro de la pantalla
    float ballX = (float) (GetScreenWidth() / 2);
    float ballY = (float) (GetScreenHeight() / 2);

    float ballSpeed = 0f;
    float acceleration = 0f;

    float directionX = 0, directionY = 0;

    while (!WindowShouldClose()) {

        if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {

            // Establecemos como destino la posición donde se ha hecho clic
            final float targetX = GetMouseX();
            final float targetY = GetMouseY();

            // Calculamos la distancia a recorrer para llegar al destino
            // Mediante el teorema de Pitágoras (ya que se trata de la hipotenusa)
            float distance = (float) Math.sqrt(
                    Math.pow(targetX - ballX, 2) +
                    Math.pow(targetY - ballY, 2)
            );

            // Velocidad inicial, la maxima, un impulso
            ballSpeed = MAX_SPEED;

            /*
            V_inicial = Vo
            V_final = Vf = 0
            T (tiempo total)
            V = Vo + a * t --> Vf = Vo + a * T --> 0 = Vo + aT
            T = -Vo / a

            X = Xo + Vo * t + 1/2 a * t^2 --> Xf = Xo + Vo * T +  1/2 a * T^2
            Xf - Xo = distance => 0 = 1/2 a * T^2 + Vo * T - distance
            A = 1/2 * a
            B = Vo
            C = - distance
            a = - Vo * Vo / (2 * distance)
             */

            // Aceleración inicial (deceleración, por tanto, negativa)
            acceleration = -ballSpeed * ballSpeed / (distance * 2);

            // Calculamos el tiempo que costará llegar al destino
            // según la velocidad de desplazamiento
            float totalTime = -ballSpeed / acceleration;

            // Actualizamos dirección de la bola
            // (la dirección es un vector de modulo 1 = longitud 1)
            directionX = (targetX - ballX) / distance;
            directionY = (targetY - ballY) / distance;
        }

        // Calculamos la velocidad direccional en X e Y
        float velocityX = directionX * ballSpeed;
        float velocityY = directionY * ballSpeed;

        // Actualizamos la posición de la bola
        ballX += velocityX * GetFrameTime();
        ballY += velocityY * GetFrameTime();

        // Actualizamos la velocidad V(i)  = V(i-1) + a * [t(i) - t(i-1)]
        // La velocidad nueva es la velocidad del frame anterior
        // más la aceleración multiplicada por el incremento de tiempo
        ballSpeed += acceleration * GetFrameTime();

        // Si la velocidad se hace negativa la topamos a cero
        ballSpeed = ballSpeed > 0 ? ballSpeed : 0;
        //ballSpeed = Math.max(ballSpeed, 0);

        BeginDrawing();
        ClearBackground(BLACK);

        // Dibujar la bola en la posición ballX, ballY como centro
        DrawCircle((int) ballX, (int) ballY, ballRadius, ORANGE);

        DrawText(
                String.format("%8.2f", ballSpeed),
                20,20,
                60,
                RAYWHITE
        );

        EndDrawing();
    }

    CloseWindow();
}