# Práctica: Pong

## 1. Introducción

En esta práctica vas a desarrollar una versión del clásico videojuego **Pong**, utilizando Java y la biblioteca gráfica **Raylib**, a través de su adaptación para Java, **Jaylib**.

Pong es un videojuego de tenis de mesa en el que dos jugadores controlan unas palas (paddles) situadas en los extremos de la pantalla. El objetivo es conseguir que la pelota sobrepase la pala del adversario para obtener un punto.

En esta versión jugarás contra la CPU, que controlará automáticamente su propia pala.

## 2. Preparación del proyecto

Para desarrollar esta práctica utilizarás **Gradle como sistema de construcción (build system)**.

Gradle se encargará de gestionar las dependencias necesarias para compilar y ejecutar el proyecto.

Debes crear un proyecto Java utilizando Gradle y añadir la siguiente dependencia en el archivo `build.gradle.kts`:

```kotlin
dependencies {
    implementation("uk.co.electronstudio.jaylib:jaylib:6.0.+")
}
```

Esta dependencia proporciona Jaylib, una biblioteca que permite utilizar Raylib desde Java.

Puedes consultar la documentación y los ejemplos de Raylib para conocer las funciones disponibles.

## 3. Características del juego

El juego debe desarrollarse en una ventana de **1920 × 1080 píxeles**, con una relación de aspecto 16:9.

El campo de juego estará dividido verticalmente en dos mitades:

- La mitad izquierda corresponderá a la CPU.
- La mitad derecha corresponderá al jugador.

En el centro aparecerán una línea divisoria y un círculo decorativo.

### 3.1. Elementos del juego

En pantalla aparecerán los siguientes elementos:

- **Pelota:** un círculo de color amarillo, con un radio de 22 píxeles, situado inicialmente en el centro de la pantalla.
- **Pala del jugador:** un rectángulo blanco de 25 píxeles de ancho y 150 píxeles de alto, situado cerca del borde derecho.
- **Pala de la CPU:** otro rectángulo de las mismas dimensiones, situado cerca del borde izquierdo.
- **Marcadores:** dos contadores que mostrarán la puntuación de cada participante.
- **Campo de juego:** tendrá un fondo verde oscuro, con la mitad derecha de un verde más claro y una línea divisoria blanca.

Las palas tendrán las esquinas redondeadas.

### 3.2. Inicio de la partida

Al iniciar el programa, la pelota permanecerá inmóvil en el centro de la pantalla.

Se mostrará el mensaje:

"¡Pulsa ENTER para empezar!"

Al pulsar la tecla ENTER comenzará la partida y la pelota empezará a desplazarse.

### 3.3. Control de la pala del jugador

El jugador controlará su pala mediante las siguientes teclas:

- **Flecha arriba:** desplazar la pala hacia arriba.
- **Flecha abajo:** desplazar la pala hacia abajo.

La pala deberá moverse de forma continua mientras se mantenga pulsada la tecla correspondiente.

En ningún caso podrá sobrepasar los límites superior e inferior de la ventana.

### 3.4. Movimiento de la pelota

La pelota se desplazará en línea recta siguiendo una trayectoria diagonal.

Deberás utilizar variables para representar su posición y su velocidad en los ejes horizontal y vertical.

La pelota deberá actualizar su posición en cada fotograma teniendo en cuenta el tiempo transcurrido desde el último frame.

Cuando la pelota alcance el límite superior o inferior de la ventana, deberá rebotar invirtiendo el sentido de su movimiento vertical.

### 3.5. Movimiento de la CPU

La pala de la CPU se moverá automáticamente intentando interceptar la pelota.

Su comportamiento será el siguiente:

- Si el centro de la pala está por encima de la posición vertical de la pelota, deberá desplazarse hacia abajo.
- Si el centro de la pala está por debajo de la posición vertical de la pelota, deberá desplazarse hacia arriba.

La pala de la CPU deberá moverse a la misma velocidad que la pala del jugador.

