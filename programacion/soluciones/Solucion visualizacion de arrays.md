# Solución: Visualización gráfica de arrays y algoritmos de ordenación con Jaylib

## 1. Planteamiento de la solución

Vamos a desarrollar un visualizador que permita representar un array mediante barras verticales y observar cómo trabajan tres algoritmos de ordenación:

- Bubble Sort.
- Selection Sort.
- Insertion Sort.

El programa tendrá una ventana de Jaylib en la que se mostrará el array y se irá actualizando su representación gráfica conforme avance el algoritmo.

La idea fundamental es que **el algoritmo no se ejecutará de principio a fin en una única llamada**. En su lugar, realizaremos una pequeña operación en cada intervalo de tiempo.

De esta manera podremos observar las comparaciones, los intercambios y los desplazamientos.

### Distribución de la ventana

La ventana tendrá tres zonas:

1. Parte superior: nombre del algoritmo y controles.
2. Parte central: representación gráfica del array.
3. Parte inferior: contadores y leyenda de colores.

### Significado de los colores

| Color | Significado |
|---|---|
| Gris claro | Elemento sin destacar |
| Amarillo | Elemento que se está examinando |
| Rojo | Elemento que se está intercambiando o desplazando |
| Verde | Elemento que ya ocupa su posición definitiva |
| Azul | Zona ordenada provisionalmente en Insertion Sort |

---

## 2. Código completo

El siguiente programa utiliza un array de 50 elementos cuyos valores se generan aleatoriamente entre 5 y 100.

Se puede seleccionar cualquiera de los tres algoritmos y observar su ejecución paso a paso.

### Controles

| Tecla | Acción |
|---|---|
| `1` | Seleccionar Bubble Sort |
| `2` | Seleccionar Selection Sort |
| `3` | Seleccionar Insertion Sort |
| `ESPACIO` | Iniciar, pausar o reanudar |
| `R` | Generar un nuevo array aleatorio |
| `+` | Aumentar la velocidad |
| `-` | Disminuir la velocidad |

### Código Java

```java
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
```

---

## 3. Explicación de las partes más importantes

### 3.1. Representación gráfica del array

La representación se realiza recorriendo el array con un bucle:

```java
for (int k = 0; k < n; k++) {

    int x = startX + k * (barWidth + spacing);

    int height = (int) (numbers[k] * scale);

    int y = baseY - height;

    DrawRectangle(x, y, barWidth, height, LIGHTGRAY);
}
```

Hay tres cálculos importantes:

**Posición horizontal**

Cada barra se dibuja a continuación de la anterior. La separación entre ellas se consigue sumando `spacing` a la anchura.

**Altura**

La altura depende del valor almacenado en el array. Multiplicamos el valor por un factor de escala.

**Posición vertical**

Como las coordenadas de pantalla crecen hacia abajo, tenemos que restar la altura a la posición de la base.

Por eso utilizamos:

```java
int y = baseY - height;
```

### 3.2. Ejecutar el algoritmo paso a paso

Esta es probablemente la parte más interesante de la práctica.

Normalmente, un algoritmo de ordenación se ejecutaría mediante varios bucles anidados:

```java
for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {

        if (numbers[j] > numbers[j + 1]) {

            int temp = numbers[j];
            numbers[j] = numbers[j + 1];
            numbers[j + 1] = temp;
        }
    }
}
```

El problema es que, cuando termina el algoritmo, el array ya está ordenado. No hemos tenido oportunidad de mostrar gráficamente las operaciones intermedias.

Para solucionarlo, hemos convertido el algoritmo en una pequeña máquina de estados.

En lugar de utilizar los bucles internos, conservamos los índices `i` y `j` entre las diferentes iteraciones del bucle principal de Jaylib.

Por ejemplo, en Bubble Sort:

```java
if (numbers[j] > numbers[j + 1]) {

    int temp = numbers[j];

    numbers[j] = numbers[j + 1];
    numbers[j + 1] = temp;
}

j++;
```

Cada vez que se ejecuta este código se realiza una única comparación y, como máximo, un intercambio.

En la siguiente iteración del bucle principal se realiza la siguiente comparación.

### 3.3. Controlar la velocidad

No queremos que el algoritmo avance en cada fotograma porque, a 60 FPS, resultaría demasiado rápido.

Por eso utilizamos un acumulador de tiempo:

