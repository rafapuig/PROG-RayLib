import com.raylib.Raylib.Vector2;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class Star {

    public static void main(String[] args) {

        InitWindow(800, 600, "Estrella de 5 puntas");
        SetTargetFPS(60);

        // Centro de la estrella
        float centerX = 400;
        float centerY = 300;

        // Radios
        float outerRadius = 200;
        float innerRadius = 80;

        // 11 puntos:
        // P0 = centro
        // P1..P10 = 10 vértices de la estrella
        Vector2 points = new Vector2(12);

        // Centro
        points.position(0)
                .x(centerX)
                .y(centerY);

        // Los 10 vértices de la estrella.
        //
        // Empezamos arriba (-90 grados) y avanzamos
        // 36 grados en cada punto.
        for (int i = 0; i < 10; i++) {

            double angle = Math.toRadians(90 + i * 36);

            float radius = (i % 2 == 0)
                    ? outerRadius
                    : innerRadius;

            float x = centerX + (float) Math.cos(angle) * radius;
            float y = centerY + (float) -Math.sin(angle) * radius;

            points.position(i + 1)
                    .x(x)
                    .y(y);
        }

        // Punto 12, otra vez el primer vértice
        float lastX = points.position(1).x();
        float lastY = points.position(1).y();

        /*Vector2 last =
                points.position(1).getPointer(); //.getPointer();
        points.position(11).put(last);// .x(lastX).y(lastY);*/
        points.position(11).x(lastX).y(lastY);

        while (!WindowShouldClose()) {

            BeginDrawing();

            ClearBackground(RAYWHITE);

            // Estrella rellena
            DrawTriangleFan(
                    points.position(0),
                    12,
                    RED
            );

            EndDrawing();
        }

        CloseWindow();
    }
}