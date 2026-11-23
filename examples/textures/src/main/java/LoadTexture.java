import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;
import static org.bytedeco.javacpp.Loader.extractResource;


void main() throws IOException {

    InitWindow(1920, 1080, "");
    final int frameRate = GetMonitorRefreshRate(1);
    SetTargetFPS(frameRate);

    //IO.println(System.getProperty("user.dir"));

    //Texture texture = LoadTexture("examples/textures/build/resources/main/character.png");
    //Texture texture = LoadTexture("examples/textures/resources/character.png");
    //File file = extractResource("character.png", null,"raylib-",".png");

    //File file = extractResource("character.png", null,null,null);
    //Texture texture = LoadTexture(file.getAbsolutePath());

    Texture texture = AssetManager.loadTexture("character.png");

    while (!WindowShouldClose()) {
        BeginDrawing();
        ClearBackground(RAYWHITE);
        DrawTexture(texture, 0, 0, WHITE);
        EndDrawing();
    }
    CloseWindow();

}