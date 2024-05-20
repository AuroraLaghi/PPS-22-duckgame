# Implementazione
Nel capitolo seguente sono stati motivati e dettagliati gli aspetti implementativi ritenuti rilevanti per una corretta comprensione del progetto.

È importante evidenziare che la documentazione del codice è stata curata utilizzando `Scaladoc`. Questa documentazione dettagliata può essere consultata per approfondire
la comprensione dell'implementazione dell'applicativo e del suo funzionamento.

## Paradigma funzionale

Durante lo sviluppo del progetto si è cercato di attenersi il più possibile
al paradigma funzionale e, di conseguenza, raffinare la soluzione scelta per
poter sfruttare al meglio i vantaggi offerti da questo paradigma.

Se durante l'implementazione di una determinata funzionalità si notava un
approccio orientato maggiormente all'*object-oriented* piuttosto che al
paradigma funzionale, a seguito di un'attenta analisi alla ricerca di una
soluzione più funzionale si procedeva con il *refactoring* del codice.

Nelle prossime sezioni si discuteranno in modo più approfondito alcuni elementi
della programmazione funzionale utilizzati all'interno del progetto, riportandone
alcuni esempi del loro utilizzo.

### Currying

In *Scala* è possibile definire funzioni *curried*, per cui la valutazione di
una funzione che assuma parametri multipli può essere tradotte nella
valutazione di una sequenza di funzioni.

