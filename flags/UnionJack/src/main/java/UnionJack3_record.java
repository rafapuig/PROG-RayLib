import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Colors.WHITE;
import static com.raylib.Helpers.newColor;
import static com.raylib.Raylib.*;
import static stars.five_pointed.FivePointerStarConstants.*;

record Star(Vector2 center, Vector2 vertices) implements AutoCloseable {
    @Override
    public void close() {
        center.deallocate();
        vertices.deallocate();
    }
}

Star generateFivePointedStar(Vector2 location, float rotation, float scale) {

    Vector2 vertices = new Vector2(NUM_VERTICES);

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

    return new Star(location, vertices);
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

Star[] generateUnionJackStars() {

    final int numStars = calculateTotalStars();

    final Star[] stars = new Star[numStars];

    int count = 0;

    for (int i = 0; i < NUM_VERTICAL_STARS; i++) {

        final float y = OFFSET_TOP + STAR_RADIUS + STAR_RADIUS * RATIO * i;

        boolean isOddRow = i % 2 == 1;

        final int numStarsInTheRow = NUM_HORIZONTAL_STARS - (isOddRow ? 1 : 0);

        for (int j = 0; j < numStarsInTheRow; j++, count++) {

            float x = OFFSET_LEFT + STAR_RADIUS + STAR_RADIUS * 4 * j;
            if (isOddRow) x += STAR_RADIUS * 2;

            final Vector2 location = new Vector2().x(x).y(y);

            stars[count] = generateFivePointedStar(location, 0, STAR_RADIUS);
        }
    }
    return stars;
}


void drawStar(Star star) {
    final int totalVertices = Math.toIntExact(star.vertices.capacity());

    for (int i = 0; i < totalVertices; i += 2) {
        star.vertices.position(i);
        Vector2 outer = star.vertices.getPointer();

        star.vertices.position(i + 1);
        Vector2 inner = star.vertices.getPointer();

        star.vertices.position((i + 2) % totalVertices);
        Vector2 nextOuter = star.vertices.getPointer();

        DrawTriangle(star.center, outer, inner, RAYWHITE);
        DrawTriangle(star.center, inner, nextOuter, WHITE);
    }
}


final Color UNION_JACK_BLUE = newColor(1, 33, 105, 255);

final float STAR_RADIUS = 60f;
final float START_DIAMETER = STAR_RADIUS * 2;
final int NUM_HORIZONTAL_STARS = 6;
final int NUM_VERTICAL_STARS = 9;
final float RATIO = 1.75f ; // 1.75f;

final int SCREEN_WIDTH = (int) (NUM_HORIZONTAL_STARS * 2 * START_DIAMETER);
final int SCREEN_HEIGHT = (int) ((NUM_VERTICAL_STARS - 1) * STAR_RADIUS * RATIO + START_DIAMETER * 2);
//final int SCREEN_HEIGHT = (int) ((NUM_VERTICAL_STARS / 2 * RATIO + 2) * START_DIAMETER);

final float OFFSET_HORIZONTAL = SCREEN_WIDTH - START_DIAMETER * (NUM_HORIZONTAL_STARS * 2 - 1);
final float OFFSET_VERTICAL = SCREEN_HEIGHT - ((NUM_VERTICAL_STARS - 1) * STAR_RADIUS * RATIO + START_DIAMETER);
final float OFFSET_TOP = OFFSET_VERTICAL / 2;
final float OFFSET_LEFT = OFFSET_HORIZONTAL / 2;

//final int NUM_STARS = 50;

void main() {
    InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Union Jack");
    SetTargetFPS(GetMonitorRefreshRate(1));

    Star[] stars = generateUnionJackStars();

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(UNION_JACK_BLUE);

        for (Star star : stars) {
            drawStar(star);
        }

        DrawFPS(20, 20);
        EndDrawing();
    }

    for (Star star : stars) {
        star.close();
    }

    CloseWindow();
}

