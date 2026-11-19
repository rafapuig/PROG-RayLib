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


   /* float pearlRadius = 30f;

    float maxCollarRadius = (float) Math.min(SCREEN_WIDTH, SCREEN_HEIGHT) / 2 - pearlRadius;

    float collarRadius = maxCollarRadius * 0.5f; // - pearlRadius * 2;

    int numPearls = (int) Math.floor(Math.PI / Math.asin(pearlRadius / collarRadius));

    //float pearlRadius = (float) (collarRadius * Math.sin(Math.PI / numPearls)); // 60f;
*/

    float pearlRadius = 30f;

    while (!WindowShouldClose()) {

        if (IsKeyDown(KEY_UP)) {
            pearlRadius *= 1 + PEARL_RADIUS_GROWTH_FACTOR * GetFrameTime();
            final boolean isRadiusTooLarge = pearlRadius > MAX_PEARL_RADIUS;
            pearlRadius = isRadiusTooLarge ? MAX_PEARL_RADIUS : pearlRadius;
        }

        if (IsKeyDown(KEY_DOWN)) {
            pearlRadius /= 1 + PEARL_RADIUS_GROWTH_FACTOR * GetFrameTime();
            final boolean isRadiusTooSmall = pearlRadius < MIN_PEARL_RADIUS;
            pearlRadius = isRadiusTooSmall ? MIN_PEARL_RADIUS : pearlRadius;
        }


        float maxCollarRadius = (float) Math.min(SCREEN_WIDTH, SCREEN_HEIGHT) / 2 - pearlRadius;

        float collarRadius = maxCollarRadius * 0.5f; // - pearlRadius * 2;

        int numPearls = (int) Math.floor(Math.PI / Math.asin(pearlRadius / collarRadius));

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

        EndDrawing();
    }
    CloseWindow();
}