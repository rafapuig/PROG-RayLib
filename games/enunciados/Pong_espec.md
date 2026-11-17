# Práctica: Pong con Java y Raylib

## Objetivo

Desarrolla una versión del clásico videojuego Pong utilizando Java y la biblioteca gráfica Raylib.

El juego tendrá dos palas, una controlada por el jugador y otra por la CPU. Una pelota se desplazará por la pantalla y rebotará en los límites superior e inferior y en las palas.

El objetivo es evitar que la pelota salga por el lateral de nuestra zona de juego.

## 1. Configuración del proyecto

El proyecto debe utilizar Gradle como sistema de construcción.

Añade la siguiente dependencia al bloque `dependencies` del archivo `build.gradle.kts`:

```kotlin
dependencies {
    implementation("uk.co.electronstudio.jaylib:jaylib:6.0.+")
}
```

Esta dependencia permite utilizar Jaylib, una biblioteca que proporciona acceso a Raylib desde Java.

Raylib facilita la creación de aplicaciones gráficas y videojuegos mediante funciones para:

- Crear y gestionar ventanas.
- Dibujar figuras geométricas y textos.
- Detectar las pulsaciones del teclado.
- Detectar colisiones.
- Controlar el tiempo y la velocidad de ejecución.

## 2. Configuración de la ventana

La aplicación debe crear una ventana con las siguientes características:

- Título: `Pong`.
- Altura: `1080` píxeles.
- Anchura: debe calcularse para mantener una proporción de 16:9 respecto a la altura.

Por tanto:

```java
SCREEN_HEIGHT = 1080;
SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9;
```

La velocidad de actualización de la aplicación debe establecerse utilizando la frecuencia de refresco del monitor:

```java
SetTargetFPS(GetMonitorRefreshRate(1));
```

No se debe utilizar una velocidad de actualización fija.

## 3. Colores

Define los siguientes colores mediante sus componentes RGBA:

| Nombre | Rojo | Verde | Azul | Alfa |
|---|---:|---:|---:|---:|
| `GREEN` | 38 | 185 | 154 | 255 |
| `DARK_GREEN` | 20 | 160 | 133 | 255 |
| `LIGHT_GREEN` | 129 | 204 | 184 | 255 |
| `YELLOWISH` | 243 | 213 | 91 | 255 |

Además, utiliza los colores predefinidos `WHITE` y `RAYWHITE` que proporciona Raylib.

## 4. Diseño del escenario

El fondo debe estar dividido verticalmente en dos mitades:

- La mitad izquierda tendrá el color `DARK_GREEN`.
- La mitad derecha tendrá el color `GREEN`.

En el centro de la pantalla se dibujará un círculo decorativo de color `LIGHT_GREEN`.

Sus características serán:

- Centro: `(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2)`.
- Radio: `SCREEN_HEIGHT * 200 / 1080`.

Sobre el círculo se dibujará una línea vertical blanca que dividirá el campo de juego en dos mitades.

La línea debe comenzar en `(SCREEN_WIDTH / 2, 0)` y terminar en `(SCREEN_WIDTH / 2, SCREEN_HEIGHT)`.

Su grosor será de un píxel.

## 5. Las palas

El juego tendrá dos palas rectangulares de color blanco.

Ambas palas tendrán las mismas dimensiones:

- Anchura: `SCREEN_WIDTH * 25 / 1920`.
- Altura: `SCREEN_HEIGHT * 150 / 1080`.
- Margen respecto al borde lateral: `SCREEN_WIDTH * 20 / 1920`.

Las palas tendrán las esquinas redondeadas. Para ello, utiliza `DrawRectangleRounded` con un valor de redondez de `0.8` y `0` segmentos.

### Posición inicial

Las dos palas deben comenzar centradas verticalmente.

La coordenada Y inicial se calculará mediante:

```java
(SCREEN_HEIGHT - PADDLE_HEIGHT) / 2
```

La pala de la CPU se situará a la izquierda de la pantalla:

```java
PADDLE_SCREEN_MARGIN
```

La pala del jugador se situará a la derecha:

```java
SCREEN_WIDTH - PADDLE_WIDTH - PADDLE_SCREEN_MARGIN
```

### Movimiento de la pala del jugador

El jugador podrá controlar su pala mediante las teclas:

- Flecha arriba: desplazar la pala hacia arriba.
- Flecha abajo: desplazar la pala hacia abajo.

