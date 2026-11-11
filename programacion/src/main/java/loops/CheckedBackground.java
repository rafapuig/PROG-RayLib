import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

final int TILE_SIZE = 128;
final int ROWS = 8;
final int COLUMNS = 8;

final int SCREEN_WIDTH = TILE_SIZE * COLUMNS;
final int SCREEN_HEIGHT = TILE_SIZE * ROWS;

final int FONT_SIZE = (int) (TILE_SIZE * 0.8f);


void main() {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Checked Floor Demo");
    SetTargetFPS(GetMonitorRefreshRate(1));

    while (!WindowShouldClose()) {
        BeginDrawing();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                final int posX = j * TILE_SIZE;
                final int posY = i * TILE_SIZE;

                final boolean isEvenCell = (i + j) % 2 == 0;

                final Color cellColor = isEvenCell ? RAYWHITE : DARKGRAY;

                DrawRectangle(posX, posY, TILE_SIZE, TILE_SIZE, cellColor);


                // Texto de la celda (i,j)
                final String cellText = String.format("%d", i + j);
                final int textWidth = MeasureText(cellText, FONT_SIZE);

                // Desplazamiento horizontal para centrar horizontalmente el texto en la celda
                final int xOffset = (TILE_SIZE - textWidth) / 2;

                // Desplazamiento vertical para centrar verticalmente el texto en la celda
                final int yOffset = (TILE_SIZE - FONT_SIZE) / 2;

                // Dibujar el número centrado en la celda (i,j)
                DrawText(cellText, posX + xOffset, posY + yOffset, FONT_SIZE, GRAY);
            }
        }

        EndDrawing();
    }

    CloseWindow();
}