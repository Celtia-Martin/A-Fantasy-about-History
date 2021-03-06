# A Fantasy about History

## Integrantes

| **NOMBRE**  |  **APELLIDOS**    |             **CORREO**             |               **GITHUB**              |
| :---------: | :---------------: | :--------------------------------: | :-----------------------------------: |
| Celtia      | Martin García     | c.martinga.2017@alumnos.urjc.es    | https://github.com/Celtia-Martin      |
| Daniel      | Muñoz Rivera      | d.munozri.2017@alumnos.urjc.es     | https://github.com/MeSientoYMeCallo99 |

# INDICE

## [1.- DESCRIPCION	](#descripcion)
### [1.1.- APLICACION ](#aplicacion)
### [1.2.- ENTIDADES](#entidades)
### [1.3.- INTERFAZ SERVIDOR INTERNO](#servidorInterno)
### [1.4.- SEGURIDAD](#seguridad)
## [2.- MODELO DE DATOS](#modeloDeDatos)	
## [3.- PANTALLAS](#pantallas)
### [3.1.- CAPTURAS ](#capturasPantalla)
### [3.2.- DIAGRAMA DE NAVEGACIÓN ](#diagramaNavegacion)
### [3.3.- DIAGRAMA DE CLASES ](#diagramaClases)
## [4.- NOTAS FASE 2](#notasFase2)
## [5.- NOTAS FASE 3](#notasFase3)
## [6.- DESPLIEGUE E INSTALACIÓN](#despliegue)
## [7.- DIAGRAMA DEL DESPLIEGUE EN DOCKER](#diagrama)
## [8.- NOTAS FASE 4](#notasFase4)
## [9.- VIDEO DEMOSTRATIVO](#video)

## DESCRIPCION <a name="descripcion"/>

### APLICACION <a name="aplicacion"/>

A Fantasy about History es una aplicación web que presenta un juego de tipo Fantasy (Biwenger, Comunio, La Liga Fantasy), pero que no depende de ningun suceso real, ya que se juega con personajes históricos. Como cualquier aplicación Fantasy, los usuarios tendrán que iniciar sesión y unirse a la liga del juego.

Una vez dentro de esta liga, se nos ofrece una formación por defecto (con personajes generados automáticamente), que puede ser rellenada con personajes históricos. Con esta formación, se librará una batalla contra el resto de jugadores, consiguiendo más puntuación la que tenga mejores personajes para cada tipo de batalla. Se disputarán varias batallas (controladas por un ADMIN), y el ganador de la liga será aquel equipo que haya acumulado más puntos tras todas estas.

Para conseguir mejores personajes, la liga tendrá su propio mercado de personajes en el que los jugadores pueden pujar. El que más oro ofrezca, se lo lleva para su equipo. Se comienza con 0 oro, y por cada punto obtenido en las batallas se consigue más. El mercado se refresca a la carta (ADMIN), y ahi se decide quien gana la puja. Cada equipo tiene un máximo de 6 personajes, sin mínimo, y una vez llegada esta cantidad no se puede pujar por nuevos personajes. Por ello, es necesario vender personajes antiguos para comprar otros nuevos, y estos podrán volver a salir en el mercado.

Las batallas simuladas darán lugar a un resultado y a una serie de puntos repartidos entre todos los miembros de la liga, así como una cantidad específica de oro. Las batallas no se deciden solo por la calidad de los personajes: ofrecen una serie de información que harán que destaquen ciertos personajes sobre otros. Por ejemplo, si es una batalla cultural, los personajes culturales tendrán más peso. Así, se definen 3 tipos de personajes: militares, diplomáticos y culturales. 

Hay 2 niveles de jerarquía: el administrador de liga y el jugador. Un ADMINISTRADOR (normalmente los creadores) puede editar y ampliar las bases de datos, añadiendo personajes y batallas a las mismas. También pueden banear a jugadores de la liga. Un JUGADOR puede participar en batallas, pujar en el mercado, editar formaciones, etc.

Es estrictamente PRIVADO para los creadores la Simulación de Batallas, el reparto de puntos y oro y el cambio de personajes en el mercado.

### ENTIDADES <a name="entidades"/>

