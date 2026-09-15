import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;
import static com.raylib.Raylib.CloseWindow;

void main() {
    InitWindow(1280, 720, "Timing");
    SetTargetFPS(60);


    final float eventTime = 0.5f;
    float elapsedTime = 0.f;

    int randomValue = GetRandomValue(0, 90);

    while (!WindowShouldClose()) {

        // Update
        elapsedTime += GetFrameTime();

        if (elapsedTime > eventTime) {
            randomValue = GetRandomValue(0, 90);
            elapsedTime -= eventTime;
        }


        // Draw
        BeginDrawing();
        ClearBackground(BLACK);

        DrawText(String.format("%d", randomValue), 540, 260, 200, LIGHTGRAY);

        EndDrawing();
    }

    CloseWindow();
}