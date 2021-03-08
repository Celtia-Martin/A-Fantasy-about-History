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
### [1.3.- SERVIDOR INTERNO](#servidorInterno)
## [2.- MODELO DE DATOS](#modeloDeDatos)	
## [3.- PANTALLAS](#pantallas)
### [3.1.- CAPTURAS ](#capturasPantalla)
### [3.2.- DIAGRAMA DE NAVEGACIÓN ](#diagramaNavegacion)
## [4.- NOTAS FASE 2](#notasFase2)

## DESCRIPCION <a name="descripcion"/>

### APLICACION <a name="aplicacion"/>

A Fantasy about History es una aplicación web que presenta un juego de tipo Fantasy (Biwenger, Comunio, La Liga Fantasy), pero que no depende de ningun suceso real, ya que se juega con personajes históricos. Como cualquier aplicación Fantasy, los usuarios tendrán que iniciar sesión y unirse a la liga del juego.

Una vez dentro de esta liga, se nos ofrece una formación por defecto (con personajes generados automáticamente), que puede ser rellenada con personajes históricos. Con esta formación, se librará una batalla automática contra el resto de jugadores, consiguiendo más puntuación la que tenga mejores personajes para cada situación de la batalla. Se disputarán varias batallas (un día específico de la semana), y el ganador de la liga será aquel equipo que haya acumulado más puntos tras todas estas.

Para conseguir mejores personajes, la liga tendrá su propio mercado de personajes en el que los jugadores pueden pujar. El que más oro ofrezca, se lo lleva para su equipo. Se comienza con 0 oro, y por cada punto obtenido en las batallas se consigue más. El mercado se refresca cada 24 horas, y ahi se decide quien gana la puja. Cada equipo tiene un máximo de 6 personajes, sin mínimo, y una vez llegada esta cantidad no se puede pujar por nuevos personajes. Por ello, es necesario vender personajes antiguos para comprar otros nuevos, y estos podrán volver a salir en el mercado.

Las batallas tienen un día y hora concreto , y son totalmente automatizadas. Esto lo llevará el servidor interno. Estas batallas simuladas darán lugar a un resultado y a una serie de puntos repartidos entre todos los miembros de la liga, así como una cantidad específica de oro. Las batallas no se deciden solo por la calidad de los personajes: ofrecen una serie de información que harán que destaquen ciertos personajes sobre otros. Por ejemplo, si es una batalla cultural, los personajes culturales tendrán más peso. Así, se definen 3 tipos de personajes: militares, diplomáticos y culturales. 

Hay 2 niveles de jerarquía: el administrador de liga y el jugador. Un ADMINISTRADOR (normalmente los creadores) puede editar y ampliar las bases de datos, añadiendo personajes y batallas a las mismas. También pueden banear a jugadores de la liga. Un JUGADOR puede participar en batallas, pujar en el mercado, editar formaciones, etc.

Es estrictamente PRIVADO para los creadores la Simulación de Batallas, el reparto de puntos y oro y el cambio de personajes en el mercado.

### ENTIDADES <a name="entidades"/>

**- Usuario:** Tienen una ID, un Nombre de Usuario y Contraseña, y una cantidad de Puntos y Dinero. Cada usuario que entre en la aplicación será añadido a la liga, y tendrá una Formación asignada.También se indica si está baneado o no.

**- Personaje:** Tiene una ID, un Nombre, un Rango, un Tipo, un Precio y una serie de Características (Valor Militar, Diplomático y Cultural). Son parte de Formaciones ( si lo están es indicado por un booleano), y pueden salir en el Mercado. Hay un tipo especial: los Default (o generados), que se borran al abandonar la formación y no salen en el mercado. Se otorgan en la primera Formación.

**- Batalla:** Tienen una ID y un Tipo de Batalla. Son parte de una Liga, en la que hay varias. Se involucran una serie de Formaciones, y se reparten puntos y dinero.

**- Formaciones:** Tienen una ID y un Usuario Propietario. Tienen asignados una serie de Personajes. Definen la Formación que jugará la Batalla.

**- Mercado:** Tiene una ID y una serie de Personajes en oferta. Esos personajes no pueden salir aquí si están asignados a una Formación o son "Default".

**- Puja:** Tienen una ID, un Usuario pujante, un Personaje pujado y una cantidad de Dinero. Sirven para elegir al ganador del Personaje que se encuentra en el mercado.

**OPCIONAL**: Añadir varias **LIGAS**, que pueden ser públicas o privadas.

### SERVIDOR INTERNO <a name="servidorInterno"/>

Se encarga de:

**- Simular las batallas:** Se calculan los puntos y el dinero de cada jugador a partir de las Formaciones que alineen.

**- Refrescar el Mercado:** Selecciona aleatoriamente de la base de datos de personajes libres en la liga y los pone en el mercado. Esto ocurre cada 24 horas, en una hora concreta.

**OPCIONAL**: Controlar el Calendario, el ratio de las batallas y la hora del refresco del mercado.

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

## NOTAS DE LA FASE 2 <a name="notasFase2"/>

Para esta segunda fase se han implementado toda la funcionalidad de la página, tanto la parte de los jugadores como la de los administradores, así como la creación de la base de datos, sus entidades, consultas y relaciones.

Ahora mismo, todo usuario que se haga una cuenta e inicie sesión puede acceder tanto a la parte pública como la privada. Cuando se implemente la separación de estos dos roles, así como la seguridad en la aplicación, esto no será así, ya que solo los usuarios con el rol de Administrador podrán ver las opciones exclusivas de dicho rol.

También cabe destacar que el modo en que se inicia sesión y se crea una cuenta está sujeto a cambios futuros en cuanto implementemos la seguridad. Es decir, su implementación actual solo es un pequeño prototipo de como funcionará en un futuro.

También hemos añadido al menú principal varios botones que ejecutan operaciones típicas del servidor interno, tales como refrescar el mercado o ejecutar una batalla aleatoria, para poder comenzar a testear su funcionamiento. Sin embargo, esto será totalmente transparente para el usuario en el proyecto final.

En cuanto al esquema de MySql, por ahora está en modo "create-drop", ya que consideramos que a la aplicación aún le queda bastante desarrollo. De hecho, en el método Started() del WebController se inicializa la base de datos con algunos usuarios, personajes, un mercado y una batalla. Todo esto en pos de testear la aplicación. Esta inicialización se comentará/borrará en las versiones finales.

