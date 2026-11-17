# Ejercicio: La bola que cae

Desarrolla una aplicación utilizando **Raylib** que simule el movimiento de una bola sometida a la gravedad.

La aplicación mostrará una ventana de **1920 × 1080 píxeles** (relación de aspecto 16:9), con fondo azul oscuro, en la que aparecerá una bola de color naranja de 30 píxeles de radio, inicialmente situada en el centro de la ventana.

## 1. Funcionamiento

La simulación debe cumplir los siguientes requisitos:

### Inicio de la simulación

Inicialmente, la bola permanecerá inmóvil. Al pulsar la barra espaciadora comenzará a moverse, sometida a la aceleración de la gravedad.

### Movimiento

La bola debe desplazarse siguiendo una trayectoria parabólica.

Debes utilizar variables para representar:
- Su posición horizontal y vertical.
- Su velocidad horizontal y vertical.
- La aceleración de la gravedad.

### Interacción con el ratón

Mientras se mantenga pulsado el botón izquierdo del ratón, la bola deberá modificar su movimiento para dirigirse hacia la posición actual del cursor.

Debes contemplar dos situaciones:

- **El cursor se encuentra por encima de la bola:** la bola deberá iniciar un movimiento ascendente y alcanzar la altura indicada por el cursor justo en el punto más alto de su trayectoria.
- **El cursor se encuentra a la misma altura o por debajo de la bola:** la bola deberá continuar su movimiento vertical bajo la acción de la gravedad hasta alcanzar la altura indicada por el cursor.

En ambos casos, la bola deberá desplazarse horizontalmente para alcanzar también la coordenada X del cursor en el mismo instante en que alcance la altura indicada.

### Rebotes

Cuando la bola alcance cualquiera de los límites laterales o el límite inferior de la ventana, deberá rebotar invirtiendo el sentido de su velocidad en el eje correspondiente.

Ten en cuenta que debes considerar el radio de la bola para detectar las colisiones con los límites de la ventana.

### Actualización de la simulación

El movimiento debe ser independiente de la tasa de fotogramas, utilizando el tiempo transcurrido entre frames para actualizar la posición y la velocidad de la bola.

## 2. Ayuda para resolver el movimiento

Para implementar el movimiento de la bola puedes utilizar las siguientes ecuaciones de la física.

### 2.1. Movimiento vertical

La bola está sometida a una aceleración constante (la gravedad), por lo que su posición vertical evoluciona según la siguiente ecuación:

$$
Y_f = Y_0 + V_0 \cdot t + \frac{1}{2} \cdot g \cdot t^2
$$

Donde:

- \(Y_0\): posición vertical inicial.
- \(Y_f\): posición vertical final.
- \(V_0\): velocidad vertical inicial.
- \(g\): aceleración de la gravedad.
- \(t\): tiempo transcurrido.

La velocidad vertical en cualquier instante se calcula mediante:

$$
V_f = V_0 + g \cdot t
$$

Recuerda que en Raylib el eje Y crece hacia abajo. Por tanto:
- Las velocidades negativas representan movimientos ascendentes.
- Las velocidades positivas representan movimientos descendentes.

### 2.2. Movimiento horizontal

En el eje horizontal no aplicaremos ninguna aceleración. Por tanto, la velocidad horizontal permanecerá constante y la posición se calculará mediante:

$$
X_f = X_0 + V_x \cdot t
$$

Donde:

- $X_0$: posición horizontal inicial.
- $X_f$: posición horizontal final.
- $V_x$: velocidad horizontal.
- $t$: tiempo transcurrido.

### 2.3. Dirigir la bola hacia el cursor

Cuando el usuario mantenga pulsado el botón izquierdo del ratón, deberás calcular las velocidades necesarias para que la bola alcance la posición del cursor.

Para ello, diferencia dos situaciones.

#### Caso A: el cursor está por encima de la bola

La bola debe alcanzar la altura del cursor justo en el punto más alto de su trayectoria.

Recuerda que en el punto más alto la velocidad vertical es cero:

$$
V_f = 0
$$

Puedes utilizar esta condición junto con las ecuaciones anteriores para calcular:

1. El tiempo que tardará en alcanzar esa altura.
2. La velocidad vertical inicial necesaria.
3. La velocidad horizontal necesaria para llegar a la coordenada X del cursor durante ese mismo tiempo.

**Pista:** puedes calcular el tiempo necesario utilizando la siguiente expresión:

$$
t = \sqrt{\frac{2 \cdot |Y_f-Y_0|}{g}}
$$

Una vez obtenido el tiempo, puedes calcular las velocidades necesarias para alcanzar el punto deseado.

#### Caso B: el cursor está a la misma altura o por debajo de la bola

La bola continuará sometida a la gravedad hasta alcanzar la altura del cursor.

Para calcular el tiempo necesario, tendrás que resolver una ecuación de segundo grado:

$$
A \cdot t^2 + B \cdot t + C = 0
$$

Sus soluciones se obtienen mediante la fórmula:

$$
t = \frac{-B \pm \sqrt{B^2 - 4AC}}{2A}
$$

En este ejercicio puedes utilizar los siguientes valores:

$$
A = \frac{g}{2}
$$

$$
B = V_0
$$

$$
C = -(Y_f-Y_0)
$$

De las dos soluciones, deberás utilizar la que proporcione un tiempo positivo y corresponda al siguiente instante en el que la bola alcance la altura deseada.

Una vez calculado el tiempo, podrás obtener la velocidad horizontal necesaria para alcanzar la coordenada X del cursor:

$$
V_x = \frac{X_f-X_0}{t}
$$

### 2.4. Actualización del movimiento

Para actualizar la posición y la velocidad en cada fotograma, utiliza el tiempo transcurrido desde el fotograma anterior mediante `GetFrameTime()`.

Recuerda que debes trabajar con segundos y no con fotogramas por segundo.

Por ejemplo, para actualizar la posición y la velocidad vertical puedes utilizar:

$$
Y = Y + V_y \cdot \Delta t
$$

$$
V_y = V_y + g \cdot \Delta t
$$

Donde $\Delta t$ representa el tiempo transcurrido desde el último fotograma.

## 3. Requisitos técnicos

- Utiliza las funciones de Raylib para crear la ventana, detectar las entradas del teclado y del ratón y dibujar los elementos.
- Utiliza `GetFrameTime()` para obtener el tiempo transcurrido entre fotogramas.
- Utiliza `Math.sqrt()` y las operaciones matemáticas necesarias para calcular el movimiento de la bola.
- Muestra los FPS en la esquina superior izquierda de la ventana mediante `DrawFPS()`.
- Utiliza constantes para definir las dimensiones de la ventana, el radio de la bola y la aceleración de la gravedad.

## 4. Objetivos del ejercicio

Con este ejercicio practicarás:

- La utilización de variables y constantes.
- La actualización de posiciones mediante velocidades y aceleraciones.
- La simulación de movimientos mediante ecuaciones físicas.
- La resolución de cálculos matemáticos aplicados a un problema de programación.
- La detección de colisiones y la implementación de rebotes.
- La interacción con el usuario mediante el teclado y el ratón.
- La utilización de Raylib para desarrollar una aplicación gráfica interactiva.