package stars.five_pointed.helpers;

import com.raylib.Raylib;

import static com.raylib.Raylib.*;

public class Vector2Helper {

    public static void rotate(Raylib.Vector2 vertices, float angleDegrees) {
        for (int i = 0; i < vertices.capacity(); i++) {
            vertices.position(i).put(Vector2Rotate(vertices, (float) Math.toRadians(angleDegrees)));
        }
    }

    /**
     * Es más eficiente, no hay que volver a calcular el seno y el coseno
     * cada vez
     * No genera memoria temporal al no crear un Vector2 temporal resultado de Vector2Rotate
     */
    public static void rotateEx(Raylib.Vector2 vertices, float angleDegrees) {
        float angleRadians = (float) Math.toRadians(angleDegrees);
        float cos = (float) Math.cos(angleRadians);
        float sin = (float) Math.sin(angleRadians);

        for (int i = 0; i < vertices.capacity(); i++) {
            vertices.position(i);
            float x = vertices.x();
            float y = vertices.y();

            vertices.x(x * cos - y * sin);
            vertices.y(x * sin + y * cos);
        }
    }

    public static void scale(Raylib.Vector2 vertices, float scaleFactor) {
        for (int i = 0; i < vertices.capacity(); i++) {
            vertices.position(i).put(Vector2Scale(vertices, scaleFactor));
        }
    }

    public static void scaleEx(Raylib.Vector2 vertices, float scaleFactor) {
        for (int i = 0; i < vertices.capacity(); i++) {
            vertices.position(i);
            vertices.x(vertices.x() * scaleFactor);
            vertices.y(vertices.y() * scaleFactor);
        }
    }

    public static void translate(Raylib.Vector2 vertices, Raylib.Vector2 position) {
        for (int i = 0; i < vertices.capacity(); i++) {
            vertices.position(i).put(Vector2Add(vertices, position));
        }
    }

    public static void translateEx(Vector2 vertices, Vector2 position) {
        for (int i = 0; i < vertices.capacity(); i++) {
            vertices.position(i);
            vertices.x(vertices.x() + position.x());
            vertices.y(vertices.y() + position.y());
        }
    }

    public static void transform(Vector2 vertices, Vector2 position, float rotation, float scaleFactor) {
        rotate(vertices, rotation);
        scale(vertices, scaleFactor);
        translate(vertices, position);
    }

    public static void transformEx(Vector2 vertices, Vector2 position, float rotation, float scaleFactor) {
        rotateEx(vertices, rotation);
        scaleEx(vertices, scaleFactor);
        translateEx(vertices, position);
    }
}
