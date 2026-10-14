/**
 * https://es.wikipedia.org/wiki/Bandera_de_Espa%C3%B1a#/media/Archivo:Flag_of_Spain_construction_sheet.svg
 */

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Raylib.*;

final float HOIST = 2f; // El asta de la bandera (alto)
final float FLY = HOIST * 3.0f / 2.0f; // El vuelo de la bandera (ancho)

final float STRIPE_TOP = HOIST / 4.0f; // alto de la franja superior (1/4 del asta)
final float STRIPE_BOTTOM = STRIPE_TOP; // alto de la franja inferior (igual que la superior)
final float STRIPE_CENTRAL = HOIST - STRIPE_TOP - STRIPE_BOTTOM; // lo que quede del alto (1/2 del asta)

final float BADGE = HOIST * 2.0f / 5.0f; // altura del escudo (2/5 de la altura del asta)
final String BADGE_RESOURCE_PATH = "textures/Escudo_de_Espana.svg";

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = (int) (SCREEN_HEIGHT * FLY / HOIST);

final float RATIO = SCREEN_HEIGHT / HOIST;

final int FLAG_WIDTH = SCREEN_WIDTH;

final int BADGE_IMAGE_SIZE = (int) (RATIO * BADGE);

final int STRIPE_TOP_HEIGHT = (int) (RATIO * STRIPE_TOP);
final int STRIPE_BOTTOM_HEIGHT = (int) (RATIO * STRIPE_BOTTOM);
final int STRIPE_CENTRAL_HEIGHT = (int) (RATIO * STRIPE_CENTRAL);

final int BADGE_POS_X = (SCREEN_HEIGHT - BADGE_IMAGE_SIZE) / 2;
final int BADGE_POS_Y = (SCREEN_HEIGHT - BADGE_IMAGE_SIZE) / 2;

Color SPAIN_FLAG_RED_STRIPE = newColor(198, 0, 33, 255);
Color SPAIN_FLAG_YELLOW_STRIPE = newColor(255, 196, 0, 255);


void main() {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Bandera de España en RayLib");
    SetTargetFPS(GetMonitorRefreshRate(1));

    /**
     * Los assets lo vamos a colocar en la carpeta resources del módulo
     * Y los cargamos con esta clase de utilidad que permite cargar imágenes
     * en formato svg
     */
    final Texture badge = Assets
            .loadTexture(
                    BADGE_RESOURCE_PATH, // ruta al recurso imagen textura
                    BADGE_IMAGE_SIZE, // ancho de la imagen png a generar
                    BADGE_IMAGE_SIZE // alto
            );

    final int[] stripesHeights = {STRIPE_TOP_HEIGHT, STRIPE_CENTRAL_HEIGHT, STRIPE_BOTTOM_HEIGHT};
    final Color[] stripesColors = {SPAIN_FLAG_RED_STRIPE, SPAIN_FLAG_YELLOW_STRIPE, SPAIN_FLAG_RED_STRIPE};

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(BLACK);

        for (int i = 0, posY = 0; i < stripesHeights.length; posY += stripesHeights[i++]) {
            DrawRectangle(0, posY, // Posición x,y
                    FLAG_WIDTH, // ancho de la franja
                    stripesHeights[i], // alto de la franja i-ésima
                    stripesColors[i]); // Color de la franja i-ésima
        }

        //Escudo de la bandera
        DrawTexture(badge, BADGE_POS_X, BADGE_POS_Y, WHITE);
        EndDrawing();
    }
    CloseWindow();
    Assets.unloadAll();
}