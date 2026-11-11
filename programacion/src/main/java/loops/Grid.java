import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

void main() {

    final int SCREEN_HEIGHT = 1080;
    final int SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;

    final float GROWTH_FACTOR = 10f;
    final int MAX_GRID_CELL_SIZE = SCREEN_WIDTH / 2;
    final int MIN_GRID_CELL_SIZE = 10;

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Rejilla");

    float gridCellSize = 50f;

    while (!WindowShouldClose()) {

        final float mouseWheelMove = GetMouseWheelMove();
        if (mouseWheelMove != 0) {
            // Incrementar / decrementar el tamaño de una celda de la rejilla
            gridCellSize += mouseWheelMove * GROWTH_FACTOR;

            // Clampar entre los valores máximo y mínimo posibles
            gridCellSize = Math.clamp(gridCellSize, MIN_GRID_CELL_SIZE, MAX_GRID_CELL_SIZE);
        }

        final int horizontalLines = (int) (SCREEN_HEIGHT / gridCellSize);
        final int verticalLines = (int) (SCREEN_WIDTH / gridCellSize);


        BeginDrawing();
        ClearBackground(BLACK);

        // Dibujar las líneas horizontales de la rejilla
        for (int i = 0; i <= horizontalLines; i++) {
            final int posY = (int) (i * gridCellSize) - 1;
            DrawLine(0, posY, SCREEN_WIDTH, posY, RAYWHITE);
        }
        for (int i = 0; i <= verticalLines; i++) {
            final int posX = (int) (i * gridCellSize);
            DrawLine(posX, 0, posX, SCREEN_HEIGHT, RAYWHITE);
        }

        final String text = String.format("%.2f", gridCellSize);

        DrawText(text, 20, 20, 30, WHITE);
        EndDrawing();
    }

    CloseWindow();
}