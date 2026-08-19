import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.ORANGE;
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
 * pero necesitamos calcular el vector Velocity,
 * <p>
 * Velocity es una medida vectorial, (2D en este caso)
 * que dependerá del vector de la dirección 2D
 * <p>
 * Velocity = direction * speed
 */
float speed = 200; // pixels por segundo
/**
 * Los ángulos positivos tiene el sentido de agujas del reloj (negativos contra las agujas del reloj)
 * 0 grados -- derecha
 * 90 grados -- abajo
 * 180 grados -- izquierda
 * 270 grados -- arriba (equivale a -90)
 */
float angle = 30;

void main() {

    InitWindow(screenWidth, screenHeight, "Movimiento lineal");
    SetTargetFPS(GetMonitorRefreshRate(1));

    float posX = initialPosX;
    float posY = initialPosY;

    final Vector2 right = newVector2(1, 0);

    Vector2 direction = Vector2Rotate(
            right,
            (float) Math.toRadians(angle)
    );

    Vector2 velocity = Vector2Scale(direction, speed);

    IO.println(velocity.x() + " - " + velocity.y());

    while (!WindowShouldClose()) {
        // Update
        float deltaTime = GetFrameTime();
        posX += velocity.x() * deltaTime; // El tiempo que dura en frame  es la inversa de  FPS (1 / FPS)
        posX %= screenWidth;
        posY += velocity.y() * deltaTime;
        posY %= screenHeight;

        // Draw
        BeginDrawing();
        ClearBackground(BLACK);
        DrawFPS(20, 20);
        DrawCircle((int) posX, (int) posY, radius, ORANGE);
        EndDrawing();
    }
    CloseWindow();
}