**- Usuario:** Tienen una ID, un Nombre de Usuario y Contraseña, y una cantidad de Puntos y Dinero. Cada usuario que entre en la aplicación será añadido a la liga, y tendrá una Formación asignada.También se indica si está baneado o no.

**- Personaje:** Tiene una ID, un Nombre, un Rango, un Tipo, un Precio y una serie de Características (Valor Militar, Diplomático y Cultural). Son parte de Formaciones ( si lo están es indicado por un booleano), y pueden salir en el Mercado. Hay un tipo especial: los Default (o generados), que se borran al abandonar la formación y no salen en el mercado. Se otorgan en la primera Formación.

**- Batalla:** Tienen una ID y un Tipo de Batalla. Son parte de una Liga, en la que hay varias. Se involucran una serie de Formaciones, y se reparten puntos y dinero.

**- Formaciones:** Tienen una ID y un Usuario Propietario. Tienen asignados una serie de Personajes. Definen la Formación que jugará la Batalla.

**- Mercado:** Tiene una ID y una serie de Personajes en oferta. Esos personajes no pueden salir aquí si están asignados a una Formación o son "Default".

**- Puja:** Tienen una ID, un Usuario pujante, un Personaje pujado y una cantidad de Dinero. Sirven para elegir al ganador del Personaje que se encuentra en el mercado.

### INTERFAZ SERVIDOR INTERNO <a name="servidorInterno"/>

Todas las actividades que realiza el Servidor Interno son respuestas a las peticiones de la aplicación. La comunicación entre estos dos se realiza mediante sockets. El servidor escuchará en el puerto 9000. 

La aplicación manda una orden (en forma de mensaje String) al Servidor Interno. Este lo lee, interpretando que debe realizar una acción u otra. Entonces, realiza una serie de cálculos y actualiza la base de datos, sin devolver nada a la aplicación. Estas órdenes pueden ser:

**- Simular las batallas:** Se calculan los puntos y el dinero de cada jugador a partir de las Formaciones que alineen. La web mandará el mensaje "Batalla" a través del Socket, y este ejecutará los cálculos pertinentes y actualizará la base de datos. Cuando haya acabado, le mandará un mensaje a la web para que invalide su caché. Luego se cierran las comunicaciones.

**- Refrescar el Mercado:** Selecciona aleatoriamente de la base de datos de personajes libres en la liga y los pone en el mercado. Además, gestiona las pujas de los usuarios y el dinero que manejan. La web mandará el mensaje "Refrescar" a través del Socket para que se ejecuten dichas operaciones y actualice la base de datos. Al acabar se enviará a la web un mensaje para que invalide la caché.

### SEGURIDAD <a name="seguridad"/>

Para la implementación de la seguridad se ha usado Spring Security. Mediante la creación de una clase SecurityConfiguration configuramos qué páginas puede ver el usuario sin logearse, logeado, y siendo USER o ADMIN, que son los dos roles con los que cuenta la aplicación. También configuramos cual va a ser la página de login, y cual va a ser el "AuthenticationProvider", que en este caso será la base de datos. Este "AuthenticationProvider" comprobará si la contraseña introducida se corresponde con la del usuario en la base de datos ( estas contraseñas están protegidas por una operación Hash). Pero no es lo único que hace, ya que también comprueba si el usuario está baneado, y si ese es el caso no dejará logear al usuario, además de indicarle que ha sido baneado. Además de esta autentificación, se han protegido los formularios de ataques CSRF mediante tokens que se les pasa a los templates de mustache desde los controllers.

También se usa HTTPS, que hace uso de un certificado autofirmado para funcionar.

A continuación podemos ver como vería la aplicación un usuario convencional y el administrador (respectivamente). Aclarar que por ahora solo hay un ADMIN en la base de datos, y que los nuevos usuarios que se creen en la base de datos tendrán el rol USER.

![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/USER.png)
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/ADMIN.png)

## MODELO DE DATOS <a name="modeloDeDatos"/>

#### DIAGRAMA ENTIDAD RELACION
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/DIAGRAMA%20ENTIDAD%20RELACION.png)

#### DIAGRAMA UML
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/DIAGRAMA%20UML.png)

## PANTALLAS <a name="pantallas"/>

### CAPTURAS <a name="capturasPantalla"/>

