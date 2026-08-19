import static com.raylib.Colors.*;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

// Dimensiones de la pantalla
final int screenHeight = 600;
final int screenWidth = screenHeight * 16 / 9;

// Ball
final float radius = 40f;
final float initialPosX = screenWidth / 2f;
final float initialPosY = screenHeight / 2f;

/**
 * Speed es la medida escalar de la velocidad,
 * pero necesitamos calcular en vector Velocity que dependerá de la dirección
 */
float speed = 700; // pixels por segundo
/**
 * Los ángulos positivos tiene el sentido de agujas del reloj (negativos contra las agujas del reloj)
 *
 * 0 grados -- derecha
 * 90 grados -- abajo
 * 180 grados -- izquierda
 * 270 grados -- arriba (equivale a -90)
 */
float angle = 45;

void main() {

    InitWindow(screenWidth, screenHeight, "Movimiento lineal con dirección mediante matemática de vectores 2D");
    SetTargetFPS(GetMonitorRefreshRate(1)); // Observar como NO cambia la velocidad cuando cambian los FPS

    Vector2 position = newVector2(initialPosX, initialPosY);

    /**
     * Los ángulos positivos tiene el sentido de agujas del reloj (negativos contra las agujas del reloj)
     */
    Vector2 direction = Vector2Rotate(
            newVector2(1, 0),
            (float) Math.toRadians(angle)
    );

    Vector2 velocity = Vector2Scale(direction, speed);

    IO.println(velocity.x() + " - " + velocity.y());

    boolean isStarted = false;

    while (!WindowShouldClose()) {
        // Update
        if (IsKeyPressed(KEY_SPACE)) isStarted = !isStarted;

        float deltaTime = !isStarted ? 0f : GetFrameTime();

        position = Vector2Add(position, Vector2Scale(velocity, deltaTime));

        if (position.x() < radius || position.x() > screenWidth - radius)
            velocity.x(-velocity.x());

        if (position.y() < radius || position.y() > screenHeight - radius)
            velocity.y(-velocity.y());

        // Draw
        BeginDrawing();
        ClearBackground(BLACK);
        DrawFPS(20, 20);
        DrawCircleV(position, radius, GOLD);

        DrawText("Pulsa SPACE para iniciar / detener la simulación",
                20, screenHeight - 30, 20, RAYWHITE);

        EndDrawing();
    }
    CloseWindow();
}