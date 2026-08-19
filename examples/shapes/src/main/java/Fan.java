import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import com.raylib.Raylib.Vector2;

void main() {

        InitWindow(800, 600, "Triangle Fan");
        SetTargetFPS(60);

        Vector2 points = new Vector2(3);

        points.position(0).x(400).y(200);
        points.position(1).x(200).y(400);
        points.position(2).x(600).y(400);

        points.position(0);

        while (!WindowShouldClose()) {

            BeginDrawing();

            ClearBackground(RAYWHITE);

            DrawTriangleFan(points, 3, RED);

            EndDrawing();
        }

        CloseWindow();
    }
