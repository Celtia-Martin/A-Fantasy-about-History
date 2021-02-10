# A Fantasy about History

## Integrantes

| **NOMBRE**  |  **APELLIDOS**    |             **CORREO**             |               **GITHUB**              |
| :---------: | :---------------: | :--------------------------------: | :-----------------------------------: |
| Celtia      | Martin García     | c.martinga.2017@alumnos.urjc.es    | https://github.com/Celtia-Martin      |
| Daniel      | Muñoz Rivera      | d.munozri.2017@alumnos.urjc.es     | https://github.com/MeSientoYMeCallo99 |

## Descripcion

A Fantasy about History es una aplicación web que presenta un juego de tipo Fantasy (Biwenger, Comunio, La Liga Fantasy), pero que no depende de ningun suceso real, ya que se juega con personajes históricos. Como cualquier aplicación Fantasy, los usuarios tendrán que iniciar sesión y crear una liga o unirse a una. Las ligas pueden ser públicas o privadas. 

Una vez dentro de esta liga, se nos ofrece una formación, que debe ser rellenada con personajes históricos. Con esta formación, se librará una batalla automática contra el resto de jugadores, consiguiendo más puntuación la que tenga mejores personajes para cada situación de la batalla. Se disputarán varias batallas (un día específico de la semana), y el ganador de la liga será aquel equipo que haya acumulado más puntos tras todas estas.

Para conseguir mejores personajes, cada liga tendrá su propio mercado de personajes en el que los jugadores pueden pujar. El que más oro ofrezca, se lo lleva para su equipo. Se tiene desde el principio un oro inicial, y por cada punto obtenido en las batallas se consigue más. El mercado se refresca cada 24 horas, y ahi se decide quien gana la puja. Entre los usuarios, pueden negociar el traspaso o venta de personajes, y también robarle uno a otro usuario, a través de los Cambios de Fidelidad (pagando una cantidad de dinero fijada por el otro usuario).

Las batallas tienen lugar un día y hora concreto de la semana, y son totalmente automatizadas. Esto lo llevará el servidor interno. Estas batallas simuladas darán lugar a un resultado y a una serie de puntos repartidos entre todos los miembros de la liga, así como una cantidad específica de oro.

Las batallas no se deciden solo por la calidad de los personajes: ofrecen una serie de información que harán que destaquen ciertos personajes sobre otros. Por ejemplo, si es una batalla cultural, los personajes culturales tendrán más peso. Así, se definen 3 tipos de personajes: militares, diplomáticos y culturales. Estas características se mostrarán tras el cierre del último mercado antes de la batalla (además de prohibir los Cambios de Fidelidad).

Las formaciones son variables, pero siempre estan formadas por X (de momento, 6) unidades. Se pueden cambiar momentos antes de la batalla. Los personajes que no entren en la formación estarán en el banquillo, y no otorgarán puntos.

Hay 3 niveles de jerarquía: el editor, el administrador de liga y el jugador. Un EDITOR (normalmente los creadores) puede editar y ampliar las bases de datos, añadiendo personajes y batallas a las mismas. También puede modificar el ratio de batallas y de refresco del mercado. Un ADMINISTRADOR DE LIGA puede expulsar a jugadores de su liga, e imponer sanciones administrativas. El creador de la liga será quién tenga este rol. Un JUGADOR puede participar en batallas, pujar en el mercado, editar formaciones, etc.

Es estrictamente PRIVADO para los creadores la Simulación de Batallas, el reparto de puntos y oro y el cambio de jugadores en el mercado.

## Entidades

**- Usuario:** Tiene Nombre de Usuario y Contraseña. Puede estar asociado a una liga o a varias (si no está asociado, no puede jugar). Por cada liga, tiene una Formación (que se identifica según la liga a la que pertenece), un Equipo (conjunto de Personajes, identificados por la liga a la que pertenece) y una cantidad de puntos y oro.

**- Personaje:** Tiene un Nombre, un Rango, un Tipo, un Precio y una serie de Características (Valor Militar, Diplomático y Cultural). Son parte de Equipos y Formaciones, y pueden salir en el Mercado.

**- Batalla:** Tiene un Tipo de Batalla. Son parte de una Liga, en la que hay varias. Se involucran una serie de Formaciones.

**- Ligas:** Tienen un Nombre y pueden tener una Contraseña. Tienen un conjunto de Usuarios y un Mercado. Contiene la información de las Batallas.

**- Formaciones:** Tienen una ID. Tienen asignados una serie de Personajes, y la Liga a la que pertenecen. Definen la Formación que jugará la Batalla.

**- Equipos:** Tienen un Numero. Tienen asignados una serie de Personajes, y la Liga a la que pertenecen. Reúne tanto a los personajes de la Formación como a los del banquillo.

**- Mercado:** Tiene una serie de Personajes. 

## Servidor Interno

Se encarga de:

**- Simular las batallas:** Se calculan los puntos y el dinero de cada jugador a partir de las Formaciones que alineen.

**- Organizar el Calendario de las Ligas:** Define la hora de refresco del mercado y las fechas de las batallas.

**- Refrescar el Mercado:** Selecciona aleatoriamente de la base de datos de personajes libres en cada liga y los pone en el mercado. 

**- Ampliar el contenido:** Se puede añadir internamente nuevos personajes y batallas, sin necesidad de modificar las ligas existentes.

**- Gestión de Usuarios:** Autenticación y Seguridad de los datos del usuario.


