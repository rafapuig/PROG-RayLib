import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

void main() {

    InitWindow(1280,720, "Hola Jaylib");
    //SetTargetFPS(60);

    while (!WindowShouldClose()) {
        BeginDrawing();
        //ClearBackground(DARKGRAY);

        DrawText("Has creado tu primera ventana!", 150, 330, 64, WHITE);

        EndDrawing();
    }

    CloseWindow();
    
}