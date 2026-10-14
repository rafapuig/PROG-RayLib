/**
 * https://es.wikipedia.org/wiki/Bandera_de_Espa%C3%B1a#/media/Archivo:Flag_of_Spain_construction_sheet.svg
 */

import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Raylib.*;

static final float HOIST = 2f; // El asta de la bandera (alto)
final float FLY = HOIST * 3.0f / 2.0f; // El vuelo de la bandera (ancho)

static final float STRIPE_TOP = HOIST / 4.0f; // alto de la franja superior (1/4 del asta)
static final float STRIPE_BOTTOM = STRIPE_TOP; // alto de la franja inferior (igual que la superior)
static final float STRIPE_CENTRAL = HOIST - STRIPE_TOP - STRIPE_BOTTOM; // lo que quede del alto (1/2 del asta)

final float BADGE = HOIST * 2.0f / 5.0f; // altura del escudo (2/5 de la altura del asta)
final String BADGE_RESOURCE_PATH = "textures/Escudo_de_Espana.svg";

static final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = (int) (SCREEN_HEIGHT * FLY / HOIST);

static final float RATIO = SCREEN_HEIGHT / HOIST;

final int FLAG_WIDTH = SCREEN_WIDTH;

final int BADGE_IMAGE_SIZE = (int) (RATIO * BADGE);

static final int STRIPE_TOP_HEIGHT = (int) (RATIO * STRIPE_TOP);
static final int STRIPE_BOTTOM_HEIGHT = (int) (RATIO * STRIPE_BOTTOM);
static final int STRIPE_CENTRAL_HEIGHT = (int) (RATIO * STRIPE_CENTRAL);

final int BADGE_POS_X = (SCREEN_HEIGHT - BADGE_IMAGE_SIZE) / 2;
final int BADGE_POS_Y = (SCREEN_HEIGHT - BADGE_IMAGE_SIZE) / 2;

static Color SPAIN_FLAG_RED_STRIPE = newColor(198, 0, 33, 255);
static Color SPAIN_FLAG_YELLOW_STRIPE = newColor(255, 196, 0, 255);

enum Stripe {
    Top(STRIPE_TOP_HEIGHT, SPAIN_FLAG_RED_STRIPE),
    Central(STRIPE_CENTRAL_HEIGHT, SPAIN_FLAG_YELLOW_STRIPE),
    Bottom(STRIPE_BOTTOM_HEIGHT, SPAIN_FLAG_RED_STRIPE);

    final int stripeHeight;
    final Color stripeColor;

    public int getStripeHeight() {
        return stripeHeight;
    }
    public Color getStripeColor() {
        return stripeColor;
    }

    Stripe(int stripeHeight, Color stripeColor) {
        this.stripeHeight = stripeHeight;
        this.stripeColor = stripeColor;
    }
}

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



    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(BLACK);

        int posY = 0;
        for (Stripe stripe : Stripe.values()) {
            DrawRectangle(0, posY, // Posición x,y
                    FLAG_WIDTH, // ancho de la franja
                    stripe.getStripeHeight(), // alto de la franja i-ésima
                    stripe.getStripeColor()); // Color de la franja i-ésima

            posY += stripe.getStripeHeight();
        }

        //Escudo de la bandera
        DrawTexture(badge, BADGE_POS_X, BADGE_POS_Y, WHITE);
        EndDrawing();
    }
    CloseWindow();
    Assets.unloadAll();
}