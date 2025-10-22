# **Sistema Gestor de Tickets**

*1. Decisiones de Diseño* 

Luego de un análisis respecto al problema planteado, el diseño del proyecto se centró en la separación de responsabilidades (clases principales Comments, Person, Ticket y Enum`s para Status y Type) y en la eficiencia de las estructuras de datos. Una decisión clave fue el desarrollo de ChangesStack (Historial), que utiliza una estructura Pila/Cola limitada para actuar como Pila (LIFO) para las operaciones de deshacer/rehacer y simular una Cola (FIFO) para eliminar el elemento más antiguo al alcanzar el límite de historial. Para la base de estas estructuras (Node y DoubleNode), se utilizó Programación Genérica(<T>) para evitar la redundancia de código y garantizar la seguridad de tipos. 


*2. Catálogo de Estados*

El Catálogo de Estados del ticket, definido en el Enum Status, permite que los tickets transiten de forma legible por los estados: EN_COLA (esperando) EN_ATENCION (asignado) EN_PROCESO (trabajando) PENDIENTE_DOCS (pausa) COMPLETADO (solucionado).


*3. Casos Bordes*

Finalmente, el sistema maneja los Casos Borde validando rigurosamente la entrada por consola, restringiendo el uso de valores null en atributos críticos y previniendo fallos al manejar explícitamente las condiciones de pilas y colas vacías en las estructuras de datos del negocio.


*4. Guía de ejecución*

Al momento de ejecutar el programa se mostrará en consola el menú de las acciones que se pueden realizar.

<img width="889" height="224" alt="{A26B1737-D037-4989-B1E8-BA5128168F4C}" src="https://github.com/user-attachments/assets/38e3ee56-d24b-451a-a83c-80fb7712be6b" />


 El usuario debe ingresar el numero de la acción que desea realizar. 
 
 La opción 0 es para generar de manera automática la cantidad de tickets del usuario
 

 <img width="860" height="296" alt="{70AEA02B-5FB8-4C95-B211-0778C8C72835}" src="https://github.com/user-attachments/assets/ce20c07b-8456-49ad-a26a-571e45ff6407" />

 
 

En la opción de atención de tickets, se abre un submenú 1 que muestra las acciones para realizar la atención de tickets, las cuales son:


<img width="943" height="354" alt="{D132472B-9E1B-449B-A9C7-F14CFBB108F8}" src="https://github.com/user-attachments/assets/0d498abf-b210-4bae-a715-e26df774811a" />



En caso de que el usuario, seleccione la opción de cambiar estados, se muestra un submenú 2

<img width="889" height="347" alt="image" src="https://github.com/user-attachments/assets/2efa9341-4588-40bb-87f4-231499beb277" />



4.5 La opción 2 muestra los tickets que se encuentran disponibles 

<img width="896" height="353" alt="image" src="https://github.com/user-attachments/assets/20c5213f-aded-4546-b6c9-2c167f8a77cf" />


4.6 La opción 3 genera los tickets de manera manual, pidiendo como parametros: nombre, apellido, numero de carnte identificativo, número telefónico y el tipo de trasnsacción

<img width="882" height="461" alt="image" src="https://github.com/user-attachments/assets/fcdad13f-5459-435c-a969-eadcf6be11a4" />


4.7 La opción 4 imprime el historial de los tickets Atendidos Junto a su ultimo estado.

<img width="1385" height="345" alt="image" src="https://github.com/user-attachments/assets/73b37f66-b29d-41c5-8684-7cdadbf37bf9" />


4.8 Finalmente, con la opción numero 5 se cierra el programa. 

<img width="721" height="203" alt="image" src="https://github.com/user-attachments/assets/cc46473e-b33e-4685-bd23-5c37743156f4" />














