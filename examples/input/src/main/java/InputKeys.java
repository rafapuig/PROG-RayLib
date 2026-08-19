import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

final int speed = 5; //pixels / frame

void main() {

    var ballPosition = newVector2(screenWidth / 2f, screenHeight / 2f);

    InitWindow(screenWidth, screenHeight, "Entrada de teclado en RayLib");
    SetTargetFPS(GetMonitorRefreshRate(1));

    while (!WindowShouldClose()) {
        // Actualizar
        if (IsKeyDown(KEY_UP)) ballPosition.y(ballPosition.y() - speed);
        if (IsKeyDown(KEY_DOWN)) ballPosition.y(ballPosition.y() + speed);
        if (IsKeyDown(KEY_LEFT)) ballPosition.x(ballPosition.x() - speed);
        if (IsKeyDown(KEY_RIGHT)) ballPosition.x(ballPosition.x() + speed);

        /**
         * Existe un problema con este sistema
         * Cuando pulsamos a la vez dos teclas para movimiento horizontal y vertical
         * la bola se mueve el diagonal y se mueve más deprisa
         * Cuando lo hace en vertical o en horizontal únicamente el avance es 5 pixels por frame
         * Cuando lo hace en diagonal se mueve una distancia equivalente
         * a la hipotenusa de un rectangulo de 5 * 5
         * h = sqrt(5^2 + 5^2) = sqrt(25 + 25) = sqrt(50) = 7,07
         */

        // Dibujar
        BeginDrawing();
        ClearBackground(DARKGRAY);

        DrawCircleV(ballPosition, 60f, PINK);

        DrawText("Mueve la bola con las flechas cursores", 20, 20, 40, RAYWHITE);
        EndDrawing();
    }
    CloseWindow();

}