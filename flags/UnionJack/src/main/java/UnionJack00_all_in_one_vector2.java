import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Colors.WHITE;
import static com.raylib.Helpers.newColor;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;

Vector2 generateFivePointedStar(Vector2 location, float rotation, float scale) {

    Vector2 vertices = new Vector2(NUM_VERTICES + 1);

    final float startAngle = START_ANGLE_DEGREES + rotation;
    final float angleStep = (float) Math.toRadians(ANGLE_BETWEEN_VERTICES_DEGREES);

    float radians = (float) Math.toRadians(startAngle);

    for (int i = 0; i < NUM_VERTICES; i++) {

        vertices.position(i);

        float cos = (float) Math.cos(radians);
        float sin = (float) -Math.sin(radians);

        final float radius = scale * (i % 2 == 0 ? 1 : INNER_TO_OUTER_RADIUS_RATIO);

        vertices
                .x(location.x() + cos * radius)
                .y(location.y() + sin * radius);

        radians += angleStep;
    }

    // En la última posición añadimos el punto central
    vertices.position(NUM_VERTICES).put(location);

    return vertices;
}

void drawStar(int index, Vector2 points) {

    int offset = index * (NUM_VERTICES + 1);

    Vector2 center = points.position(offset + NUM_VERTICES).getPointer();

    for (int i = 0; i < NUM_VERTICES; i += 2) {
        Vector2 outer = points.position(offset + i).getPointer();
        Vector2 inner = points.position(offset + i + 1).getPointer();
        Vector2 nextOuter = points.position(offset + (i + 2) % NUM_VERTICES).getPointer();

        DrawTriangle(center, outer, inner, RAYWHITE);
        DrawTriangle(center, inner, nextOuter, WHITE);
    }
}

int calculateTotalStars() {

    int count = 0;

    for (int i = 0; i < NUM_VERTICAL_STARS; i++) {

        final boolean isOddRow = i % 2 == 1;
        final int numStarsInTheRow = NUM_HORIZONTAL_STARS - (isOddRow ? 1 : 0);

        count += numStarsInTheRow;
    }
    return count;
}


final Color UNION_JACK_BLUE = newColor(1, 33, 105, 255);

final float STAR_RADIUS = 60f;
final int NUM_HORIZONTAL_STARS = 6;
final int NUM_VERTICAL_STARS = 9;

final int SCREEN_WIDTH = (int) (NUM_HORIZONTAL_STARS * STAR_RADIUS * 2 * 2);
final int SCREEN_HEIGHT = (int) ((NUM_VERTICAL_STARS - 1) * STAR_RADIUS * 1.5 + STAR_RADIUS * 2 * 2);

final float OFFSET_HORIZONTAL = SCREEN_WIDTH - STAR_RADIUS * 2 * (NUM_HORIZONTAL_STARS * 2 - 1);
final float OFFSET_VERTICAL = SCREEN_HEIGHT - ((NUM_VERTICAL_STARS - 1) * STAR_RADIUS * 1.5f + STAR_RADIUS * 2);
final float OFFSET_TOP = OFFSET_VERTICAL / 2;
final float OFFSET_LEFT = OFFSET_HORIZONTAL / 2;

final int NUM_STARS = 50;


void main() {
    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Union Jack");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Vector2 stars = generateUnionJackStars();

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        final int numStars = Math.toIntExact(stars.capacity() / (NUM_VERTICES + 1));

        for (int i = 0; i < numStars; i++) {
            drawStar(i, stars);
        }

        DrawFPS(20, 20);
        EndDrawing();
    }

    stars.deallocate();

    CloseWindow();
}

Vector2 generateUnionJackStars() {
    final int numStars = calculateTotalStars();

    Vector2 stars = new Vector2((long) numStars * (NUM_VERTICES + 1));

    int count = 0;

    for (int i = 0; i < NUM_VERTICAL_STARS; i++) {

        final float y = OFFSET_TOP + STAR_RADIUS + STAR_RADIUS * 1.5f * i;

        boolean isOddRow = i % 2 == 1;

        final int numStarsInTheRow = NUM_HORIZONTAL_STARS - (isOddRow ? 1 : 0);

        for (int j = 0; j < numStarsInTheRow; j++, count++) {

            float x = OFFSET_LEFT + STAR_RADIUS + STAR_RADIUS * 4 * j;
            if (isOddRow) x += STAR_RADIUS * 2;

            final Vector2 location = new Vector2().x(x).y(y);

            final var points = generateFivePointedStar(location, 0, STAR_RADIUS);
            for(int k = 0; k <= NUM_VERTICES; k++) {
                stars.position((long) count * (NUM_VERTICES + 1) + k).put(points.position(k));
            }

        }
    }
    return stars;
}