La velocidad de desplazamiento será de `7 * 60` píxeles por segundo.

El movimiento debe tener en cuenta el tiempo transcurrido entre fotogramas mediante `GetFrameTime()`.

La pala no podrá salir de los límites superior e inferior de la ventana.

### Movimiento de la pala de la CPU

La pala de la CPU seguirá verticalmente la posición de la pelota.

Para ello, compara la coordenada Y del centro de la pala con la coordenada Y de la pelota:

- Si el centro de la pala está por encima de la pelota, debe desplazarse hacia abajo.
- Si el centro de la pala está por debajo de la pelota, debe desplazarse hacia arriba.

La velocidad de desplazamiento será la misma que la de la pala del jugador.

## 6. La pelota

La pelota será un círculo de color `YELLOWISH`.

Sus características serán:

- Radio: `SCREEN_HEIGHT * 22 / 1080`.
- Posición inicial X: `SCREEN_WIDTH / 2`.
- Posición inicial Y: `SCREEN_HEIGHT / 2`.

La pelota tendrá dos componentes de velocidad:

- Velocidad horizontal inicial: `SCREEN_WIDTH * 540 / 1920` píxeles por segundo.
- Velocidad vertical inicial: `SCREEN_HEIGHT * 540 / 1080` píxeles por segundo.

Inicialmente, ambas velocidades serán positivas, por lo que la pelota se desplazará hacia la derecha y hacia abajo.

### Inicio del movimiento

Al iniciar la aplicación, la pelota permanecerá inmóvil en el centro de la pantalla.

El juego comenzará cuando el jugador pulse la tecla ENTER.

A partir de ese momento, la pelota comenzará a desplazarse.

### Movimiento

La posición de la pelota debe actualizarse teniendo en cuenta el tiempo transcurrido entre fotogramas:

```java
ballX += ballVelocityX * GetFrameTime();
ballY += ballVelocityY * GetFrameTime();
```

### Rebotes en los límites superior e inferior

La pelota debe rebotar cuando alcance los límites superior o inferior de la ventana.

Para detectar estas situaciones, debes tener en cuenta el radio de la pelota:

- Si `ballY + BALL_RADIUS` supera `SCREEN_HEIGHT`, la pelota ha alcanzado el límite inferior.
- Si `ballY - BALL_RADIUS` es menor que `0`, la pelota ha alcanzado el límite superior.

Cuando se produzca cualquiera de estas situaciones, debe invertirse el sentido de la velocidad vertical.

### Colisiones con las palas

Debes utilizar la función `CheckCollisionCircleRec` para comprobar las colisiones entre la pelota y las palas.

Cuando la pelota choque con la pala del jugador:

- Debe colocarse justo a la izquierda de la pala.
- Debe invertirse el sentido de su velocidad horizontal.

Cuando choque con la pala de la CPU:

- Debe colocarse justo a la derecha de la pala.
- Debe invertirse el sentido de su velocidad horizontal.

Las posiciones horizontales de la pelota después de las colisiones deben calcularse teniendo en cuenta las dimensiones de la ventana, las palas, sus márgenes y el radio de la pelota.

## 7. Marcador

El juego tendrá dos marcadores:

- Marcador de la CPU, situado en la parte superior de la mitad izquierda.
- Marcador del jugador, situado en la parte superior de la mitad derecha.

Ambos marcadores tendrán las siguientes características:

- Color: `WHITE`.
- Tamaño de fuente: `100` píxeles.
- Distancia respecto al borde superior: `20` píxeles.

El texto de cada marcador debe estar centrado horizontalmente en su respectiva mitad de la pantalla.

Para ello, utiliza `MeasureText` para conocer la anchura del texto y calcular su posición X.

La posición horizontal de cada marcador se calculará tomando como referencia el centro de su mitad correspondiente:

- CPU: centro horizontal en `SCREEN_WIDTH / 4`.
- Jugador: centro horizontal en `SCREEN_WIDTH * 3 / 4`.

El valor de cada marcador debe mostrarse como un número entero.

## 8. Sistema de puntuación

Cuando la pelota salga por el lateral izquierdo de la pantalla, el jugador conseguirá un punto.

Cuando la pelota salga por el lateral derecho, la CPU conseguirá un punto.

Para detectar estas situaciones, debes tener en cuenta el radio de la pelota:

