# **Sistema de Gestión de Tickets**

Este es un sistema de gestión de tickets de soporte desarrollado en Java, enfocado en la atención y seguimiento de trámites. Es una aplicación de consola que permite crear, atender, y gestionar el ciclo de vida de los tickets, con persistencia de datos y funcionalidad de Deshacer/Rehacer.

# ****Características Principales****

  Gestión de Colas: Utiliza una cola priorizada para atender primero los tickets marcados como "prioridad".

  Gestión de Estados: Los tickets transitan por diferentes estados (En Cola, En Atención, Pendiente Documentos, Completado).

  Sistema Undo/Redo: Permite deshacer y rehacer cambios de estado o comentarios en el ticket que se está atendiendo.

  Gestión de Comentarios: Se pueden añadir, modificar o eliminar comentarios en cada ticket.

# **Persistencia de Datos:**

  Guarda los tickets aún en cola en un archivo .csv al salir, para importarlos en la próxima sesión.

  Almacena un historial permanente de tickets "Completados" en un archivo .json.

  Mantiene un contador del último ID de ticket en un .txt para asegurar IDs únicos entre sesiones.

# **Requisitos Previos**

 - Java JDK 23 o superior.

 - Apache Maven (para gestionar las dependencias).

# **Instalación y Dependencias**

Este proyecto utiliza dependencias externas (como json-simple) para la gestión de archivos JSON.

Clona o descarga este repositorio en tu máquina local.

Revisa si las dependencias requeridas están instaladas.

En caso de no estar instaladas, abre una terminal en la carpeta raíz del proyecto y ejecuta la siguiente línea:

Bash:

mvn install

Esto leerá el archivo pom.xml y descargará los .jar necesarios para el proyecto (como la librería JSON) en tu repositorio local de Maven.

# **Estructura de Archivos de Datos**

Para que la persistencia de datos funcione correctamente, el programa espera encontrar (o creará) una carpeta específica para almacenar los archivos de historial.

Asegúrate de que tu proyecto contenga la siguiente ruta: src/main/java/history.

Dentro de esta carpeta se gestionarán los siguientes archivos:

tickets_pendientes.csv: Almacena los tickets que quedaron "En Cola" o "Pendiente Docs" al cerrar la aplicación. Se leen al iniciar.

tickets_finalizados.json: Guarda un historial permanente de todos los tickets que han sido marcados como "Completados".

last_id.txt: Un simple archivo de texto que guarda el último ID numérico utilizado, para que los nuevos tickets continúen la secuencia.

# **Cómo Usar (Guía Rápida)**

Una vez instaladas las dependencias, compila y ejecuta la clase principal: RunApp.java.

Se desplegará el Menú Principal en la consola.

# **Flujo Básico de Uso**

Crear Tickets: Usa la Opción 2 ("Crear Nuevo Ticket") para añadir nuevos tickets a la cola. El sistema te pedirá los datos del cliente, el tipo de trámite y si es prioritario.

Atender Tickets: Selecciona la Opción 1 ("Atender Siguiente Ticket"). El sistema tomará el ticket correspondiente de la cola (dando prioridad a los prioritarios) y te llevará al Menú de Ticket.

Nota: No podrás atender un nuevo ticket hasta que finalices (marques como "Completado" o "Pendiente Docs") el que estás atendiendo.

Gestionar un Ticket: Dentro del Menú de Ticket puedes:

Añadir o modificar comentarios.

Cambiar el estado (ej. "En Proceso", "Pendiente Documentos").

Usar "Deshacer" o "Rehacer" si cometiste un error al cambiar el estado.

Finalizar un Ticket: Para poder tomar un nuevo ticket, debes marcar el actual como:

"Completado": El ticket se guarda en el historial .json y se libera el sistema.

"Pendiente Documentos": El ticket se devuelve a la cola (respetando su prioridad) y se libera el sistema.

Generar un Reporte Top-K: Devuelve la lista de tickets ordenados por id o por cantidad de comentarios.

Salir del Programa: Utiliza siempre la Opción 5 ("Guardar y Salir") del Menú Principal. Esto asegura que todos los tickets que quedaron pendientes en la cola se guarden en tickets_pendientes.csv para la próxima vez que ejecutes el programa.






