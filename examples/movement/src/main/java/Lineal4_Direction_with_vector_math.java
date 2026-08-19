import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.ORANGE;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;

// Dimensiones de la pantalla
final int screenHeight = 600;
final int screenWidth = screenHeight * 16 / 9;

final Vector2 topLeft = newVector2(0, 0);
final Vector2 bottomRight = newVector2(screenWidth, screenHeight);

final float initialPosX = screenWidth / 2f;
final float initialPosY = screenHeight / 2f;

/**
 * Speed es la medida escalar de la velocidad,
 * pero necesitamos calcular en vector Velocity que dependerá de la dirección
 */
float speed = 200; // pixels por segundo
/**
 * 0 grados -- derecha
 * 90 grados -- abajo
 * 180 grados -- izquierda
 * 270 grados -- arriba (equivale a -90)
 */
float angle = 30;

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

    while (!WindowShouldClose()) {
        // Update
        position = Vector2Add(position, Vector2Scale(velocity, GetFrameTime()));
        position = newVector2(
                position.x() % screenWidth,
                position.y() % screenHeight
        );

        // Draw
        BeginDrawing();
        ClearBackground(BLACK);
        DrawFPS(20, 20);
        DrawCircleV(position, 50f, ORANGE);
        EndDrawing();
    }
    CloseWindow();

}