- Si `ballX - BALL_RADIUS` es menor que `0`, aumenta el marcador del jugador.
- Si `ballX + BALL_RADIUS` supera `SCREEN_WIDTH`, aumenta el marcador de la CPU.

### Reinicio de la pelota

Después de que la pelota salga por cualquiera de los laterales:

1. Debe regresar al centro de la pantalla.
2. Su posición horizontal y vertical deben recuperar sus valores iniciales.
3. La velocidad horizontal debe cambiar aleatoriamente de signo o conservarlo.
4. La velocidad vertical debe cambiar aleatoriamente de signo o conservarlo.

Para decidir aleatoriamente si cada componente de velocidad cambia de signo, puedes utilizar `Math.random()`.

Cada componente debe tener un 50 % de posibilidades de cambiar de sentido.

## 9. Mensaje inicial

Mientras el juego no haya comenzado, debe mostrarse el siguiente mensaje:

`Pulsa ENTER para empezar!`

Características:

- Color: `RAYWHITE`.
- Tamaño de fuente: `60` píxeles.
- Centrado horizontalmente en la ventana.
- Posición vertical: `(SCREEN_HEIGHT - 60) * 4 / 5`.

Para centrar el mensaje horizontalmente, utiliza `MeasureText`.

El mensaje debe desaparecer cuando el jugador pulse ENTER.

## 10. Información de FPS

En la esquina superior izquierda de la ventana debe mostrarse la información de fotogramas por segundo.

Utiliza la función:

```java
DrawFPS(20, 20);
```

## 11. Bucle principal

La aplicación debe utilizar un bucle principal que se ejecute mientras el usuario no solicite cerrar la ventana.

En cada iteración se deben realizar las siguientes operaciones:

1. Leer las entradas del teclado.
2. Actualizar la posición de la pala del jugador.
3. Actualizar la posición de la pelota, si el juego ha comenzado.
4. Actualizar la posición de la pala de la CPU.
5. Comprobar los rebotes de la pelota.
6. Comprobar si la pelota ha salido por alguno de los laterales y actualizar el marcador.
7. Comprobar las colisiones con las palas.
8. Dibujar todos los elementos del juego.

Para dibujar cada fotograma, utiliza las funciones `BeginDrawing()` y `EndDrawing()`.

Antes de dibujar los elementos, limpia la pantalla con `ClearBackground()`.

Al finalizar la aplicación, libera los recursos de la ventana utilizando `CloseWindow()`.

## 12. Funciones de Raylib que puedes necesitar

| Función | Utilidad |
|---|---|
| `InitWindow` | Crear la ventana. |
| `SetTargetFPS` | Establecer la frecuencia de actualización. |
| `GetMonitorRefreshRate` | Consultar la frecuencia de refresco de un monitor. |
| `WindowShouldClose` | Comprobar si se ha solicitado cerrar la ventana. |
| `IsKeyDown` | Comprobar si una tecla está pulsada. |
| `GetFrameTime` | Obtener el tiempo transcurrido desde el último fotograma. |
| `CheckCollisionCircleRec` | Comprobar la colisión entre un círculo y un rectángulo. |
| `ClearBackground` | Limpiar el fondo de la ventana. |
| `DrawRectangle` | Dibujar un rectángulo. |
| `DrawRectangleRounded` | Dibujar un rectángulo con esquinas redondeadas. |
| `DrawCircle` | Dibujar un círculo. |
| `DrawLine` | Dibujar una línea. |
| `MeasureText` | Obtener la anchura de un texto. |
| `DrawText` | Dibujar un texto. |
| `DrawFPS` | Mostrar los fotogramas por segundo. |
| `BeginDrawing` | Comenzar el dibujo de un fotograma. |
| `EndDrawing` | Finalizar el dibujo de un fotograma. |
| `CloseWindow` | Cerrar la ventana y liberar sus recursos. |

## 13. Consideraciones finales

- Organiza el código utilizando constantes para las dimensiones, velocidades y colores.
- Evita repetir valores numéricos que puedan calcularse a partir de las dimensiones de la ventana.
- Utiliza `GetFrameTime()` para que el movimiento sea independiente de la frecuencia de actualización.
- Separa claramente la lógica de actualización del juego de la lógica de dibujo.
- Comprueba que las colisiones y los rebotes funcionan correctamente.