#### INICIO
Pantalla donde se muestra el título de la aplicación y las opciones disponibles para entrar en ella.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/INICIO.png)

#### INICIAR SESIÓN
Pantalla para iniciar sesión con Usuario y Contraseña. Avisa si el usuario no existe o falta información.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/INICIAR%20SESION.png)

#### CREAR CUENTA
Pantalla para crear una cuenta con Usuario y Contraseña. Avisa si el usuario ya existe en la aplicación o falta información.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/CREAR%20CUENTA.png)

#### MENU PRINCIPAL
Pantalla principal de la aplicación. Tiene un banner superior donde se muestra el Título, el usuario actual y su dinero y puntos. Con el Navegador de la izquierda o los botones del centro, se pueden navegar entre pantallas. Los botones Borrar Usuario y Cerrar Sesión llevan a la pantalla de Inicio, realizando las acciones pertinentes. Aclaración: los botones marcados como ADMIN solo los puede ver un usuario Administrador, así como los botones Servidor Interno, que representan funcionalidad del mismo.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/MENU%20PRINCIPAL.png)

#### FORMACION
Pantalla donde se muestran los personajes que tiene el equipo del usuario. Se pueden vender para obtener dinero.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/FORMACION.png)

#### MERCADO
Pantalla donde se muestran los personajes en venta de la aplicación. Se puede pujar una cantidad superior o igual al precio del personaje, y se decide el ganador cuando el servidor interno refresca el mercado.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/MERCADO.png)

#### CLASIFICACION
Pantalla donde se muestra el Top 10 de mejores puntuaciones en el juego.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/CLASIFICACION.png)

#### CREAR PERSONAJE
Pantalla donde un Administrador puede añadir nuevos personajes a la base de datos. Avisa si falta algun tipo de información.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/CREAR%20PERSONAJE.png)

#### MOSTRAR BD
Pantalla donde un Administrador puede consultar toda la base de datos de Personajes.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/MOSTRAR%20BD.png)

#### ADMINISTRAR
Pantalla donde un Administrador puede banear a ciertos usuarios e impedir su entrada a la aplicación
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/ADMINISTRAR.png)

#### ERROR LOGIN
Pantalla de error que aparece cuando un usuario intenta entrar en una página sin iniciar sesión, por lo que no tendría acceso.
![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/PANTALLAS/ERROR.png)

### DIAGRAMA DE NAVEGACIÓN <a name="diagramaNavegacion"/>

![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/DIAGRAMA%20DE%20PANTALLAS.png)

### DIAGRAMA DE CLASES <a name="diagramaClases"/>

![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/DEFI.png)

## NOTAS DE LA FASE 2 <a name="notasFase2"/>

Para esta segunda fase se han implementado toda la funcionalidad de la página, tanto la parte de los jugadores como la de los administradores, así como la creación de la base de datos, sus entidades, consultas y relaciones.

Ahora mismo, todo usuario que se haga una cuenta e inicie sesión puede acceder tanto a la parte pública como la privada. Cuando se implemente la separación de estos dos roles, así como la seguridad en la aplicación, esto no será así, ya que solo los usuarios con el rol de Administrador podrán ver las opciones exclusivas de dicho rol.

También cabe destacar que el modo en que se inicia sesión y se crea una cuenta está sujeto a cambios futuros en cuanto implementemos la seguridad. Es decir, su implementación actual solo es un pequeño prototipo de como funcionará en un futuro.

También hemos añadido al menú principal varios botones que ejecutan operaciones típicas del servidor interno, tales como refrescar el mercado o ejecutar una batalla aleatoria, para poder comenzar a testear su funcionamiento. Sin embargo, esto será totalmente transparente para el usuario en el proyecto final.

En cuanto al esquema de MySql, por ahora está en modo "create-drop", ya que consideramos que a la aplicación aún le queda bastante desarrollo. De hecho, en el método Started() del WebController se inicializa la base de datos con algunos usuarios, personajes, un mercado y una batalla. Todo esto en pos de testear la aplicación. Esta inicialización se comentará/borrará en las versiones finales.

## NOTAS DE LA FASE 3 <a name="notasFase3"/>

