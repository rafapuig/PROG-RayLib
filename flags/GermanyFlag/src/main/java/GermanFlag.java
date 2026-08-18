import com.raylib.Raylib.Color;

import static com.raylib.Colors.*;
import static com.raylib.Helpers.*;
import static com.raylib.Raylib.*;


// Largo de la bandera
final int FLY = 5; // distancia desde el asta hasta el extremo libre

// Ancho de la bandera
final int HOIST = 3; //longitud del lado junto al asta

final int STRIPE_LENGTH = 1; // Anchura de una franja

final int screenHeight = 720;
final int screenWidth = screenHeight * FLY / HOIST;

// Altura de una franja en pixeles
final int screenStripeHeight = screenHeight / HOIST * STRIPE_LENGTH;

// Sintaxis fluida
final Color GOLD = new Color()
        .r((byte) 255)
        .g((byte) 204)
        .b((byte) 0)
        .a((byte) 255);

final Color gold = newColor(255, 204, 0, 255);

void main() {

    InitWindow(screenWidth, screenHeight, "Bandera de Alemania en RayLib");
    SetTargetFPS(60);

    while (!WindowShouldClose()) {
        BeginDrawing();
        /*ClearBackground(GOLD);
        DrawRectangle(0, 0, screenWidth, screenStripeHeight, BLACK);
        DrawRectangle(0, screenStripeHeight, screenWidth, screenStripeHeight, RED);*/

        /* Implementación mediante bucle for */
        /*for (int i = 0; i < 3; i++) {
            var stripeColor = i == 0 ? BLACK : i == 1 ? RED : GOLD;
            DrawRectangle(0, i * screenStripeHeight, screenWidth, screenStripeHeight, stripeColor);
        }*/

        /* Implementación mediante una lista de colores y un for-each */
        var stripeColors = List.of(BLACK, RED, GOLD);
        int offsetY = 0;
        for (Color stripeColor : stripeColors) {
            DrawRectangle(0, offsetY, screenWidth, screenStripeHeight, stripeColor);
            offsetY += screenStripeHeight;
        }

        EndDrawing();
    }
    CloseWindow();
}
