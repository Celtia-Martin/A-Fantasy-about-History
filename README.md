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

## DESCRIPCION <a name="descripcion"/>

### APLICACION <a name="aplicacion"/>

A Fantasy about History es una aplicación web que presenta un juego de tipo Fantasy (Biwenger, Comunio, La Liga Fantasy), pero que no depende de ningun suceso real, ya que se juega con personajes históricos. Como cualquier aplicación Fantasy, los usuarios tendrán que iniciar sesión y unirse a la liga del juego.

Una vez dentro de esta liga, se nos ofrece una formación por defecto (con personajes generados automáticamente), que puede ser rellenada con personajes históricos. Con esta formación, se librará una batalla automática contra el resto de jugadores, consiguiendo más puntuación la que tenga mejores personajes para cada situación de la batalla. Se disputarán varias batallas (un día específico de la semana), y el ganador de la liga será aquel equipo que haya acumulado más puntos tras todas estas.

Para conseguir mejores personajes, cada liga tendrá su propio mercado de personajes en el que los jugadores pueden pujar. El que más oro ofrezca, se lo lleva para su equipo. Se tiene desde el principio un oro inicial, y por cada punto obtenido en las batallas se consigue más. El mercado se refresca cada 24 horas, y ahi se decide quien gana la puja. Cada equipo tiene un máximo de 6 personajes, sin mínimo, y una vez llegada esta cantidad no se puede pujar por nuevos personajes. Por ello, es necesario vender personajes antiguos para comprar otros nuevos, y estos podrán volver a salir en el mercado.

Las batallas tienen lugar un día y hora concreto de la semana, y son totalmente automatizadas. Esto lo llevará el servidor interno. Estas batallas simuladas darán lugar a un resultado y a una serie de puntos repartidos entre todos los miembros de la liga, así como una cantidad específica de oro. Las batallas no se deciden solo por la calidad de los personajes: ofrecen una serie de información que harán que destaquen ciertos personajes sobre otros. Por ejemplo, si es una batalla cultural, los personajes culturales tendrán más peso. Así, se definen 3 tipos de personajes: militares, diplomáticos y culturales. 

Hay 2 niveles de jerarquía: el administrador de liga y el jugador. Un ADMINISTRADOR DE LIGA (normalmente los creadores) puede editar y ampliar las bases de datos, añadiendo personajes y batallas a las mismas. También pueden expulsar a jugadores de la liga, e imponer sanciones administrativas. Un JUGADOR puede participar en batallas, pujar en el mercado, editar formaciones, etc.

Es estrictamente PRIVADO para los creadores la Simulación de Batallas, el reparto de puntos y oro y el cambio de jugadores en el mercado.

### ENTIDADES <a name="entidades"/>

**- Usuario:** Tienen una ID, un Nombre de Usuario y Contraseña, y una cantidad de Puntos y Dinero. Cada usuario que entre en la aplicación será añadido a la liga, y tendrá una Formación asignada.

**- Personaje:** Tiene una ID, un Nombre, un Rango, un Tipo, un Precio y una serie de Características (Valor Militar, Diplomático y Cultural). Son parte de Formaciones, y pueden salir en el Mercado. Hay un tipo especial: los Default (o generados), que se borran al abandonar la formación y no salen en el mercado. Se otorgan en la primera Formación.

**- Batalla:** Tienen una ID y un Tipo de Batalla. Son parte de una Liga, en la que hay varias. Se involucran una serie de Formaciones, y se reparten puntos y dinero.

**- Formaciones:** Tienen una ID y un Usuario Propietario. Tienen asignados una serie de Personajes. Definen la Formación que jugará la Batalla.

**- Mercado:** Tiene una ID y una serie de Personajes. Esos personajes no pueden salir aquí si están asignados a una Formación.

**- Puja:** Tienen una ID, un Usuario pujante, un Personaje pujado y una cantidad de Dinero. Sirven para elegir al ganador del Personaje que se encuentra en el mercado.

**OPCIONAL**: Añadir varias **LIGAS**, que pueden ser públicas o privadas.

### SERVIDOR INTERNO <a name="servidorInterno"/>

Se encarga de:

**- Simular las batallas:** Se calculan los puntos y el dinero de cada jugador a partir de las Formaciones que alineen.

**- Refrescar el Mercado:** Selecciona aleatoriamente de la base de datos de personajes libres en la liga y los pone en el mercado. Esto ocurre cada 24 horas, en una hora concreta.

**OPCIONAL**: Controlar el Calendario, el ratio de las batallas y la hora del refresco del mercado.

## MODELO DE DATOS <a name="modeloDeDatos"/>


## PANTALLAS <a name="pantallas"/>

### CAPTURAS <a name="capturasPantalla"/>



### DIAGRAMA DE NAVEGACIÓN <a name="diagramaNavegacion"/>


