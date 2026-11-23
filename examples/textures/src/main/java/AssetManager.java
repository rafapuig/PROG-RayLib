/*import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AssetManager {

    static String extractResource(String resource) throws IOException {
        try (InputStream input = ClassLoader.getSystemResourceAsStream(resource)) {
            if (input == null)
                throw new FileNotFoundException(resource);

            String extension = resource.substring(resource.lastIndexOf('.'));

            Path file = Files.createTempFile("raylib-", extension);

            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);

            file.toFile().deleteOnExit();

            return file.toAbsolutePath().toString();
        }
    }
}*/


import com.raylib.Raylib.Texture;
import org.bytedeco.javacpp.Loader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.raylib.Raylib.*;

public class AssetManager implements AutoCloseable {

    static private final Map<String, Texture> textures = new HashMap<>();

    static public Texture loadTexture(String name) throws IOException {

        Texture texture = textures.get(name);

        if (texture != null)
            return texture;

        File file = Loader.cacheResource(
                AssetManager.class,
                "/" + name
        );

        if (file == null)
            throw new IOException("Resource not found: " + name);

        texture = LoadTexture(file.getAbsolutePath());

        if (texture.id() == 0)
            throw new IOException("Could not load texture: " + name);

        textures.put(name, texture);

        return texture;
    }

    @Override
    public void close() {
        for (Texture texture : textures.values())
            UnloadTexture(texture);

        textures.clear();
    }
}