Se ha implementado el apartado de seguridad en todas sus facetas. Se han usado tokens para evitar ataques CSRF, hemos codificado las contraseñas, se ha usado Spring Security, la pagina funciona con HTTPS y se han definido los 2 roles de ADMIN y USER.

Para la comunicación entre las aplicaciones, hemos usado sockets. Esto se debe a que nuestra comunicacion se basa en enviar ordenes, y no en devolver ninguna información. Con sockets podemos simplemente mandar Strings con la instrucción correspondiente y que el servidor lo interprete. El servidor interno modificará la base de datos según la instrucción recibida: podrá refrescar el mercado o ejecutar una batalla. Estas operaciones pueden tardar en caso de que se tenga un alto número de usuarios, y es por ello que se ha decidido que sea el servidor interno quién las ejecute. 

Para el despliegue en una máquina virtual desde cero se ha usado Ubuntu ya que es gratuito y fácil de usar. 

## DESPLIEGUE E INSTALACIÓN <a name="despliegue"/>

A continuación se va a explicar como se han generado los jar y cómo se ha instalado la aplicación desde cero en una máquina virtual.


**1.-Compilamos ambas aplicaciones:** (tanto la aplicación web como el servidor interno) con Maven Clean. Luego lo hacemos con la opción de Maven Build. En la ventana de opciones que se despliega ponemos como opción en "goals" "package". Esto ya nos generá los jars necesarios.


**2.- Máquina virtual:** Se ha usado el Sistema Operativo Ubuntu 20.04). Hay que **instalar Java y MySql, así como MySql WorkBench**. Pero antes de ejecutar las aplicaciones, debemos cambiar la contraseña de MySql para el usuario root para que coincida con la añadida en el archivo properties de las aplicaciones. Esto lo hacemos abriendo MySql con el usuario correspondiente y ejecutando la linea "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'GatoPato115'", y después "FLUSH PRIVILEGES;". Una posible mejora sería poder modificar dicha contraseña añadiendola como argumento.


**3.- Ejecutar JAR:** Ahora que está todo preparado, se abre la consola en la carpeta del jar de la aplicación web y ejecutamos : **java -jar "nombre_del_jar_de_la_app_web". jar**. Después procedemos a hacer lo mismo en la carpeta donde se encuentra el jar del servidor interno: **java -jar "nombre_del_jar_del_servidor_interno". jar**.


-**La aplicación ya se está ejecutando**, y se puede comprobar accediendo desde el navegador web de la máquina virtual. Se puede acceder desde cualquier otra maquina con la ID correspondiente. Como la app corre en una máquina virtual, hay que especificar que use un adaptador de puentes para traducir la IP.


Aclarar que mientras que la aplicación web tiene el esquema MySql en "create-drop", el servidor interno lo tiene como "update", y es por eso que ejecutamos la aplicación web primero. En un estado más avanzado de la aplicación donde la base de datos ya sea fija, ambos podrán estar en "update".


## DIAGRAMA DEL DESPLIEGUE EN DOCKER <a name="diagrama"/>

![alt text](https://github.com/Celtia-Martin/A-Fantasy-about-History/blob/main/MEMORIA/DiagramaDocker.png)

## NOTAS DE LA FASE 4 <a name="notasFase4"/>

Se ha implementado la aplicación usando contenedores Docker, que se comunican entre sí mediante Docker Compose. Tanto la web como el servidor interno están en un contenedor independiente, y junto a la base de datos y el balanceador de carga forman la aplicación. El balanceador de carga está implementado con Haproxy, y gestiona dos nodos distintos de la web. Si uno cae, Haproxy se encarga de migrar al usuario al otro nodo, proporcionando además la posibilidad de separar a los usuarios entre los nodos y aligerar la carga.

También se han cacheado algunas consultas a la table de personajes y de usuarios. Para la segunda, ha sido necesario usar el carácter bidireccional de los sockets, ya que el servidor interno, una vez haya acabado las operaciones pertinentes, enviará un mensaje a la web para que invalide la caché.

Se ha eliminado la clase SessionScope ya que causaba problemas con Hazelcast. En su lugar se han utilizado HttpSession y HttpServletRequest.

## VIDEO DEMOSTRATIVO <a name="video"/>

<a href="https://youtu.be/r5VWQu1NNkI">** LINK DEL VIDEO **</a>

