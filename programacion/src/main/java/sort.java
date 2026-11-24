import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

void main() {

    // -------------------------------------------------
    // CONFIGURACIÓN DE LA VENTANA
    // -------------------------------------------------

    int screenWidth = 1100;
    int screenHeight = 700;

    InitWindow(screenWidth, screenHeight, "Visualizador de algoritmos de ordenacion");
    SetTargetFPS(60);

    // -------------------------------------------------
    // DATOS DEL ARRAY
    // -------------------------------------------------

    int n = 50;

    int[] numbers = new int[n];
    int[] original = new int[n];

    boolean[] fixed = new boolean[n];

    // Generar el array inicial
    for (int k = 0; k < n; k++) {
        original[k] = (int) (Math.random() * 96) + 5;
        numbers[k] = original[k];
    }

    // -------------------------------------------------
    // CONFIGURACIÓN DE LA REPRESENTACIÓN
    // -------------------------------------------------

    int startX = 40;
    int baseY = 550;

    int barWidth = 18;
    int spacing = 2;

    float scale = 3.5f;

    // -------------------------------------------------
    // CONFIGURACIÓN DEL ALGORITMO
    // -------------------------------------------------

    // 1 = Bubble Sort
    // 2 = Selection Sort
    // 3 = Insertion Sort

    int algorithm = 1;

    boolean running = false;
    boolean finished = false;

    // Índices utilizados por los algoritmos
    int i = 0;
    int j = 0;
    int minIndex = 0;

    // Variables para Insertion Sort
    int keyValue = 0;
    boolean insertionActive = false;

    // Elementos destacados en cada operación
    int activeA = -1;
    int activeB = -1;

    boolean swapping = false;

    // Contadores
    int comparisons = 0;
    int swaps = 0;
    int movements = 0;

    // Control de velocidad
    float delay = 0.15f;
    float accumulator = 0;

    // -------------------------------------------------
    // BUCLE PRINCIPAL
    // -------------------------------------------------

    while (!WindowShouldClose()) {

        // =============================================
        // 1. CONTROL DEL TECLADO
        // =============================================

        boolean reset = false;
        boolean newRandom = false;

        if (IsKeyPressed(KEY_ONE)) {
            algorithm = 1;
            reset = true;
            running = false;
        }

        if (IsKeyPressed(KEY_TWO)) {
            algorithm = 2;
            reset = true;
            running = false;
        }

        if (IsKeyPressed(KEY_THREE)) {
            algorithm = 3;
            reset = true;
            running = false;
        }

        if (IsKeyPressed(KEY_R)) {
            newRandom = true;
            reset = true;
            running = false;
        }

        if (IsKeyPressed(KEY_SPACE)) {

            if (finished) {
                reset = true;
            }

            running = !running;
        }

        if (IsKeyPressed(KEY_EQUAL) || IsKeyPressed(KEY_KP_ADD)) {
            delay -= 0.02f;

            if (delay < 0.02f) {
                delay = 0.02f;
            }
        }

        if (IsKeyPressed(KEY_MINUS) || IsKeyPressed(KEY_KP_SUBTRACT)) {
            delay += 0.02f;

            if (delay > 1.0f) {
                delay = 1.0f;
            }
        }

        // =============================================
        // 2. REINICIAR EL ALGORITMO
        // =============================================

        if (newRandom) {

            for (int k = 0; k < n; k++) {
                original[k] = (int) (Math.random() * 96) + 5;
            }
        }

        if (reset) {

            for (int k = 0; k < n; k++) {
                numbers[k] = original[k];
                fixed[k] = false;
            }

            i = 0;
            j = 0;
            minIndex = 0;

            keyValue = 0;
            insertionActive = false;

            activeA = -1;
            activeB = -1;

            swapping = false;

            comparisons = 0;
            swaps = 0;
            movements = 0;

            finished = false;
            accumulator = 0;
        }

        // =============================================
        // 3. ACTUALIZAR EL ALGORITMO
        // =============================================

        if (running && !finished) {

            accumulator += GetFrameTime();

            if (accumulator >= delay) {

                accumulator -= delay;

                // -------------------------------------
                // BUBBLE SORT
                // -------------------------------------

                if (algorithm == 1) {

                    activeA = j;
                    activeB = j + 1;

                    swapping = false;

                    comparisons++;

                    if (numbers[j] > numbers[j + 1]) {

                        int temp = numbers[j];

                        numbers[j] = numbers[j + 1];
                        numbers[j + 1] = temp;

                        swaps++;

                        swapping = true;
                    }

                    j++;

                    // Ha terminado una pasada
                    if (j >= n - i - 1) {

                        fixed[n - i - 1] = true;

                        i++;
                        j = 0;

                        // Todas las posiciones ordenadas
                        if (i >= n - 1) {

                            for (int k = 0; k < n; k++) {
                                fixed[k] = true;
                            }

                            finished = true;
                            running = false;
                        }
                    }
                }

                // -------------------------------------
                // SELECTION SORT
                // -------------------------------------

                else if (algorithm == 2) {

                    swapping = false;

                    if (j < n) {

                        activeA = j;
                        activeB = minIndex;

                        comparisons++;

                        if (numbers[j] < numbers[minIndex]) {
                            minIndex = j;
                        }

                        j++;

                    } else {

                        // Intercambiar el mínimo encontrado
                        // con el primer elemento de la zona
                        // que todavía no está ordenada.

                        activeA = i;
                        activeB = minIndex;

                        if (minIndex != i) {

                            int temp = numbers[i];

                            numbers[i] = numbers[minIndex];
                            numbers[minIndex] = temp;

                            swaps++;

                            swapping = true;
                        }

                        fixed[i] = true;

                        i++;

                        if (i >= n - 1) {

                            for (int k = 0; k < n; k++) {
                                fixed[k] = true;
                            }

                            finished = true;
                            running = false;

                        } else {

                            minIndex = i;
                            j = i + 1;
                        }
                    }
                }

                // -------------------------------------
                // INSERTION SORT
                // -------------------------------------

                else if (algorithm == 3) {

                    swapping = false;

                    // Comenzar una nueva inserción
                    if (!insertionActive) {

                        keyValue = numbers[i];

                        j = i - 1;

                        insertionActive = true;

                        activeA = i;
                        activeB = -1;

                    } else {

                        // Buscar la posición de inserción
                        if (j >= 0) {

                            activeA = j;
                            activeB = j + 1;

                            comparisons++;

                            if (numbers[j] > keyValue) {

                                // Desplazar el elemento
                                numbers[j + 1] = numbers[j];

                                movements++;

                                j--;

                                swapping = true;

                            } else {

                                // Insertar el elemento
                                numbers[j + 1] = keyValue;

                                movements++;

                                insertionActive = false;

                                i++;
                            }

                        } else {

                            // Insertar al principio
                            numbers[0] = keyValue;

                            movements++;

                            insertionActive = false;

                            i++;
                        }
                    }

                    // Comprobar si ha terminado
                    if (i >= n) {

                        for (int k = 0; k < n; k++) {
                            fixed[k] = true;
                        }

                        finished = true;
                        running = false;
                    }
                }
            }
        }

        // =============================================
        // 4. DIBUJAR LA VENTANA
        // =============================================

        BeginDrawing();

        ClearBackground(RAYWHITE);

        // ---------------------------------------------
        // TÍTULO
        // ---------------------------------------------

        String algorithmName = "";

        if (algorithm == 1) {
            algorithmName = "BUBBLE SORT";
        } else if (algorithm == 2) {
            algorithmName = "SELECTION SORT";
        } else {
            algorithmName = "INSERTION SORT";
        }

        DrawText(
                algorithmName,
                40, 25,
                30,
                DARKGRAY
        );

        // ---------------------------------------------
        // CONTROLES
        // ---------------------------------------------

        DrawText(
                "1: Bubble   2: Selection   3: Insertion",
                40, 75,
                18,
                DARKGRAY
        );

        DrawText(
                "ESPACIO: iniciar/pausar   R: nuevo array   +/-: velocidad",
                40, 105,
                16,
                DARKGRAY
        );

        // ---------------------------------------------
        // REPRESENTACIÓN DEL ARRAY
        // ---------------------------------------------

        for (int k = 0; k < n; k++) {

            int x = startX + k * (barWidth + spacing);

            int height = (int) (numbers[k] * scale);

            int y = baseY - height;

            // Color por defecto
            var barColor = LIGHTGRAY;

            // Posiciones definitivas
            if (fixed[k]) {
                barColor = GREEN;
            }

            // Zona ordenada de Insertion Sort
            if (algorithm == 3 && k < i && !finished) {
                barColor = SKYBLUE;
            }

            // Elementos destacados
            if (k == activeA) {
                barColor = YELLOW;
            }

            if (k == activeB) {

                if (algorithm == 2) {
                    barColor = RED;
                } else {
                    barColor = swapping ? RED : YELLOW;
                }
            }

            // Intercambio o desplazamiento
            if (swapping && (k == activeA || k == activeB)) {
                barColor = RED;
            }

            // Dibujar la barra
            DrawRectangle(
                    x,
                    y,
                    barWidth,
                    height,
                    barColor
            );

            // Dibujar el borde
            DrawRectangleLines(
                    x,
                    y,
                    barWidth,
                    height,
                    DARKGRAY
            );

            // Mostrar algunos índices
            if (k % 5 == 0) {

                DrawText(
                        Integer.toString(k),
                        x,
                        baseY + 8,
                        12,
                        DARKGRAY
                );
            }
        }

        // Línea base
        DrawRectangle(
                startX,
                baseY,
                n * (barWidth + spacing),
                2,
                DARKGRAY
        );

        // ---------------------------------------------
        // INFORMACIÓN DEL ALGORITMO
        // ---------------------------------------------

        DrawText(
                "Comparaciones: " + comparisons,
                40, 600,
                20,
                DARKGRAY
        );

        DrawText(
                "Intercambios: " + swaps,
                300, 600,
                20,
                DARKGRAY
        );

        DrawText(
                "Desplazamientos: " + movements,
                540, 600,
                20,
                DARKGRAY
        );

        // ---------------------------------------------
        // ESTADO
        // ---------------------------------------------

        String status;

        if (finished) {
            status = "FINALIZADO";
        } else if (running) {
            status = "EJECUTANDO";
        } else {
            status = "PAUSADO";
        }

        DrawText(
                "Estado: " + status,
                40, 640,
                20,
                BLUE
        );

        DrawText(
                "Velocidad: " + String.format("%.2f", delay) + " s",
                300, 640,
                20,
                DARKGRAY
        );

        // Mostrar la clave de Insertion Sort
        if (algorithm == 3 && insertionActive) {

            DrawText(
                    "Clave: " + keyValue,
                    650, 640,
                    20,
                    DARKGRAY
            );
        }

        // ---------------------------------------------
        // LEYENDA
        // ---------------------------------------------

        DrawRectangle(780, 25, 15, 15, LIGHTGRAY);
        DrawText("Normal", 805, 25, 15, DARKGRAY);

        DrawRectangle(780, 48, 15, 15, YELLOW);
        DrawText("Comparacion", 805, 48, 15, DARKGRAY);

        DrawRectangle(780, 71, 15, 15, RED);
        DrawText("Intercambio", 805, 71, 15, DARKGRAY);

        DrawRectangle(780, 94, 15, 15, GREEN);
        DrawText("Definitivo", 805, 94, 15, DARKGRAY);

        DrawRectangle(780, 117, 15, 15, SKYBLUE);
        DrawText("Zona ordenada", 805, 117, 15, DARKGRAY);

        EndDrawing();
    }

    CloseWindow();
}