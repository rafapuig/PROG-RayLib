/**
 * https://thumb.wikimedia.org/wikipedia/commons/thumb/a/ab/Flag_of_the_United_States_specification.svg/1920px-Flag_of_the_United_States_specification.svg.png?utm_source=es.wikipedia.org&utm_campaign=imageinfo&utm_content=thumbnail
 */

import static com.raylib.Colors.WHITE;
import static com.raylib.Helpers.newColor;
import static com.raylib.Helpers.newVector2;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;

Vector2 generateStar(float radius) {
    Vector2 vertices = new Vector2(NUM_VERTICES);

    final float angleStep = (float) Math.toRadians(ANGLE_BETWEEN_VERTICES_DEGREES);
    float angle = (float) Math.toRadians(START_ANGLE_DEGREES);

    for (int i = 0; i < NUM_VERTICES; i++, angle += angleStep) {

        float vertexRadius = i % 2 == 0 ? 1 : INNER_TO_OUTER_RADIUS_RATIO;

        float x = (float) (vertexRadius * Math.cos(angle));
        float y = (float) (vertexRadius * -Math.sin(angle));

        vertices.position(i).x(radius * x).y(radius * y);
    }
    return vertices;
}


/**
 * Cuidado con el rendimiento, solamente se recomienda usar Vector2 para llamadas nativas de dibujado a RayLib
 * para mantenimiento de la información, se recomienda usar variables y estructuras nativas de Java
 */

void drawStar(final Vector2 vertices, final Vector2 center, final float thickness, Color color) {
    for (int i = 0; i < NUM_VERTICES; i++) {
        Vector2 outerVertex = vertices.position(i).getPointer();
        Vector2 innerVertex = vertices.position(i + 1).getPointer();
        Vector2 nextCounterclockwiseOuterVertex = vertices.position((i + 2) % NUM_VERTICES).getPointer();

        Vector2 outerVertexPosition = Vector2Add(center, outerVertex);
        Vector2 innerVertexPosition = Vector2Add(center, innerVertex);
        Vector2 nextCounterclockwiseOuterVertexPosition = Vector2Add(center, nextCounterclockwiseOuterVertex);

        DrawTriangle(center, outerVertexPosition, innerVertexPosition, color);
        DrawTriangle(center, innerVertexPosition, nextCounterclockwiseOuterVertexPosition, color);

        // Liberar recursos
        outerVertexPosition.deallocate();
        innerVertexPosition.deallocate();
        nextCounterclockwiseOuterVertexPosition.deallocate();
    }
}


void drawStar2(final Vector2 vertices, final Vector2 center, final float thickness, Color color) {

    for (int i = 0; i < NUM_VERTICES; i++) {
        Vector2 vertex1 = vertices.position(i).getPointer();
        Vector2 vertex2 = vertices.position(i + 1).getPointer();

        Vector2 vertex1Position = Vector2Add(center, vertex1);
        Vector2 vertex2Position = Vector2Add(center, vertex2);

        DrawTriangle(center, vertex1Position, vertex2Position, color);

        vertex1Position.deallocate();

        vertex1Position = vertex2Position;

        vertex2 = vertices.position((i + 2) % NUM_VERTICES).getPointer();
        vertex2Position = Vector2Add(center, vertex2);

        DrawTriangle(center, vertex1Position, vertex2Position, color);

        // Liberar recursos
        vertex1Position.deallocate();
        vertex2Position.deallocate();
    }
}


void drawStar3(final Vector2 vertices, final Vector2 center, final float thickness, Color color) {

    Vector2 firstVertex = center;

    for (int i = 0; i < NUM_VERTICES; i++) {

        vertices.position(i);
        Vector2 secondVertex = Vector2Add(center, vertices);

        vertices.position(i + 1);
        Vector2 thirdVertex = Vector2Add(center, vertices);

        DrawTriangle(firstVertex, secondVertex, thirdVertex, color);

        secondVertex.deallocate();

        secondVertex = thirdVertex;

        vertices.position((i + 2) % NUM_VERTICES);
        thirdVertex = Vector2Add(center, vertices);

        DrawTriangle(firstVertex, secondVertex, thirdVertex, color);

        // Liberar recursos
        secondVertex.deallocate();
        thirdVertex.deallocate();
    }
}

final int NUM_STRIPES = 13;
final int NUM_HORIZONTAL_STARS = 6;
final int NUM_VERTICAL_STARS = 9;

final float A = 1.0f; // anchura
final float B = 1.9f; // altura
final float C = A * 7 / NUM_STRIPES; // alto del cantón
final float D = B * 2 / 5; // ancho del cantón
final float E = C / (NUM_VERTICAL_STARS + 1); // espaciado entre líneas de estrellas
final float F = E;
final float G = D / (NUM_HORIZONTAL_STARS * 2); // espaciado entre columnas de estrellas
final float H = G;
final float L = A / NUM_STRIPES; // ancho de las franjas
final float K = L * 4 / 5; // diámetro de la estrella

final float scale = 1080.0f;

final Color UNION_JACK_BLUE = newColor(0, 40, 104, 255);
final Color RED_STRIPE = newColor(191, 10, 48, 255);
final Color STAR_COLOR = newColor(241, 241, 241, 255);

final int SCREEN_HEIGHT = (int) (A * scale);
final int SCREEN_WIDTH = (int) (B * scale);

void main() {

    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Bandera de Estados Unidos");
    SetTargetFPS(GetMonitorRefreshRate(1));

    // Generar vertices de una estrella de radio K / 2 * scale con centro en origen 0,0
    Vector2 star = generateStar(K / 2 * scale);

    while (!WindowShouldClose()) {
        BeginDrawing();

        // Dibujar las 13 franjas
        for (int i = 0; i < NUM_STRIPES; i++) {

            // Color de la franja (pares ROJO, impares BLANCO)
            final Color stripeColor = i % 2 == 0 ? RED_STRIPE : WHITE;

            // Dibujar la franja i-ésima
            int stripeWidth = (int) (B * scale);
            int stripeHeight = (int) (L * scale);
            int stripeYOffset = (int) (i * L * scale);
            DrawRectangle(0, stripeYOffset, stripeWidth, stripeHeight, stripeColor);
        }

        // Dibujar el fondo del cantón
        int cantonWidth = (int) (D * scale);
        int cantonHeight = (int) (C * scale);
        DrawRectangle(0, 0, cantonWidth, cantonHeight, UNION_JACK_BLUE);

        // Dibujar estrellas
        for (int i = 0; i < NUM_VERTICAL_STARS; i++) {

            // Si es una franja impar hay que dibujar una estrella menos
            final boolean isOddRow = (i % 2 == 1);
            final int numStarsInRow = NUM_HORIZONTAL_STARS - (isOddRow ? 1 : 0);

            for (int j = 0; j < numStarsInRow; j++) {
                final float x = (j * 2 + 1 + (isOddRow ? 1 : 0)) * G * scale;
                final float y = (i + 1) * E * scale;

                //DrawCircle((int) x, (int) y, 30, WHITE);

                try (final Vector2 position = newVector2(x, y)) {
                    drawStar3(star, position, 1.0f, STAR_COLOR);
                    //position.deallocate(); // con try with resources no es necesario
                }
            }
        }

        DrawFPS(20, 20);
        EndDrawing();
    }

    // Liberar recursos
    star.deallocate();

    CloseWindow();
}