/**
 * https://es.wikipedia.org/wiki/Bandera_de_Jap%C3%B3n#/media/Archivo:Construction_sheet_of_the_Japanese_flag_no_text.svg
 */

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

// Constantes relacionadas con la bandera
final int fly = 3; // fly = distancia desde el asta hasta el extremo libre
final int hoist = 2; // hoist = longitud del lado junto al asta

// Coordenadas X e Y del centro del punto rojo de la bandera
final float solarDiscCenterX = fly / 2.0f;
final float solarDiscCenterY = hoist / 2.0f;

final float solarDiscDiameter = hoist * 3 / 5.0f; // Cuidado con el orden de las operaciones para no perder precision
final float solarDiscRadius = solarDiscDiameter / 2.0f; // El radio es la mitad del diámetro

final int screenHeight = 720;
final int screenWidth = screenHeight * fly / hoist;

final int screenSolarDiscCenterX = (int) (solarDiscCenterX * screenWidth / fly);
final int screenSolarDiscCenterY = (int) (solarDiscCenterY * screenHeight / hoist);

final float screenSolarDiscRadius = solarDiscRadius * screenHeight / hoist;


void main() {
    IO.println("Solar Disc Center X: " + solarDiscCenterX);
    IO.println("Solar Disc Center Y: " + solarDiscCenterY);
    IO.println("Solar Disc Diameter: " + solarDiscDiameter);
    IO.println("Solar Disc Radius: " + solarDiscRadius);
    IO.println("Screen dimensions:");
    IO.println("Screen Width: " + screenWidth);
    IO.println("Screen Height: " + screenHeight);
    IO.println("Screen Solar Disc Center X:" + screenSolarDiscCenterX);
    IO.println("Screen Solar Disc Center Y:" + screenSolarDiscCenterY);
    IO.println("Screen Solar Disc Radius: " + screenSolarDiscRadius);


    // Vector 2D con las coordenadas X e Y del centro del círculo que representa el disco solar
    Vector2 center = new Vector2().x(screenSolarDiscCenterX).y(screenSolarDiscCenterY);

    InitWindow(screenWidth, screenHeight, "Bandera de Japón en RayLib");

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(WHITE); //Limpiar el fondo de color Blanco
        /*
         * Dibujar un círculo (el disco solar) de color rojo
         * en el centro de la pantalla
         * cuyo diámetro sea 3/5 del asta de la bandera
         */
        // Usamos la función DrawCircleV, la cual recibe como argumento el vector2D
        DrawCircleV(center, screenSolarDiscRadius, RED);
        EndDrawing();
    }
    CloseWindow();
}