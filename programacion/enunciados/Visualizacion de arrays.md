# Práctica: Visualización gráfica de arrays y algoritmos de ordenación con Jaylib

## Objetivos

En esta práctica vas a desarrollar un programa que permita representar gráficamente un array unidimensional utilizando la biblioteca **Jaylib**.

Cada elemento del array se representará mediante una barra vertical cuya altura dependerá del valor almacenado en esa posición.

Una vez implementada la representación gráfica, utilizarás el programa para visualizar el funcionamiento de diferentes algoritmos de ordenación, mostrando cómo se comparan e intercambian los elementos hasta conseguir ordenar el array.

Con esta práctica aprenderás a:

- Representar gráficamente estructuras de datos.
- Transformar índices y valores de un array en coordenadas y dimensiones de pantalla.
- Utilizar colores para destacar elementos.
- Animar un algoritmo mostrando su evolución.
- Separar los datos, el algoritmo y su representación gráfica.

---

## 1. Representación gráfica de un array

Declara un array de números enteros que contenga diferentes valores.

Por ejemplo:

```java
int[] numbers = {5, 2, 8, 3, 1, 7, 4, 6, 9, 2};
```

El programa deberá representar cada elemento del array mediante un rectángulo vertical.

La posición horizontal de cada rectángulo dependerá del índice del elemento, mientras que su altura dependerá del valor almacenado.

Por ejemplo, si el valor de una posición es 8, su barra deberá ser más alta que la correspondiente a una posición cuyo valor sea 3.

### Requisitos

1. Crear una ventana de Jaylib.
2. Dibujar una barra por cada elemento del array.
3. Situar las barras consecutivamente, manteniendo una separación constante entre ellas.
4. Calcular la altura de cada barra en función del valor correspondiente.
5. Centrar horizontalmente la representación del array.
6. Situar todas las barras sobre una misma línea horizontal, que actuará como base.
7. Mantener la ventana abierta hasta que el usuario decida cerrarla.

El tamaño de las barras deberá adaptarse al tamaño de la ventana y al número de elementos del array.

### Conversión de valores en coordenadas

Para dibujar una barra mediante `DrawRectangle()`, tendrás que calcular sus coordenadas y dimensiones.

Si `i` representa el índice del elemento, `barWidth` la anchura de cada barra y `spacing` la separación entre ellas, la coordenada horizontal podrá calcularse mediante:

```java
int x = startX + i * (barWidth + spacing);
```

La altura de la barra dependerá del valor almacenado:

```java
int height = numbers[i] * scale;
```

Recuerda que las coordenadas de pantalla de Jaylib tienen su origen en la esquina superior izquierda. Por tanto, para que todas las barras compartan la misma base, tendrás que calcular su coordenada vertical a partir de la altura de la barra.

---

## 2. Generación de arrays aleatorios

Modifica el programa para que el array se genere aleatoriamente.

El programa deberá permitir establecer:

- El número de elementos del array.
- El valor mínimo que puede tener un elemento.
- El valor máximo que puede tener un elemento.

Los valores deberán generarse aleatoriamente dentro del intervalo establecido.

Añade una tecla que permita generar un nuevo array aleatorio.

Cada vez que se genere un array nuevo, deberá actualizarse automáticamente su representación gráfica.

---

## 3. Visualización de las comparaciones

Vamos a preparar el programa para representar gráficamente el funcionamiento de los algoritmos de ordenación.

Para ello, tendrás que utilizar diferentes colores para identificar los elementos que intervienen en cada operación.

Utiliza los siguientes colores:

| Color | Significado |
|---|---|
| Gris | Elemento que no interviene en la operación actual |
| Amarillo | Elemento que está siendo comparado |
| Rojo | Elemento que está siendo intercambiado |
| Verde | Elemento que ya está en su posición definitiva |

Modifica el código de dibujo para que pueda representar cada barra con un color diferente.

Para ello, tendrás que utilizar un array auxiliar que indique el estado visual de cada elemento o utilizar las variables que necesites para identificar las posiciones que deben destacarse.

Comprueba que puedes cambiar los colores de las barras sin modificar los valores almacenados en el array.

---

## 4. Visualización del algoritmo Bubble Sort

Implementa el algoritmo de ordenación **Bubble Sort**.

El programa deberá mostrar gráficamente cómo se ordena el array.

Durante la ejecución:

1. Los dos elementos que se están comparando deberán aparecer en amarillo.
2. Si es necesario intercambiar sus posiciones, deberán aparecer en rojo durante el intercambio.
3. Una vez finalizada cada comparación, las barras deberán recuperar su color habitual.
4. Los elementos que ya hayan alcanzado su posición definitiva podrán mostrarse en verde.

