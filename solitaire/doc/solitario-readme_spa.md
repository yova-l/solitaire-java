## Se disponen de 3 grandes objetos:
* Inicializador
* Estado
* Juego

### InicializadorSpider/Klondike:
Este objeto puede recibir una semilla (int) y generar un estado
válido, aleatorio de un juego de solitario Klondike/Spider, o bien, recibir
una descripción con cierta codificación (List<List(String)>) que
describe el estado en que se encuentran las pilas del juego. 
Tras construirse de cualquiera de las dos maneras, el objeto almacenará
el estado inicial del juego y este podrá obtenerse para ser utilizado por
otro objeto.

### Estado
Este objeto es el encargado de ejecutar los movimientos que se le soliciten
y mantener actualizado el estado de las distintas pilas del juego. Detecta
si el juego está ganado y puede generar una descripción del juego
con el formato (List<List(String)>), para futuras features como "deshacer" un
movimiento o "guardar partida". Existen dos subclases de Estado correspondientes
a los tipos de juego diferentes. Estas subclases sobrescriben la manera de 
detectar si el juego esta ganado. Además poseen sus métodos particulares para
interpretar y realizar movimientos especiales de cada implementación.

### Juego
Este objeto es el encargado de validar que el movimiento solicitado sea un
movimiento válido según las reglas del tipo de solitario, en otras palabras, "filtra"
los movimientos para que Estado solo ejecute movimientos válidos. Consta de una 
superclase JuegoSolitario y dos subclases JuegoSpider y JuegoKlondike, que heredan
métodos comunes de la superclase e implementan los suyos particulares, principalmente
para analizar si un movimiento es válido en su tipo de juego.

---
## Otros objetos:

### Enums
ZonasKlondike, ZonaSpider, Palo, NumeroCarta, Color son los enums disponibles en el 
programa. Se utilizan para validar movimientos, la creación de cartas, y
para generar las descripciones del estado de las pilas del juego (List<List<String>>),
permitiendo una codificación compacta. 

Para la codificación de  los elementos a una descripción, cada bullet point representa
un elemento de la siguiente manera:
* ["1-0-true", "12-1-true"]
* []
* ["0-0-false"]
* ...

En este caso, el elemento 0 de la lista hace referencia a la ZonaKlondikePOZO1, cuyo cardinal es 0. 
La sublista contenida hace referencia a una colección de cartas definidas por un String, el cual 
se compone por 3 atributos separados por un guión medio: el primero 
hace referencia al enum NumeroCarta; el segundo, al cardinal del enum Palo; y el 
último atributo hace referencia a si la carta está oculta o visible (true/false respectivamente).    
En este ejemplo podemos ver que el "pozo" (su estado) tiene 
un DOS de CORAZONES OCULTO, por encima una K de PICA OCULTA; el "waste" no tiene
cartas; y la PILA RESULTADO N°1 tiene el AS de CORAZON VISIBLE.

### Interfaz CardsStack/PilaJuegoSolitario y Pilas
La interfaz CardsStack modela un "stack" de cartas a la cual le puedo extraer o 
colocarle un subconjunto, puede ver si esta vacía, y traducir su estado
en una descripción (List(String)).

Las distintas pilas implementadas modelan las distintas zonas del juego, todas
responden a los mismos mensajes PERO con ligeras modificaciones. Según la lógica
necesaria para la parte del tablero que representan.

### FabricaPilas / FabricaPilasEstado
En el caso de la FabricaPilas se utiliza un mazo, y a través de una descripción que se 
pasa por distintos parámetros, es capaz de ir acumulando en una List<Cardstack> las distintas 
configuraciones de pila que se le pidan (Ejemplo 1).
En el caso de la FabricaPilasEstado, esta recibe además un List<List<Card>> correspondiente
al contenido de cartas que debe utilizarse para armar una List<CardStack> dependiendo de la
configuración que se le pase por parámetro (Ejemplo 2).
``` java
        /*Ejemplo 1*/
        var fabrica = new FabricaPilas(new MazoCartas(semilla, 8, true)); 
            // 8 = cantidad de juegos (cartas desde A hasta K); 
            // boolean = si todos los juegos son del mismo palo.
        List<CardsStack> pilas = new ArrayList<>();
        pilas = fabrica.crearPilas( 1, pilas, 50, PilaPozo::new);        
        pilas = fabrica.crearPilas(4, pilas, 6, 5);
        pilas = fabrica.crearPilas(6, pilas, 5, 4);
        
        /*Ejemplo 2*/
        var arrCartas = Parsers.generarArrCards(desc, new ArrayList<>());
        List<CardsStack> pilas = new ArrayList<>();
        var fabricaPilasEstado = new FabricaPilasEstado();
        pilas = fabricaPilasEstado.crearPilas(1, arrCartas, pilas, PilaPozo::new);
        pilas = fabricaPilasEstado.crearPilas(10, arrCartas, pilas, PilaJuego::new);
```

### MazoCartas
Se utiliza para inicializar un juego nuevo a partir de una semilla, una canditad de juegos
(juego = cartas desde A hasta K) y un boolean que indica si son todos los juegos del
mismo palo, o estos deben variar. Permite solicitarle una cantidad de cartas y las devolverá 
mezcladas según la "aleatoreidad" de la semilla y todas NO visibles.

### Parsers
Es una biblioteca de funciones que se encarga de generar una List<List<Card>> a partir
de una descripción, o generar una descripción a traves de una lista de cartas

### Carta
Modela una carta en un juego de naipes. Utiliza los enums antes mencionados. Es la implementación
de la intefaz Card que a su vez es un Descriptable

### FabricaDeCartas
Como su nombre lo indica, es una biblioteca simple que devuelve un objeto Carta. 
Por ejemplo si la descripción es "0-0-true" se creará un objeto Carta AS de CORAZON NO visible.

### Gamesaver
Es el objeto encargado generar un estado a partir de la ruta a un archivo binario
que contiene la serializacion de un estado. También puede realizar el proceso inverso, es decir,
a partir de un estado generar el archivo. Esta clase es agnósotica al tipo de Solitario.

### Movimiento
Esta clase modela una intención de movimiento, posee los datos necesarios para evaluar
si un movimiento es válido o no: la carta origen y destino, la zona origen y destino
del tablero, si el movimiento pretende arrastrar una cascada de cartas, la posición en el 
stack de cartas de origen, y la posición en el stack de cartas de destino. Esta clase
es agnóstica al tipo de Solitario.