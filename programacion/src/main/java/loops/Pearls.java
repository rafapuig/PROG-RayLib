import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

void main() {

    final int SCREEN_HEIGHT = 1080;
    final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

    final float MAX_PEARL_RADIUS = (float) (SCREEN_HEIGHT / 2);
    final float MIN_PEARL_RADIUS = 0.1f;

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Collar de perlas");
    SetTargetFPS(GetMonitorRefreshRate(1));

    float pearlRadius = 60f;
    final float radiusGrowth = .25f;


    while (!WindowShouldClose()) {

        if (IsKeyDown(KEY_UP)) {
            pearlRadius *= 1 + radiusGrowth * GetFrameTime();
            final boolean isRadiusTooBig = pearlRadius > MAX_PEARL_RADIUS;
            pearlRadius = isRadiusTooBig ? MAX_PEARL_RADIUS : pearlRadius;
        }

        if (IsKeyDown(KEY_DOWN)) {
            pearlRadius /= 1 + radiusGrowth * GetFrameTime();
            final boolean isRadiusTooSmall = pearlRadius < MIN_PEARL_RADIUS;
            pearlRadius = isRadiusTooSmall ? MIN_PEARL_RADIUS : pearlRadius;
        }

        BeginDrawing();
        ClearBackground(BLACK);

        // Calcular la cantidad de perlas que caben en la pantalla
        final float pearlDiameter = pearlRadius * 2;
        final int numPearls = (int) (SCREEN_WIDTH / pearlDiameter);

        // Calcular el espacio sobrante de la pantalla en horizontal
        final float totalMargin = SCREEN_WIDTH - numPearls * pearlDiameter;

        // Calcular el margen que quedará a la izquierda
        // para dibujar centradas las perlas
        final float leftMargin = totalMargin / 2;

        // Dibujar las perlas
        for (int i = 0; i < numPearls; i++) {
            final int pearlX = (int) (leftMargin + pearlRadius + i * pearlDiameter);
            final int pearlY = SCREEN_HEIGHT / 2;

            /*final Color color = switch ((int) (Math.random() * 4)) {
                case 0 -> BLUE;
                case 1 -> RED;
                case 2 -> YELLOW;
                case 3 -> GREEN;
                default -> RAYWHITE;
            };*/

            DrawCircle(pearlX, pearlY, pearlRadius, RAYWHITE);
        }
        DrawFPS(20, 20);
        EndDrawing();
    }
    CloseWindow();
}