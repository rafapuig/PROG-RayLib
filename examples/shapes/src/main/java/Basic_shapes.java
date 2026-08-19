/**
 * https://www.raylib.com/cheatsheet/cheatsheet.html
 */

import com.raylib.Colors;
import com.raylib.Helpers;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;
import static com.raylib.Helpers.*;


final int screenHeight = 1080;
final int screenWidth = screenHeight * 16 / 9;

void main() {

    InitWindow(screenWidth, screenHeight, "Formas básicas");
    SetTargetFPS(120);

    int numPoints = 5;
    Vector2 points = new Vector2(numPoints);

    points.position(0).x(9 * screenWidth / 10f).y(600);
    points.position(1).x(9 * screenWidth / 10f).y(700);
    points.position(2).x(9 * screenWidth / 10f + 100).y(700);
    points.position(3).x(9 * screenWidth / 10f + 150).y(600);
    points.position(4).x(9 * screenWidth / 10f - 50).y(500);

    points.position(0);


    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(RAYWHITE);
        //DrawFPS(0,0);

        DrawText("Algunas formas básicas disponibles en Raylib", 20, 20, 20, DARKGRAY);

        // Circle shapes and lines
        DrawCircle(screenWidth / 10, 120, 35f, DARKBLUE);

        // funcion helper newVector2 para crear un vector 2D, Vector2
        var center1 = newVector2(2 * screenWidth / 10f, 120f);
        DrawCircleV(center1, 45f, GOLD);

        // Sintaxis fluida para crear un vector 2D
        var center2 = new Vector2()
                .x(3 * screenWidth / 10f)
                .y(120f);

        DrawCircleGradient(center2, 60f, SKYBLUE, DARKGREEN);

        DrawCircleLines(4 * screenWidth / 10, 120, 50f, RED);

        DrawCircleLinesV(newVector2(5 * screenWidth / 10f, 120f), 60, DARKBLUE);

        DrawCircleSector(
                newVector2(6 * screenWidth / 10f, 120f),
                80, 0f, 360f, 12, PINK);

        DrawCircleSector(
                newVector2(7 * screenWidth / 10f, 120f),
                80, 0f, 180f, 6, ORANGE);

        DrawCircleSectorLines(
                newVector2(8 * screenWidth / 10f, 120f),
                80, -90f, 180f, 8, LIME);


        // Elipses
        DrawEllipse(screenWidth / 10, 300, 90f, 50f, DARKPURPLE);

        DrawEllipseLines(2 * screenWidth / 10, 300, 40, 70, DARKBROWN);

        DrawEllipseV(newVector2(3 * screenWidth / 10f, 300f), 70f, 40f, BEIGE);

        DrawEllipseLinesV(newVector2(4 * screenWidth / 10f, 300f), 70, 50f, DARKGRAY);

        // Anillos
        DrawRing(newVector2(5 * screenWidth / 10f, 300f), 50f, 70f, 0f, 360f, 10, VIOLET);
        DrawRingLines(newVector2(6 * screenWidth / 10f, 300f), 50f, 70f, 0f, 360f, 12, PINK);
        DrawRingLines(newVector2(7 * screenWidth / 10f, 300f), 50f, 70f, -90f, 120f, 8, BLUE);
        DrawRing(newVector2(8 * screenWidth / 10f, 300f), 50f, 70f, -90f, 180f, 6, MAROON);
        DrawRingLines(newVector2(9 * screenWidth / 10f, 300f), 50f, 70f, -90f, 270f, 9, DARKPURPLE);

        // Rectángulos
        DrawRectangle(screenWidth / 10, 450, 100, 60, LIME);
        DrawRectangleV(
                newVector2(2 * screenWidth / 10f, 450),
                newVector2(50f, 80f),
                GREEN);

        var rect = newRectangle(3 * screenWidth / 10f, 450f, 60f, 60f);
        DrawRectangleRec(rect, RED);

        DrawRectanglePro(
                newRectangle(4 * screenWidth / 10f, 450f, 100f, 80f),
                newVector2(0f, 0f),
                0f,
                GOLD);

        DrawRectanglePro(
                newRectangle(5 * screenWidth / 10f, 450f, 100f, 80f),
                newVector2(50f, 0f),
                0f,
                GOLD);

        DrawRectanglePro(
                newRectangle(6 * screenWidth / 10f, 450f, 100f, 80f),
                newVector2(50f, 40f),
                0f,
                GOLD);

        DrawRectanglePro(
                newRectangle(7 * screenWidth / 10f, 450f, 100f, 80f),
                newVector2(0f, 40f),
                0f,
                GOLD);

        DrawRectanglePro(
                newRectangle(4 * screenWidth / 10f, 450f, 100f, 80f),
                newVector2(0f, 0f),
                45f,
                BLUE);

        DrawRectanglePro(
                newRectangle(5 * screenWidth / 10f, 450f, 100f, 80f),
                newVector2(50f, 0f),
                45f,
                BLUE);

        DrawRectanglePro(
                newRectangle(6 * screenWidth / 10f, 450f, 100f, 80f),
                newVector2(50f, 40f),
                50f,
                BLUE);

        DrawRectanglePro(
                newRectangle(7 * screenWidth / 10f, 450f, 100f, 80f),
                newVector2(0f, 40f),
                45f,
                BLUE);

        DrawRectangleGradientH(screenWidth / 10, 600, 100, 60, ORANGE, DARKBLUE);
        DrawRectangleGradientV(2 * screenWidth / 10, 600, 100, 60, LIME, YELLOW);
        DrawRectangleGradientEx(
                newRectangle(3 * screenWidth / 10f, 600f, 100f, 60f),
                GREEN,
                YELLOW,
                RED,
                BLUE);

        DrawRectangleLinesEx(
                newRectangle(4 * screenWidth / 10f, 600f, 100f, 60f),
                6f,
                DARKPURPLE
        );

        DrawRectangleRounded(
                newRectangle(5 * screenWidth / 10f, 600f, 100f, 120f),
                .75f,
                5,
                VIOLET
        );

        DrawRectangleRoundedLinesEx(
                newRectangle(6 * screenWidth / 10f, 600f, 100f, 120f),
                .75f,
                5,
                4f,
                VIOLET
        );

        // Triángulo (vertices en sentido contrario a las agujas del reloj!!!)
        DrawTriangle(
                newVector2(7 * screenWidth / 10f + 50, 600f),
                newVector2(7 * screenWidth / 10f - 0f, 700f),
                newVector2(7 * screenWidth / 10f + 100f, 700f),
                GOLD
        );

        // Triángulo (vertices en sentido contrario a las agujas del reloj!!!)
        DrawTriangleLines(
                newVector2(8 * screenWidth / 10f + 50, 600f),
                newVector2(8 * screenWidth / 10f - 0f, 700f),
                newVector2(8 * screenWidth / 10f + 100f, 700f),
                DARKBROWN
        );

        DrawTriangleFan(points, numPoints, ORANGE);

        DrawTriangleStrip(points, numPoints, BLUE);

        DrawPoly(
                newVector2(screenWidth / 10f, 800f),
                5,
                70f,
                0f,
                SKYBLUE
        );

        DrawPolyLinesEx(
                newVector2(2 * screenWidth / 10f, 800f),
                5,
                70f,
                30f,
                8.5f,
                DARKBLUE
        );


        EndDrawing();
    }
    CloseWindow();
}