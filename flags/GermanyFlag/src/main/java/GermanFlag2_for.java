import com.raylib.Raylib.Color;

import static com.raylib.Colors.*;
import static com.raylib.Helpers.*;
import static com.raylib.Raylib.*;


// Largo de la bandera
final int FLY = 5; // distancia desde el asta hasta el extremo libre

// Ancho de la bandera
final int HOIST = 3; //longitud del lado junto al asta

final int NUM_STRIPES = 3;
final int STRIPE_LENGTH = HOIST / NUM_STRIPES; // Anchura de una franja


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
    SetTargetFPS(GetMonitorRefreshRate(1));

    while (!WindowShouldClose()) {
        BeginDrawing();

        /* Implementación mediante bucle for */
        for (int i = 0; i < NUM_STRIPES; i++) {
            var stripeColor = i == 0 ? BLACK : i == 1 ? RED : GOLD;
            DrawRectangle(0, i * screenStripeHeight, screenWidth, screenStripeHeight, stripeColor);
        }

        EndDrawing();
    }
    CloseWindow();
}
