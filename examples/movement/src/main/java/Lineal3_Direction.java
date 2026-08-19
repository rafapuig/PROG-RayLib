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
float speed = 200; // pixels por segundo, medida escalar
/**
 * Los ángulos positivos tiene el sentido de agujas del reloj (negativos contra las agujas del reloj)
 * 0 grados --> derecha
 * 90 grados --> abajo
 * 180 grados --> izquierda
 * 270 grados --> arriba (equivale a -90)
 */
float angle = 30; // Ángulo que indica la dirección del movimiento

void main() {

    InitWindow(screenWidth, screenHeight, "Movimiento lineal con dirección");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Establecer la posición inicial de la bola
    float posX = initialPosX;
    float posY = initialPosY;

    // Establecer la dirección en 2D en función del ángulo que orienta la dirección
    Vector2 direction = newVector2((float) Math.cos(Math.toRadians(angle)), (float) Math.sin(Math.toRadians(angle)));

    // Calcular la velocidad vectorial velocity = direction * speed
    Vector2 velocity = newVector2(direction.x() * speed, direction.y() * speed);

    IO.println(velocity.x() + " - " + velocity.y());

    while (!WindowShouldClose()) {
        // Update
        float deltaTime = GetFrameTime();

        // posiciónX = posiciónX + velocidadX * tiempo;
        posX += velocity.x() * deltaTime; // El tiempo que dura en frame  es la inversa de  FPS (1 / FPS)
        posX %= screenWidth;
        // posiciónY = posiciónY + velocidadY * tiempo;
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