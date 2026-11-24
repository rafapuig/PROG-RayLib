# Práctica: Representación gráfica de mapas con Jaylib

## Objetivos

En esta práctica vas a desarrollar un programa que permita representar gráficamente un mapa almacenado en una matriz bidimensional utilizando la biblioteca **Jaylib**.

El objetivo es aprender a transformar los datos de una matriz en una representación gráfica formada por casillas rectangulares, de manera que cada posición de la matriz se corresponda con una casilla de la ventana.

Esta práctica servirá también como punto de partida para representar gráficamente algoritmos que trabajan sobre mapas, como los algoritmos de búsqueda de caminos.

## 1. El mapa

Un mapa estará representado mediante una matriz bidimensional de números enteros. Cada posición de la matriz representa una casilla del mapa.

Utilizaremos los siguientes valores:

| Valor | Significado |
|---|---|
| `0` | Casilla libre |
| `1` | Pared u obstáculo |
| `2` | Posición inicial |
| `3` | Posición de destino |

Por ejemplo, el siguiente mapa:

```java
int[][] map = {
    {2, 0, 0, 1, 0, 0, 0},
    {1, 1, 0, 1, 0, 1, 0},
    {0, 0, 0, 0, 0, 1, 0},
    {0, 1, 1, 1, 0, 0, 0},
    {0, 0, 0, 1, 1, 1, 0},
    {1, 1, 0, 0, 0, 0, 3}
};
```

representa un mapa de 6 filas y 7 columnas.

## 2. Representación gráfica

El programa debe mostrar el mapa en una ventana de Jaylib.

Cada casilla de la matriz se representará mediante un rectángulo. Todas las casillas tendrán el mismo tamaño.

Se utilizará un color diferente para cada tipo de casilla:

- Casillas libres: blanco o gris claro.
- Paredes: gris oscuro o negro.
- Posición inicial: verde.
- Posición de destino: rojo.

Las casillas deberán estar separadas visualmente por un pequeño borde o línea para que se pueda distinguir claramente la cuadrícula.

### Conversión de coordenadas

Para dibujar una casilla de la matriz mediante `DrawRectangle()`, tendrás que transformar sus índices en coordenadas de pantalla.

Si el tamaño de cada casilla es `tileSize`, la posición de la esquina superior izquierda de una casilla será:

- Coordenada horizontal: `column * tileSize`.
- Coordenada vertical: `row * tileSize`.

Por tanto, la posición de una casilla dependerá de su fila y columna dentro de la matriz.

## 3. Requisitos del programa

El programa deberá cumplir los siguientes requisitos:

1. Declarar una matriz bidimensional que represente el mapa.
2. Crear una ventana de Jaylib con un tamaño adecuado para mostrarlo.
3. Calcular el tamaño de las casillas teniendo en cuenta las dimensiones de la matriz.
4. Recorrer la matriz para dibujar todas sus casillas.
5. Representar cada tipo de casilla con el color correspondiente.
6. Dibujar una separación visual entre las casillas.
7. Mantener la ventana abierta hasta que el usuario la cierre.

El mapa deberá quedar centrado en la ventana, tanto horizontal como verticalmente, siempre que sus dimensiones lo permitan.

## 4. Ampliación 1: Mostrar las coordenadas

Añade a cada casilla un texto que indique su posición dentro de la matriz.

Por ejemplo, la casilla situada en la fila 2 y columna 4 deberá mostrar:

```text
(2,4)
```

El texto deberá quedar centrado dentro de la casilla y tener un tamaño que permita leerlo sin ocupar demasiado espacio.

Puedes utilizar `DrawText()` para representar las coordenadas.

## 5. Ampliación 2: Cambiar el tamaño de las casillas

Añade una variable `tileSize` que determine el tamaño de las casillas.

El programa deberá permitir modificar este valor y actualizar la representación gráfica del mapa.

Comprueba qué sucede cuando:

- Las casillas son demasiado pequeñas.
- Las casillas son demasiado grandes.
- El mapa no cabe completamente en la ventana.

## 6. Ampliación 3: Modificar el mapa con el teclado

Permite que el usuario pueda modificar el contenido del mapa utilizando el teclado.

El programa deberá permitir:

- Cambiar la casilla seleccionada.
- Convertir una casilla libre en una pared.
- Convertir una pared en una casilla libre.

La casilla seleccionada deberá destacarse visualmente mediante un borde de otro color.

Para ello, tendrás que mantener dos variables que indiquen la fila y la columna de la casilla seleccionada.

Utiliza las funciones de Jaylib para detectar las pulsaciones del teclado.

**Importante:** no se podrá modificar la posición inicial ni la posición de destino.

## 7. Ampliación 4: Generar mapas aleatorios

Modifica el programa para que pueda generar mapas aleatorios.

El mapa deberá contener:

- Una posición inicial.
- Una posición de destino.
- Un conjunto aleatorio de paredes.
- El resto de casillas libres.

La posición inicial y la de destino deberán estar en posiciones diferentes y no podrán coincidir con una pared.

Puedes utilizar una probabilidad para decidir si cada casilla será una pared o estará libre.

Añade una tecla que permita generar un nuevo mapa aleatorio.

## 8. Reflexión final

Responde a las siguientes preguntas:

1. ¿Por qué resulta útil utilizar una matriz bidimensional para representar un mapa?
2. ¿Qué relación existe entre los índices de la matriz y las coordenadas de pantalla?
3. Si el mapa tiene 10 filas y 15 columnas, ¿cuántas casillas habrá que dibujar?
4. ¿Qué habría que modificar para que las casillas fueran rectangulares en lugar de cuadradas?
5. ¿Por qué es conveniente separar los datos del mapa del código encargado de dibujarlo?

## Entrega

Se entregará el proyecto Java completo.

El programa deberá incluir, como mínimo, los apartados 1, 2 y 3. Las ampliaciones se realizarán progresivamente y permitirán mejorar la calificación de la práctica.

**Consejo:** no intentes dibujar cada casilla de forma independiente. Utiliza dos bucles anidados para recorrer la matriz y representar cada posición. De esta manera, el código de dibujo será prácticamente el mismo para cualquier mapa, independientemente de sus dimensiones.