### Animación

No queremos que el algoritmo se ejecute completamente en un único instante.

Queremos poder observar cómo evoluciona el array.

Para conseguirlo, tendrás que separar la ejecución del algoritmo en pequeñas operaciones.

En cada iteración del bucle principal de Jaylib se deberá realizar una operación del algoritmo y actualizar la representación gráfica.

Puedes utilizar una variable para controlar la velocidad de la animación.

Por ejemplo, puedes establecer un intervalo de tiempo entre operaciones para que los cambios sean visibles.

### Controles

El programa deberá permitir:

- Iniciar la ordenación pulsando una tecla.
- Pausar y reanudar la animación.
- Reiniciar el array con nuevos valores aleatorios.
- Modificar la velocidad de la animación.

---

## 5. Visualización del algoritmo Selection Sort

Implementa también el algoritmo **Selection Sort**.

En este caso, deberás representar gráficamente cómo se busca el elemento mínimo de la parte del array que todavía no está ordenada.

Durante la ejecución:

- El elemento que se está examinando deberá aparecer en amarillo.
- El elemento mínimo encontrado hasta el momento deberá aparecer en rojo.
- Cuando termine la búsqueda del mínimo, se deberá destacar el intercambio con la primera posición de la zona desordenada.
- Los elementos que ya estén ordenados deberán aparecer en verde.

La animación deberá permitir observar cómo va creciendo la zona ordenada del array.

---

## 6. Información adicional

Añade información textual a la ventana para mostrar el estado del algoritmo.

Como mínimo, se deberá mostrar:

- El nombre del algoritmo que se está ejecutando.
- El número de comparaciones realizadas.
- El número de intercambios realizados.
- El estado actual: preparado, ejecutándose, pausado o finalizado.

Utiliza `DrawText()` para mostrar esta información.

---

## 7. Ampliaciones

Una vez completados los apartados anteriores, puedes realizar las siguientes ampliaciones.

### 7.1. Visualización de Insertion Sort

Implementa el algoritmo **Insertion Sort** y muestra gráficamente cómo se desplazan los elementos para insertar cada valor en su posición correspondiente.

Deberás distinguir visualmente:

- El elemento que se está insertando.
- Los elementos que se están desplazando.
- La zona del array que ya está ordenada.

### 7.2. Comparación de algoritmos

Permite ejecutar dos algoritmos diferentes sobre el mismo array inicial.

Muestra sus representaciones gráficas simultáneamente, una al lado de la otra.

Añade información sobre el número de comparaciones e intercambios realizados por cada algoritmo.

De esta manera podrás observar cómo se comportan los diferentes algoritmos cuando trabajan con los mismos datos.

### 7.3. Ordenación en sentido inverso

Permite elegir entre ordenar el array de menor a mayor o de mayor a menor.

### 7.4. Diferentes distribuciones iniciales

Permite generar arrays con diferentes distribuciones:

- Valores completamente aleatorios.
- Valores ya ordenados.
- Valores ordenados en sentido inverso.
- Valores casi ordenados.

Comprueba cómo influye la distribución inicial de los datos en el comportamiento de cada algoritmo.

---

## 8. Reflexión final

Responde a las siguientes preguntas:

1. ¿Qué relación existe entre el índice de un elemento y la posición horizontal de su barra?
2. ¿Por qué es necesario calcular la coordenada vertical de cada barra a partir de su altura?
3. ¿Qué ventajas tiene utilizar colores para representar el estado de los elementos?
4. ¿Por qué no podemos ejecutar todo el algoritmo de ordenación de una sola vez si queremos visualizarlo paso a paso?
5. ¿Qué diferencia existe entre el número de comparaciones y el número de intercambios?
6. ¿Por qué Bubble Sort puede necesitar muchos más intercambios que Selection Sort?
7. ¿Qué diferencias observas entre los algoritmos cuando el array inicial ya está ordenado?
8. ¿Por qué resulta conveniente separar el código del algoritmo del código encargado de dibujar el array?

---

## Entrega

Se entregará el proyecto Java completo.

Los apartados 1, 2, 3 y 4 son obligatorios.

Los apartados restantes se realizarán como ampliaciones.

El programa deberá permitir generar arrays aleatorios y visualizar gráficamente su ordenación, mostrando en todo momento el estado de los elementos y las operaciones que realiza el algoritmo.

**Importante:** el algoritmo de ordenación no debe encargarse de dibujar las barras. Su responsabilidad será modificar el array y actualizar la información necesaria para la representación gráfica. El código de Jaylib será el encargado de mostrar el estado actual.

Esta separación permitirá reutilizar el mismo sistema de representación para visualizar otros algoritmos y estructuras de datos.