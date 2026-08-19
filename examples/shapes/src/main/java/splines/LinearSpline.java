import com.raylib.Colors;
import com.raylib.Raylib;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;
import static com.raylib.Raylib.ClearBackground;

final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

void main() {

    InitWindow(screenWidth, screenHeight, "Formas básicas");

    int numPoints = 8;
    Raylib.Vector2 points = new Raylib.Vector2(numPoints);

    points.position(0).x(screenWidth / 2f).y(500f);
    points.position(1).x(screenWidth / 2f).y(1000f);
    points.position(2).x(screenWidth / 2f + 500).y(1000f);
    points.position(3).x(screenWidth / 2f + 800).y(600);
    points.position(4).x(screenWidth / 2f - 100).y(100);
    points.position(5).x(screenWidth / 2f - 700).y(600);
    points.position(6).x(screenWidth / 2f - 500).y(900);
    points.position(7).x(screenWidth / 2f - 100).y(700);

    points.position(0);

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(DARKGRAY);

        DrawSplineLinear(points, numPoints, 3f, Colors.BEIGE);
        DrawSplineBasis(points,numPoints, 3f, DARKPURPLE);
        DrawSplineCatmullRom(points, numPoints, 3f, DARKGREEN);
        DrawSplineBezierQuadratic(points, numPoints, 3f, LIME);
        DrawSplineBezierCubic(points, numPoints, 3f, ORANGE);

        //DrawSplineSegmentLinear();

        EndDrawing();
    }
    CloseWindow();
}