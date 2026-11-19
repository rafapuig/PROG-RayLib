import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

final float MAX_PEARL_RADIUS = (float) (SCREEN_HEIGHT / 2);
final float MIN_PEARL_RADIUS = 0.1f;

final float PEARL_RADIUS_GROWTH_FACTOR = .25f;

void main() {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Collar de perlas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    int numPearls = 32;

    float maxCollarRadius = (float) ( Math.min(SCREEN_WIDTH, SCREEN_HEIGHT) /
                                     (2 * (1 + Math.sin((Math.PI / numPearls)))));

    float collarRadius = maxCollarRadius * 0.5f; // - pearlRadius * 2;

    float pearlRadius = (float) (collarRadius * Math.sin(Math.PI / numPearls)); // 60f;


    while (!WindowShouldClose()) {

        float mouseWheel = GetMouseWheelMove();
        if (mouseWheel != 0) {
            collarRadius += mouseWheel * PEARL_RADIUS_GROWTH_FACTOR * 100;
            collarRadius = Math.min(collarRadius, maxCollarRadius);
            pearlRadius = (float) (collarRadius * Math.sin(Math.PI / numPearls)); // 60f;
        }

        if (IsKeyPressed(KEY_UP)) {
            numPearls++;
        }

        if (IsKeyPressed(KEY_DOWN)) {
            numPearls--;
        }



        BeginDrawing();
        ClearBackground(BLACK);

        // Calcular el angulo entre perla y perla
        final float angleBetweenPearls = 360f / numPearls;


        // Calcular el espacio sobrante de la pantalla en horizontal
        final float totalMargin = SCREEN_WIDTH - (collarRadius * 2 + 2 * pearlRadius);

        // Calcular el margen que quedará a la izquierda
        // para dibujar centradas las perlas
        final float leftMargin = totalMargin / 2;


        float pearlAngle = 0f;
        float collarCenterX = leftMargin + collarRadius;
        float collarCenterY = SCREEN_HEIGHT / 2;

        // Dibujar las perlas
        for (int i = 0; i < numPearls; i++) {

            final int pearlX = (int) (collarCenterX + collarRadius * Math.cos(angleBetweenPearls * i * Math.PI / 180));
            final int pearlY = (int) (collarCenterY + collarRadius * Math.sin(angleBetweenPearls * i * Math.PI / 180));

            DrawCircle(pearlX, pearlY, pearlRadius, RAYWHITE);
        }
        DrawFPS(20, 20);
        DrawText(String.format("%.2f", mouseWheel), 20, 50, 30, WHITE);

        EndDrawing();
    }
    CloseWindow();
}