### 3.6. Colisiones con las palas

Cuando la pelota choque contra cualquiera de las palas, deberá rebotar invirtiendo el sentido de su velocidad horizontal.

Debes utilizar las funciones de Raylib para detectar la colisión entre un círculo y un rectángulo.

Después de cada colisión, la pelota deberá quedar situada justo en el exterior de la pala para evitar que permanezca dentro de ella y se produzcan múltiples colisiones consecutivas.

### 3.7. Puntuación

Cuando la pelota sobrepase el límite izquierdo de la ventana, el jugador conseguirá un punto.

Cuando la pelota sobrepase el límite derecho, la CPU conseguirá un punto.

Después de conseguir un punto:

- Se incrementará el marcador del participante correspondiente.
- La pelota volverá a su posición inicial, en el centro de la pantalla.
- La pelota cambiará aleatoriamente el sentido de su movimiento horizontal.
- También podrá cambiar aleatoriamente el sentido de su movimiento vertical.

La partida continuará después de cada punto sin necesidad de volver a pulsar ENTER.

## 4. Dibujado de los elementos

Todos los elementos deberán dibujarse utilizando las funciones de Raylib.

Para ello, puedes utilizar, entre otras, las siguientes funciones:

| Función | Utilidad |
|---|---|
| `InitWindow()` | Crear la ventana del juego. |
| `SetTargetFPS()` | Establecer la tasa de fotogramas objetivo. |
| `WindowShouldClose()` | Comprobar si se debe cerrar la ventana. |
| `IsKeyDown()` | Comprobar si una tecla está pulsada. |
| `IsKeyPressed()` | Detectar la pulsación de una tecla. |
| `GetFrameTime()` | Obtener el tiempo transcurrido desde el último fotograma. |
| `CheckCollisionCircleRec()` | Comprobar la colisión entre un círculo y un rectángulo. |
| `DrawRectangle()` | Dibujar un rectángulo. |
| `DrawRectangleRounded()` | Dibujar un rectángulo con esquinas redondeadas. |
| `DrawCircle()` | Dibujar un círculo. |
| `DrawLine()` | Dibujar una línea. |
| `MeasureText()` | Medir la anchura de un texto. |
| `DrawText()` | Dibujar un texto. |
| `DrawFPS()` | Mostrar los fotogramas por segundo. |
| `BeginDrawing()` | Comenzar el dibujado de un fotograma. |
| `EndDrawing()` | Finalizar el dibujado de un fotograma. |
| `ClearBackground()` | Limpiar el fondo de la ventana. |
| `CloseWindow()` | Cerrar la ventana. |

Puedes consultar la documentación de Jaylib para conocer los parámetros y el funcionamiento de cada función.

## 5. Requisitos de implementación

Para desarrollar el juego debes tener en cuenta los siguientes requisitos:

- Utiliza constantes para definir las dimensiones de la ventana, las dimensiones de las palas, el radio de la pelota y las velocidades de movimiento.
- Utiliza variables para almacenar las posiciones y velocidades de los elementos del juego.
- Utiliza `GetFrameTime()` para actualizar los movimientos, de forma que sean independientes de la tasa de fotogramas.
- Organiza el bucle principal del juego diferenciando las fases de entrada, actualización y dibujado.
- Utiliza las funciones de Jaylib para dibujar los elementos y detectar las colisiones.
- Muestra los FPS en la esquina superior izquierda de la ventana.

## 6. Objetivos de la práctica

Con esta práctica trabajarás los siguientes conceptos:

- Desarrollo de aplicaciones gráficas utilizando Raylib y Jaylib.
- Configuración y gestión de dependencias mediante Gradle.
- Utilización de variables y constantes.
- Actualización de posiciones mediante velocidades.
- Gestión de entradas del teclado.
- Detección y resolución de colisiones.
- Implementación de una inteligencia artificial sencilla.
- Gestión de puntuaciones y estado de una partida.
- Organización de un bucle principal de actualización y dibujado.