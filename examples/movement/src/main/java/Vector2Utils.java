import com.raylib.Raylib.Vector2;

public class Vector2Utils {

    private Vector2Utils() {
    }

    public static void addInPlace(Vector2 target, Vector2 other) {
        target.x(target.x() + other.x());
        target.y(target.y() + other.y());
    }

    public static void clampInPlace(Vector2 target, Vector2 min, Vector2 max) {
        target.x(Math.clamp(target.x(), min.x(), max.x()));
        target.y(Math.clamp(target.y(), min.y(), max.y()));
    }

    public static void scaleInPlace(Vector2 target, float scale) {
        target.x(target.x() * scale);
    }

    public static void rotateInPlace(Vector2 target, float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float newX = (float) (target.x() * cos + (target.y() * -sin));
        float newY = (float) (target.x() * sin + (target.y() * cos));
        target.x(newX);
        target.y(newY);
    }
}
