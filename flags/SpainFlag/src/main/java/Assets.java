
import com.raylib.Raylib.Image;
import com.raylib.Raylib.Texture;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.raylib.Raylib.*;

public final class Assets {

    private Assets() {
    }

    private static final Map<String, Texture> textures = new HashMap<>();


    // ---------------------------------------------------------
    // Cargar textura normal
    // ---------------------------------------------------------

    public static Texture loadTexture(String resource) {

        String key = resource;

        Texture cached = textures.get(key);

        if (cached != null) {
            return cached;
        }

        try (InputStream input = getResource(resource)) {

            byte[] data = input.readAllBytes();

            Image image = LoadImageFromMemory(
                    getExtension(resource),
                    data,
                    data.length
            );

            checkImage(image, resource);

            Texture texture = LoadTextureFromImage(image);

            UnloadImage(image);

            textures.put(key, texture);

            return texture;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error leyendo el recurso: " + resource,
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // Cargar SVG con tamaño específico
    // ---------------------------------------------------------

    public static Texture loadTexture(
            String resource,
            int width,
            int height
    ) {

        String key = resource + "#" + width + "x" + height;

        Texture cached = textures.get(key);

        if (cached != null) {
            return cached;
        }

        if (!resource.toLowerCase().endsWith(".svg")) {
            return loadTexture(resource);
        }

        try (InputStream input = getResource(resource)) {

            byte[] pngData = svgToPng(
                    input,
                    width,
                    height
            );

            Image image = LoadImageFromMemory(
                    ".png",
                    pngData,
                    pngData.length
            );

            checkImage(image, resource);

            Texture texture = LoadTextureFromImage(image);

            UnloadImage(image);

            textures.put(key, texture);

            return texture;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error cargando SVG: " + resource,
                    e
            );
        }
    }


    // ---------------------------------------------------------
    // Convertir SVG -> PNG
    // ---------------------------------------------------------

    private static byte[] svgToPng(
            InputStream svg,
            int width,
            int height
    ) throws Exception {

        PNGTranscoder transcoder = new PNGTranscoder();

        transcoder.addTranscodingHint(
                PNGTranscoder.KEY_WIDTH,
                (float) width
        );

        transcoder.addTranscodingHint(
                PNGTranscoder.KEY_HEIGHT,
                (float) height
        );

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        TranscoderInput input =
                new TranscoderInput(svg);

        TranscoderOutput transcoderOutput =
                new TranscoderOutput(output);

        transcoder.transcode(
                input,
                transcoderOutput
        );

        return output.toByteArray();
    }


    // ---------------------------------------------------------
    // Obtener recurso de resources
    // ---------------------------------------------------------

    private static InputStream getResource(
            String resource
    ) {

        InputStream input =
                Assets.class
                        .getClassLoader()
                        .getResourceAsStream(resource);

        if (input == null) {
            throw new RuntimeException(
                    "No se encontró el recurso: " + resource
            );
        }

        return input;
    }


    // ---------------------------------------------------------
    // Obtener extensión
    // ---------------------------------------------------------

    private static String getExtension(
            String resource
    ) {

        int dot = resource.lastIndexOf('.');

        if (dot == -1) {
            throw new IllegalArgumentException(
                    "El recurso no tiene extensión: " + resource
            );
        }

        return resource
                .substring(dot)
                .toLowerCase();
    }


    // ---------------------------------------------------------
    // Comprobar imagen
    // ---------------------------------------------------------

    private static void checkImage(
            Image image,
            String resource
    ) {

        if (image == null ||
                image.data() == null) {

            throw new RuntimeException(
                    "No se pudo cargar la imagen: "
                            + resource
            );
        }
    }


    // ---------------------------------------------------------
    // Liberar todas las texturas
    // ---------------------------------------------------------

    public static void unloadAll() {

        for (Texture texture : textures.values()) {
            UnloadTexture(texture);
        }

        textures.clear();
    }
}
