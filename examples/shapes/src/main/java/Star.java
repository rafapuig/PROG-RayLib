import com.raylib.Raylib.Vector2;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class Star {

    public static void main(String[] args) {

        InitWindow(800, 600, "Estrella de 5 puntas");
        SetTargetFPS(60);

        // Dibujamos los puntos para poder ver cómo funciona
        /*DrawCircle(400, 300, 5, BLACK);

        DrawCircle(400, 100, 5, BLACK);
        DrawCircle(459, 281, 5, BLACK);

        DrawCircle(590, 235, 5, BLACK);

        DrawCircle(495, 358, 5, BLACK);

        /*DrawCircle(512, 525, 5, BLACK);
        DrawCircle(400, 425, 5, BLACK);
        DrawCircle(288, 525, 5, BLACK);
        DrawCircle(305, 358, 5, BLACK);
        DrawCircle(210, 235, 5, BLACK);
        DrawCircle(341, 281, 5, BLACK);*/

        // Centro de la estrella
        float centerX = 400;
        float centerY = 300;

        // Radios
        float outerRadius = 200;
        float innerRadius = 80;

        // 11 puntos:
        // P0 = centro
        // P1..P10 = 10 vértices de la estrella
        Vector2 points = new Vector2(11);

        // Centro
        points.position(0)
                .x(centerX)
                .y(centerY);

        // Los 10 vértices de la estrella.
        //
        // Empezamos arriba (-90 grados) y avanzamos
        // 36 grados en cada punto.
        for (int i = 0; i < 10; i++) {

            double angle = Math.toRadians(-90 + i * 36);

            float radius = (i % 2 == 0)
                    ? outerRadius
                    : innerRadius;

            float x = centerX + (float) Math.cos(angle) * radius;
            float y = centerY + (float) Math.sin(angle) * radius;

            points.position(i + 1)
                    .x(x)
                    .y(y);
        }

        // Muy importante:
        // dejamos el Vector2 apuntando al primer elemento.
        points.position(0);

        while (!WindowShouldClose()) {

            BeginDrawing();

            ClearBackground(RAYWHITE);

            // Estrella rellena
            DrawTriangleFan(
                    points,
                    11,
                    RED
            );

            EndDrawing();
        }

        CloseWindow();
    }
}