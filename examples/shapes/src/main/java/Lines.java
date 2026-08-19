import com.raylib.Colors;
import com.raylib.Raylib;

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

final int screenHeight = 500;
final int screenWidth = screenHeight * 16 / 9;

void main() {


    InitWindow(screenWidth, screenHeight, "Lineas en RayLib");
    SetTargetFPS(120);

    final int numPoints = 4;
    Vector2 points = new Vector2(numPoints); // Memoria para numPoints estructuras Vector2

    points.position(0).x(300).y(150);
    points.position(1).x(400).y(100);
    points.position(2).x(300).y(100);
    points.position(3).put(newVector2(400,150));


    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(DARKGRAY);
        DrawFPS(20, 10);

        DrawText("Lineas y puntos en Raylib", 20, 40, 30, WHITE);

        DrawPixel(20, 100, WHITE);
        DrawPixel(25, 100, WHITE);
        DrawPixel(30, 100, WHITE);

        DrawPixelV(newVector2(20, 120), YELLOW);
        DrawPixelV(newVector2(25, 120), YELLOW);
        DrawPixelV(newVector2(30, 120), YELLOW);

        // Lineas
        DrawLine(100, 100, 200, 100, SKYBLUE);
        DrawLineV(newVector2(100, 120), newVector2(200, 120), GOLD);
        DrawLineEx(newVector2(100, 140), newVector2(200, 140), 4f, LIME);


        /*points.position(0);
        DrawLineStrip(points, numPoints, PINK);*/
        DrawLineStrip(points.position(0), (int) points.capacity(), PINK);

        DrawLineBezier(newVector2(500, 100), newVector2(600, 150), 5f, BEIGE);

        DrawLineDashed(newVector2(700, 100), newVector2(800, 150), 10, 5, YELLOW);

        EndDrawing();
    }
    CloseWindow();


}