```java
accumulator += GetFrameTime();

if (accumulator >= delay) {

    accumulator -= delay;

    // Ejecutar una operación del algoritmo
}
```

`GetFrameTime()` devuelve el tiempo transcurrido desde el fotograma anterior.

La variable `delay` establece cuánto tiempo debe transcurrir entre dos operaciones.

Por ejemplo:

- `0.50` segundos: dos operaciones por segundo.
- `0.15` segundos: aproximadamente siete operaciones por segundo.
- `0.02` segundos: cincuenta operaciones por segundo.

### 3.4. Representar los elementos mediante colores

Para destacar los elementos que intervienen en cada operación utilizamos dos variables:

```java
int activeA = -1;
int activeB = -1;
```

Estas variables indican las posiciones del array que queremos destacar.

Por ejemplo, en Bubble Sort:

```java
activeA = j;
activeB = j + 1;
```

Así, cuando se dibuja el array, podemos comprobar si una barra corresponde a alguna de esas posiciones y cambiar su color.

También utilizamos:

```java
boolean[] fixed = new boolean[n];
```

Este array auxiliar permite identificar las posiciones que ya están definitivamente ordenadas.

En Bubble Sort, cuando termina una pasada, el elemento más grande de la zona desordenada queda situado en su posición definitiva. Por eso podemos marcarlo en verde.

En Selection Sort sucede algo parecido: cada vez que encontramos el mínimo de la zona desordenada y lo colocamos al principio, esa posición queda definitivamente ordenada.

En Insertion Sort, sin embargo, la zona izquierda está ordenada, pero sus elementos todavía pueden desplazarse en las siguientes inserciones. Por eso la representamos en azul y no en verde.

---

## 4. Particularidades de cada algoritmo

### Bubble Sort

Se comparan elementos consecutivos.

Cuando el elemento de la izquierda es mayor que el de la derecha, se intercambian.

Al terminar cada pasada, el elemento más grande de la zona desordenada queda colocado en su posición definitiva.

Por eso utilizamos `fixed` para colorear progresivamente la parte derecha del array.

### Selection Sort

Se busca el elemento más pequeño de la zona desordenada.

Durante la búsqueda:

- `activeA` representa el elemento que estamos examinando.
- `activeB` representa el mínimo encontrado hasta ese momento.

Cuando termina la búsqueda, intercambiamos el mínimo con el primer elemento de la zona desordenada.

Así conseguimos que la zona ordenada crezca desde la izquierda.

### Insertion Sort

Este algoritmo trabaja manteniendo una zona ordenada a la izquierda.

En cada iteración:

1. Seleccionamos el siguiente elemento, que denominamos clave.
2. Comparamos la clave con los elementos anteriores.
3. Desplazamos hacia la derecha los elementos mayores que la clave.
4. Insertamos la clave en el hueco que queda libre.

A diferencia de los otros dos algoritmos, Insertion Sort no necesita intercambiar elementos. Realiza desplazamientos.

Por eso hemos añadido un contador independiente de movimientos.

---

## 5. Observaciones y mejoras

La solución anterior es funcional como visualizador didáctico, pero conviene tener presentes algunas cuestiones:

1. Los contadores de Insertion Sort contabilizan las comparaciones entre la clave y los elementos anteriores, pero no incluyen la comparación final contra el límite del array.
2. El contador de movimientos de Insertion Sort incluye tanto los desplazamientos como la inserción final de la clave.
3. La animación muestra una operación por intervalo de tiempo. Cuando se produce un intercambio, los elementos aparecen en rojo durante ese intervalo.
4. El array original se conserva en `original`, de modo que al cambiar de algoritmo podemos volver a partir de los mismos datos. Esto resulta especialmente útil para comparar los algoritmos.

### Posibles mejoras para los alumnos

Una vez que el programa funcione, se pueden plantear algunas ampliaciones:

- Permitir modificar el número de elementos del array.
- Mostrar el valor numérico encima de cada barra.
- Mostrar el número de comparaciones e intercambios en tiempo real.
- Añadir un botón para ejecutar el algoritmo hasta el final sin animación.
- Permitir ejecutar dos algoritmos simultáneamente sobre dos copias del mismo array.
- Añadir QuickSort cuando se haya estudiado recursividad.

La comparación simultánea de algoritmos sería especialmente interesante: permitiría observar que, aunque todos ordenan correctamente el mismo array, la cantidad y el tipo de operaciones que realizan pueden ser muy diferentes.