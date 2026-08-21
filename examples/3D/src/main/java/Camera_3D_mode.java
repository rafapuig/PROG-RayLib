import static com.raylib.Colors.*;
import static com.raylib.Helpers.*;
import static com.raylib.Raylib.*;
import static com.raylib.Raylib.BeginDrawing;

void main() {
    final int screenHeight = 1080;
    final int screenWidth = screenHeight * 16 / 9;

    InitWindow(screenWidth, screenHeight, "Modo de cámara 3D RayLib");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Camera3D camera = newCamera(
            newVector3(0f, 6f, 12f), // Posición
            newVector3(0f,0f,0f), // Punto de mira
            newVector3(0f,1f,0), // Vector arriba
            45f, // fovy Field of View (Campo de vision)
            CAMERA_PERSPECTIVE // Modo de cámara
    );

    Vector3 cubePosition = newVector3(0,0,0);

    DisableCursor();

    while (!WindowShouldClose()) {

        UpdateCamera(camera, CAMERA_FREE);

        BeginDrawing();
        ClearBackground(DARKGRAY);

        BeginMode3D(camera);

        DrawCube(cubePosition, 2,2,2, SKYBLUE);
        DrawCubeWires(cubePosition, 2,2,2, DARKBLUE);
        DrawGrid(10, 1f);

        EndMode3D();

        EndDrawing();
    }
    CloseWindow();
}