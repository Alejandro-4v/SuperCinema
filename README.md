# SuperCinema

Este proyecto simula el funcionamiento de una taquilla de cine, gestionando la venta de entradas y las colas de clientes mediante hilos en Java. El proyecto se divide en dos versiones (v1 y v2), cada una con diferentes características y niveles de complejidad.

## Versión 1 (v1)

La primera versión implementa una simulación básica de un cine antiguo.

### Características Principales

*   **Taquillas:** Se simulan múltiples taquillas (por defecto 2) que atienden a los clientes.
*   **Cola Infinita:** Existe una única cola compartida por todas las taquillas donde los clientes esperan su turno. Esta cola no tiene límite de capacidad.
*   **Generación de Clientes:** Los clientes (cinéfilos) llegan en grupos de 10 a 15 cada minuto.
*   **Venta de Entradas:** Cada taquilla tarda entre 20 y 30 segundos en vender una entrada.
*   **Apertura Anticipada:** Las taquillas abren 30 minutos antes del inicio de la película.
*   **Capacidad:** El cine tiene una capacidad limitada (por defecto 200 asientos).
*   **Cierre:** La simulación termina cuando se venden todas las entradas o cuando comienza la película.

### Estructura del Código (v1)

*   `Cinema.java`: Clase principal que inicia la simulación, crea las taquillas y el generador de clientes, y controla el cierre del cine.
*   `BoxOffice.java`: Representa una taquilla. Se ejecuta en su propio hilo, atendiendo clientes de la cola infinita.
*   `InfiniteQueue.java`: Implementa una cola bloqueante (`LinkedBlockingQueue`) sin límite de capacidad para almacenar a los clientes.
*   `CinephileGenerator.java`: Genera clientes aleatoriamente y los añade a la cola infinita.
*   `Cinephile.java`: Representa a un cliente.
*   `Finals.java`: Contiene las constantes de configuración de la simulación (tiempos, capacidades, etc.).
*   `Maths.java`: Utilidades matemáticas para generar números aleatorios.

## Versión 2 (v2)

La segunda versión añade complejidad a la simulación introduciendo restricciones de espacio y múltiples colas.

### Cambios respecto a la v1

*   **Colas Finitas:** El espacio para hacer cola es limitado. Ahora se utilizan colas con capacidad máxima (por defecto 10 personas).
*   **Múltiples Colas:** Se pueden configurar múltiples colas por taquilla (por ejemplo, 4 colas para 2 taquillas).
*   **Asignación de Clientes:** Cuando un cliente llega, elige la cola con más espacio disponible. Si todas las colas están llenas, el cliente se marcha sin entrar.
*   **Selección de Cola por Taquilla:** La taquilla decide de qué cola atender, priorizando aquella con más clientes esperando.
*   **Configuración Flexible:** Se permite configurar el número de taquillas, colas por taquilla, capacidad de las colas, tiempos de venta, etc.

### Estructura del Código (v2)

*   `Cinema.java`: Clase principal adaptada para gestionar múltiples colas finitas y su asignación a las taquillas.
*   `BoxOffice.java`: Modificada para manejar un array de `FiniteQueue`. Selecciona el siguiente cliente de la cola más llena.
*   `FiniteQueue.java`: Implementa una cola bloqueante con capacidad limitada (`ArrayBlockingQueue`).
*   `CinephileGenerator.java`: Ahora intenta añadir clientes a la cola con más espacio libre. Si no hay espacio, el cliente se descarta.
*   `Finals.java`: Actualizado con nuevos parámetros de configuración para las colas finitas y validaciones de los valores.

## Ejecución

Para ejecutar la simulación, corre el método `main` en la clase `Cinema` de la versión deseada (`v1.Cinema` o `v2.Cinema`). Asegúrate de revisar y ajustar los parámetros en `Finals.java` si deseas cambiar el comportamiento de la simulación.
