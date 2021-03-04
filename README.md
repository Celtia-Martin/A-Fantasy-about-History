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

Una vez dentro de esta liga, se nos ofrece una formación, que debe ser rellenada con personajes históricos. Con esta formación, se librará una batalla automática contra el resto de jugadores, consiguiendo más puntuación la que tenga mejores personajes para cada situación de la batalla. Se disputarán varias batallas (un día específico de la semana), y el ganador de la liga será aquel equipo que haya acumulado más puntos tras todas estas.

Para conseguir mejores personajes, cada liga tendrá su propio mercado de personajes en el que los jugadores pueden pujar. El que más oro ofrezca, se lo lleva para su equipo. Se tiene desde el principio un oro inicial, y por cada punto obtenido en las batallas se consigue más. El mercado se refresca cada 24 horas, y ahi se decide quien gana la puja. Entre los usuarios, pueden negociar el traspaso o venta de personajes, y también robarle uno a otro usuario, a través de los Cambios de Fidelidad (pagando una cantidad de dinero fijada por el otro usuario).

Las batallas tienen lugar un día y hora concreto de la semana, y son totalmente automatizadas. Esto lo llevará el servidor interno. Estas batallas simuladas darán lugar a un resultado y a una serie de puntos repartidos entre todos los miembros de la liga, así como una cantidad específica de oro.

Las batallas no se deciden solo por la calidad de los personajes: ofrecen una serie de información que harán que destaquen ciertos personajes sobre otros. Por ejemplo, si es una batalla cultural, los personajes culturales tendrán más peso. Así, se definen 3 tipos de personajes: militares, diplomáticos y culturales. Estas características se mostrarán tras el cierre del último mercado antes de la batalla (además de prohibir los Cambios de Fidelidad). Las formaciones son variables, pero siempre estan formadas por X (de momento, 6) unidades.

Hay 2 niveles de jerarquía: el administrador de liga y el jugador. Un ADMINISTRADOR DE LIGA (normalmente los creadores) puede editar y ampliar las bases de datos, añadiendo personajes y batallas a las mismas. También pueden expulsar a jugadores de la liga, e imponer sanciones administrativas. Un JUGADOR puede participar en batallas, pujar en el mercado, editar formaciones, etc.

Es estrictamente PRIVADO para los creadores la Simulación de Batallas, el reparto de puntos y oro y el cambio de jugadores en el mercado.

### ENTIDADES <a name="entidades"/>

**- Usuario:** Tiene Nombre de Usuario y Contraseña. Estan asociados a una Liga. En esa liga, tiene una Formación (que se identifica según la liga a la que pertenece) y una cantidad de puntos y oro.

**- Personaje:** Tiene un Nombre, un Rango, un Tipo, un Precio y una serie de Características (Valor Militar, Diplomático y Cultural). Son parte de Formaciones, y pueden salir en el Mercado.

**- Batalla:** Tiene un Tipo de Batalla. Son parte de una Liga, en la que hay varias. Se involucran una serie de Formaciones.

**- Formaciones:** Tienen una ID. Tienen asignados una serie de Personajes. Definen la Formación que jugará la Batalla.

**- Mercado:** Tiene una serie de Personajes.

**- Puja:** Tienen un Usuario, un Personaje y una cantidad de Dinero. Sirven para elegir al ganador del Personaje que se encuentra en el mercado.

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


