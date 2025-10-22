Sistema Gestor de Tickets 
1.Desiciones de Diseño
2.Catálogo de Estados
3.Casos Bordes 
4. Guía de Ejecución
1. Decisiones de Diseño
Luego de un análisis respecto al problema planteado, el diseño del proyecto se centró en la separación de responsabilidades (clases principales Comments, Person, Ticket y Enum`s para Status y Type) y en la eficiencia de las estructuras de datos. Una decisión clave fue el desarrollo de ChangesStack (Historial), que utiliza una estructura Pila/Cola limitada para actuar como Pila (LIFO) para las operaciones de deshacer/rehacer y simular una Cola (FIFO) para eliminar el elemento más antiguo al alcanzar el límite de historial. Para la base de estas estructuras (Node y DoubleNode), se utilizó Programación Genérica(<T>) para evitar la redundancia de código y garantizar la seguridad de tipos. 
2. Catálogo de Estados
El Catálogo de Estados del ticket, definido en el Enum Status, permite que los tickets transiten de forma legible por los estados: EN_COLA (esperando) EN_ATENCION (asignado) EN_PROCESO (trabajando) PENDIENTE_DOCS (pausa) COMPLETADO (solucionado). 
3. Casos Bordes
Finalmente, el sistema maneja los Casos Borde validando rigurosamente la entrada por consola, restringiendo el uso de valores null en atributos críticos y previniendo fallos al manejar explícitamente las condiciones de pilas y colas vacías en las estructuras de datos del negocio.
4. Guía de ejecución 
4.1 Ejecutar el programa para que se muestre en consola el menú de las acciones que se pueden realizar.
0. Llenar tickets
1. Atender ticket
2. Tickets disponibles
3. Generar ticket
4. Imprimir historial
5. Salir del programa
4.2 El usuario debe ingresar el numero de la acción que desea realizar. 
4.3 La opción 0 es para generar de manera automática la cantidad de tickets del usuario
4.4 Opción 1: atención de tickets
	En la opción de atención de tickets, se abre un submenú 1 que muestra las acciones para realizar la atención de tickets, las cuales son:
  0. Mostrar Ticket
  1. Agregar comentario
2. Cambiar estado
3. Revertir cambios
4. Rehacer cambios
5. Volver
En caso de que el usuario, seleccione la opción de cambiar estados, se un submenú 2
4.5 La opción 2 muestra los tickets que se encuentran disponibles 
4.6 La opción 3 genera los tickets de manera manual 
4.7 La opción 4 imprime el historial de los tickets.
4.8 Finalmente, con la opción numero 5 se cierra el programa. 

-	Desde el submenú de 4.4: 
-	




