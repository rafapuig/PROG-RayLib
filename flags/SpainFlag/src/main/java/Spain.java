import static com.raylib.Colors.*;
import static com.raylib.Helpers.newColor;
import static com.raylib.Raylib.*;

final float HOIST = 1.0f;
final float FLY = HOIST * 3.0f / 2.0f;
final float STRIPE_TOP = HOIST / 4.0f;
final float STRIPE_BOTTOM = STRIPE_TOP;
final float STRIPE_CENTRAL = 1.0f - STRIPE_TOP - STRIPE_BOTTOM;

final float BADGE = 2.0f / 5.0f;
final String BADGE_RESOURCE_PATH = "textures/Escudo_de_Espana.svg";

final int SCREEN_HEIGHT = 1080;
final int SCREEN_WIDTH = (int) (SCREEN_HEIGHT * FLY / HOIST);

final int FLAG_WIDTH = SCREEN_WIDTH;
final int FLAG_HEIGHT = SCREEN_HEIGHT;

final int BADGE_IMAGE_SIZE = (int) (FLAG_HEIGHT * BADGE);

final int STRIPE_TOP_HEIGHT = (int) (FLAG_HEIGHT * STRIPE_TOP);
final int STRIPE_BOTTOM_HEIGHT = (int) (FLAG_HEIGHT * STRIPE_BOTTOM);
final int STRIPE_CENTRAL_HEIGHT = (int) (FLAG_HEIGHT * STRIPE_CENTRAL);

final int BADGE_POS_X = (FLAG_HEIGHT - BADGE_IMAGE_SIZE) / 2;
final int BADGE_POS_Y = (FLAG_HEIGHT - BADGE_IMAGE_SIZE) / 2;

Color SPAIN_FLAG_RED_STRIPE = newColor(198, 0, 33, 255);
Color SPAIN_FLAG_YELLOW_STRIPE = newColor(255, 196, 0, 255);

void main() {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Bandera de España en RayLib");
    SetTargetFPS(60);

    /**
     * Los assets lo vamos a colocar el la carpeta resources del módulo
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

        // Franja roja superior
        int posY = 0;
        DrawRectangle(0, posY, // Posición x,y
                FLAG_WIDTH, // ancho de la franja
                STRIPE_TOP_HEIGHT, // alto de la franja superior
                SPAIN_FLAG_RED_STRIPE); // Color de la franja Rojo

        // Franja central amarilla
        posY += STRIPE_TOP_HEIGHT;
        DrawRectangle(0, posY, // y es el alto de la franja superior
                FLAG_WIDTH, // ancho de la franja
                STRIPE_CENTRAL_HEIGHT, // alto de la franja central
                SPAIN_FLAG_YELLOW_STRIPE); // Color de la franja Amarillo

        //Franja roja inferior (y es el alto acumulado de las franjas anteriores)
        posY += STRIPE_CENTRAL_HEIGHT;
        DrawRectangle(0, posY,
                FLAG_WIDTH,
                STRIPE_BOTTOM_HEIGHT,
                SPAIN_FLAG_RED_STRIPE);

        //Escudo de la bandera
        DrawTexture(badge, BADGE_POS_X, BADGE_POS_Y, WHITE);
        EndDrawing();
    }
    CloseWindow();
    Assets.unloadAll();
}