Questo meccanismo consente di applicare il principio **DRY** (Don't Repeat Yourself),
favorendo così il riuso di codice: infatti, quando una funzione è *curried*, è
possibile applicare parzialmente la funzione per poterla utilizzare in più
punti del codice.

Nel seguente listato si può osservare un esempio di utilizzo di questo meccanismo

```scala
  private def colRowFromTerm(termX: Term)(termY: Term): (Int, Int) =
  (termX.toString.toDouble.toInt, termY.toString.toDouble.toInt)
```

Il metodo `colRowFromTerm` estrae i due valori dal termine della soluzione ottenuta
da *Prolog*, convertendola in stringa e successivamente in intero

```scala
 given Conversion [SolveInfo, (Int, Int)] = e => colRowFromTerm(e.getTerm("X"))(e.getTerm("Y"))
```

La funzione viene utilizzata all'interno della classe `PrologGameUtils` per
convertire in intero la colonna e la riga risultanti dall'esecuzione della funzione in *Prolog*.
Per chiamare la funzione bisogna mantenere la medesima notazione *currying*.

### Higher-order functions

Un meccanismo spesso utilizzato nella programmazione funzionale sono le
*higher-order functions*: funzioni che accettano altre funzioni come parametri
e/o restituiscono una funzione come risultato.

L'utilizzo di queste funzioni permette di rendere il codice riusabile, evitando
ridondanza e di adoperare il *pattern Strategy*, in quanto consente di
passare alle funzioni strategie esterne.

Uno degli esempi più comuni di *higher-order function* è `map`; nel listato
sottostante è presente una funzione *higher-order*.

```scala
  def mixPlayers(players: List[Player]): List[Player] =
  Random shuffle players
```

In particolare, si tratta di un metodo utilizzato per mescolare la lista dei giocatori prima di iniziare la partita

## Utilizzo del paradigma logico

Il team di sviluppo si è posto come obiettivo realizzativo l'uso del paradigma
logico. In fase di progettazione ci si è interrogati sul possibile utilizzo
della programmazione logica all'interno del progetto, scegliendo alcuni metodi
che ben si adattavano alle caratteristiche di *Proloog*.

### Utilizzo di *Prolog* per il lancio dei dadi

Tramite il **TuProlog engine** viene creata una coppia di interi di valore randomico, compreso tra 1 e 6

```prolog
 rollDice(Max, X):-rand_int(Max,N), X is N+1.
```

Il predicato *rollDice* si occupa di generare un numero casuale fra 1 e Max.

### Utilizzo di *Prolog* per i metodi della classe `GameUtils`

L'*engine* di *Prolog* è stato utilizzato anche per risolvere due metodi presenti nella classe `GameUtils`: in
particolare *getNthSlotFromCell* e *GetCoordinateFromPosition*.

Il primo metodo consente di individuare le coordinate dell'n-esima cella in una griglia di X righe e Y colonne.
Questa informazione è utile per rappresentare in modo corretto nell'interfaccia grafica le pedine dei giocatori,
senza che esse si sovrappongano in caso due o più giocatori finiscano sulla stessa casella.
Per ottenere il risultato richiesto, cerca all'interno della griglia le coordinate della cella in posizione N,
ovvero la prima libera.

```prolog
getCellInGrid(N, Y, X, Y1, X1):- R is (N mod Y), R = 0 -> Y1 is Y -1, X1 is N//Y - 1;
Y1 is ((N mod Y)-1), X1 is N//Y.
```

La seconda funzione, invece, permette di trovare le coordinate di una casella data la sua posizione all'interno del
tabellone di gioco, che sarà compresa tra 0 e 63. Le celle sono ordinate a spirale a partire dall'angolo superiore
sinistro, in una griglia 8 x 8.
Queste coordinate vengono utilizzate all'interno della parte di *view* per visualizzare la pedina del giocatore
nella posizione corretta all'interno del tabellone.

```prolog
getCoordinateFromPosition(P,C,Y,X):- P < C -> Y is P, X is 0;
P < C*2 -> Y is C, X is P - C;
P < C*3 -> Y is (C * 3) - P, X is C;
P < (C*4) - 1 -> Y is 0, X is (C*4)-P;
P < (C*5) - 2 -> Y is P - ((C*4) - 1) , X is 1;
P < (C*5) + 3 -> Y is C - 1, X is P -((C + 1)*4);
P =< C*6 -> Y is ((C*6)+2) - P, X is 6;
P < (C**2) - 2 -> Y is 1, X is ((C**2)-P);
P < (C**2)+2 -> Y is P-((C**2)-3), X is 2;
P < (C*8) -2 -> Y is 5 , X is P-(C**2);
P =< C*8 -> Y is ((C*8)+3)-P , X is 5;
P < (C*8) + 3 -> Y is 2 , X is ((C*9)-1)-P;
P < (C*9) - 2 -> Y is P-((C*8)+1) , X is 3;
P < C*9 -> Y is 4 , X is P-((C*8)+2);
Y is 3, X is 4.
```

#### Possible goal

Come risultato del primo goal ottengo SolY = 1 e SolX = 1 (seconda colonna nella seconda riga), mentre il risultato del
secondo è SolY = 6 e SolX = 4 (settima colonna e quinta riga)

-------------------------------------------------

Per integrare *TuProlog* in modo più fluente, si è fatto ricorso ad un metodo che utilizza al suo interno un *engine*
per risolvere le query in input: in questo modo, la soluzione fornita risultava inutilizzabile. Si è reso quindi
necessario
definire metodi utili in fase di ricostruzione del risultato finale, ossia una coppia di interi, oppure un solo intero
nel
caso del lancio dei dadi. Per effettuare il *parsing* del risultato del *Prolog Engine* si è utilizzato il principio
delle *given conversion*, come si può osservare dal listato sottostante, impiegato diverse volte all'interno della
classe
di utilità, per favorire idiomaticità e comprensibilità, oltre a fornire un *adapter* automatico senza dover introdurre
codice *boilerplate*.

## JavaFX e ScalaFX

Per implementare la parte di interfaccia grafica sono state utilizzate le librerie *JavaFX* e *ScalaFX*. un DSL scritto
in Scala che fa da *wrapper* agli elementi di *JavaFX*.

In particolare, si è deciso di generare i vari stage dell'applicazione mediante la creazione di layout in formato
*FXML*:
poichè nella libreria *ScalaFX* non è prevista la gestione di questa funzionalità, si è scelto di integrare quest'ultima
con la libreria *JavaFX*.

*FXML* è un formato *XML* che permette di comporre applicazioni *JavaFX*, separando il codice per la gestione degli
elementi dalla parte di visualizzazione dei layout.

Il componente *View* rappresenta il *Controller* associato al rispettivo layout; il controller può ottenere il
riferimento agli elementi appartenenti all'interfaccia richiamando gli ID specificati nel file *FXML* e mediante il
caricatore,
ossia *FXMLLoader* che ha il compito di istanziare e rendere accessibili gli elementi grafici. Il ruolo del controller
è quello di inizializzare gli elementi della UI e gestirne il loro comportamento.
