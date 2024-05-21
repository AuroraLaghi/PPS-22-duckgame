# 5. Implementazione
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
una funzione che assuma parametri multipli può essere tradotta nella
valutazione di una sequenza di funzioni.

Questo meccanismo consente di applicare il principio **DRY** (Don't Repeat Yourself),
favorendo così il riuso di codice: infatti, quando una funzione è *curried*, è
possibile applicare parzialmente la funzione per poterla utilizzare in più
punti del codice.

All'interno del package *utils* si è cercato di utilizzare questo meccanismo ove possibile, il seguente listato è un esempio di utilizzo di questo meccanismo

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

Per integrare *TuProlog* in modo più fluente, si è fatto ricorso ad un metodo che utilizza al suo interno un *engine*
per risolvere le query in input.

```prolog
    case class PrologEngine(theory: Theory) extends Engine:
      private val engine: Prolog = new Prolog
      engine.setTheory(theory)

      override def getCellInGrid(cell: Int)(cols: Int)(rows: Int): (Int, Int) =
        val query: String = "getCellInGrid(" + cell + ", " + cols + ", " + rows + ", X, Y)"
        solve(query)

      override def getFreeSlotInCell(position: Int)(cis: Int): (Int, Int) =
        val query: String = "getCoordinateFromPosition(" + position + ", " + cis + ", X, Y)"
        solve(query)

      override def randomDice(maxValue: Int): (Int, Int) =
        val query: String = "rollDice(" + maxValue + ", X)"
        (solve(query), solve(query))

      override def solve: Term => SolveInfo = term => engine solve term
```

In questo modo, la soluzione fornita risultava inutilizzabile. Si è reso quindi necessario definire metodi utili in fase
di ricostruzione del risultato finale, ossia una coppia di interi, oppure un solo intero
nel caso del lancio dei dadi. Per effettuare il *parsing* del risultato del *Prolog Engine* si è utilizzato il principio
delle *given conversion*, come si può osservare dal listato sottostante, impiegato diverse volte all'interno della
classe di utilità, per favorire idiomaticità e comprensibilità, oltre a fornire un *adapter* automatico senza dover
introdurre codice *boilerplate*.

```prolog
    object PrologSolution:
        given Conversion[SolveInfo, (Int, Int)] = e => colRowFromTerm(e.getTerm("X"))(e.getTerm("Y"))
        given Conversion[SolveInfo, Int] = e => intFromTerm(e.getTerm("X"))
    
        private def colRowFromTerm(termX: Term)(termY: Term): (Int, Int) =
          (termX.toString.toDouble.toInt, termY.toString.toDouble.toInt)
    
        private def intFromTerm(term: Term): Int =
          term.toString.toInt
```

### Utilizzo di *Prolog* per il lancio dei dadi

Tramite il **TuProlog engine** viene creata una coppia di interi di valore randomico, compreso tra 1 e 6

```prolog
% Roll one dice and return the result
% Max = seed, X = random integer between 1 and 6

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
 % Return the coordinates of the N-th cell in a grid of given rows and columns
 % N = Nth cell, Y = size of columns in grid, X = size of rows in grid
 % Y1 = column in given position, X1 = row in given position

getCellInGrid(N, Y, X, Y1, X1):- R is (N mod Y), R = 0 -> Y1 is Y -1, X1 is N//Y - 1;
Y1 is ((N mod Y)-1), X1 is N//Y.
```

La seconda funzione, invece, permette di trovare le coordinate di una casella data la sua posizione all'interno del
tabellone di gioco, che sarà compresa tra 0 e 63. Le celle sono ordinate a spirale a partire dall'angolo superiore
sinistro, in una griglia 8 x 8.
Queste coordinate vengono utilizzate all'interno della parte di *view* per visualizzare la pedina del giocatore
nella posizione corretta all'interno del tabellone.

```prolog
% Return the coordinates of a grid of cell given player's position
% P = position, C = CELLS IN SIDE (default 7)
% Y = column of N cell, X = row of N cell

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

<div align="center">
  <img src="..\img\getCellInGrid.png" />
  <img src="..\img\getCoordinateFromPosition.png"  />
</div>



Come risultato del primo goal ottengo SolY = 1 e SolX = 1 (seconda colonna nella seconda riga), mentre il risultato del
secondo è SolY = 6 e SolX = 4 (settima colonna e quinta riga)

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

## Testing

Per effettuare il testing delle funzionalità principali del programma si è scelto di adottare la modalità **TDD** (Test
Driven Development): questa strategia prevede di scrivere dapprima il codice di test, indicando il comportamento
corretto
della funzionalità che si vuole testare, scrivendone in un secondo tempo il codice di produzione, affinchè i test
scritti
in precedenza passino correttamente. Una volta che il codice di produzione è stato scritto ed i test hanno esito
positivo,
si può procedere con il refactoring e miglioramento della soluzione ottenuta.

Il TDD, dunque, si compone di tre diverse fasi che si alternano: red, green e refactor. Nella prima fase, è stato
scritto
solamente il codice di testing e, di conseguenza, i test non possono passare, dato che il codice di produzione non è
presente.
Nella fase green, invece, si cerca di verificare tutti i test scritti in precedenza, implementando del codice che li
verifichi. Infine, nell'ultima fase, quella di refactor, si migliora il codice di produzione scritto nella fase
precedente.

Il team, per lo sviluppo dell'applicazione, ha deciso di adottare la pratica di Continuos Integration, realizzando due
flussi di lavoro: il primo dedicato all'esecuzione dei test su SO differenti (Windowd, Linux e MacOS); il secondo,
invece, mirato a determinare la copertura ottenuta dai test implementati.

Per il testing degli aspetti della *view* sono stati svolti test manuali: difatti, risultava complicato verificare condizioni limite tramite test automatici, i quali non possono essere considerati esaustivi nella verifica degli aspetti di interazione con l'utente.

### Utilizzo di ScalaTest

Per testare le funzionalità legate alla logica di business dell'applicazione sono state realizzare diverse suite di test
con l'utilizzo della libreria *ScalaTest*.

Tutte le classi realizzare estendono *AnyFlatSpec* ed i test seguono lo stile mostrato:

```scala
"Current player starting position" should "be 0" in {
  val DEFAULT_STARTING_POSITION = 0
  GameReader.currentPlayer.actualPosition shouldBe DEFAULT_STARTING_POSITION
}
```

Per verificare determinate condizioni come, per esempio, l'uguaglianza, minoranza o maggioranza, sono stati utilizzati
i *matchers* di *ScalaTest*: in particolare, se la classe di test estende il trait `Matchers`, si ha la possibilità di
utilizzare all'interno dei test keywords come *shouldBe*, *equal*, *shouldEqual*... che consentono di verificare le
condizioni espresse

Per verificare che alcuni metodi non accettino parametri sbagliati, si è utilizzato il costrutto *an [Exception] should
be
thrownBy* come nell'esempio riportato:

```scala
 "Method getNthCellInGridWithStartingPos" should "throws exception when are given wrong arguments" in {
  val DEFAULT_STARTING_VALUE = 1
  val ILLEGAL_GRID_SIZE: (Int, Int) = (0, 1)
  an[NoSolutionException] should be thrownBy {
    GameUtils.getNthSlotFromCell(DEFAULT_STARTING_VALUE)(ILLEGAL_GRID_SIZE)
  }
}
```

### Coverage

Come detto nei capitoli precedenti, il team di sviluppo ha realizzato anche un flusso di lavoro dedicato alla *coverage* dei test, in modo tale da poter verificare la copertura ottenuta ogni qual volta vengano aggiunti o modificati dei test.

La *code coverage* si riferisce alla quantità di istruzioni di codice eseguite durante l'esecuzione dei test. 
Bisogna però tenere presente che una copertura del 100% non garantisce che il testing implementato copra tutti gli scenari possibili: infatti, l'obiettivo che il team si è posto non è quello di raggiungere un *coverage* totale, bensì quello di testare funzioni mirate.

In particolare, per ottenere i dettagli relativi alla copertura dei test, si è scelto di utilizzare il *tool* [_JaCoCo_](https://www.eclemma.org/jacoco/)

<div align="center">
  <img src="..\img\coverage.png" />
</div>

Come si denota dalla figura sopra, la *coverage* finale ottenuta è del 55% su un totale di 84 test effettuati.

Gli element per i cui si ha una *coverage* maggiore sono quelli che fanno riferimento al *model* e alla parte logica dell'applicazione, mentre si ha una *coverage* vicina allo 0 per gli elementi della *view* i quali, come spiegato poco sopra, sono stati testati tramite test manuali.

## Suddivisione del lavoro
A seguire, ogni membro del gruppo ha descritto le parti di codice implementate o effettuate in collaborazione.

foldLeft => GameBoardView

### Francesca Frattini

#### LogicController

La classe `LogicController` può essere considerata lo *scheletro logico* dell'intera applicazione.
Tramite questa classe, definita come un *object*, è possibile gestire tutti gli aggiornamenti relativi alla partita in corso: movimenti dei giocatori, designazione dei turni...
Naturalmente ci sono altre classi che affiancano `LogicController` per la gestione della partita: `EndGameController` per il controllo della presenza di un vincitore, `MovementsController` per la gestione dei movimenti all'interno del tabellone e il controllo delle caselle speciali e `PlayerController` per l'aggiornamento della posizione dei giocatori.

All'interno della classe sono state utilizzati meccanismi della programmazione funzionale, come ad esempio l'uso della notazione *tailrec* per la ricorsione di coda, visibile nel listato sottostante.

```scala
  @tailrec
  private def nextPlayerFree(): Unit =
    GameReader.nextPlayer()
    GameReader.currentPlayer.isLocked match
      case true =>
        LogicController.lockUnlockTurnPlayer(false)
        nextPlayerFree()
      case false
          if GameReader.currentPlayerIndex != GameReader.playerInWell()
            && GameReader.currentPlayerIndex != GameReader.playerInJail() =>
      case _ => nextPlayerFree()
```

#### GameBoardView

La classe `GameBoardView` rappresenta un classe di *view*, infatti estende l'interfaccia `Initializable` di *FXML*.
All'interno di questa classe sono gestiti gli elementi che compongono l'interfaccia grafica della schermata relativa alla pagina principale dell'applicazione, ovvero quella contenente la *GameBoard*, il tabellone di gioco.
Gli ID assegnati agli elementi presenti nel relativo file *FXML* sono richiamati utilizzando delle variabili con lo stesso nome.
Per gestire il movimento delle pedine, il tabellone è stato suddiviso in una griglia 8x8, le cui celle sono a loro volta divise in griglie 3x2, in modo tale che su ogni casella possono esserci contemporaneamente fino a 6 pedine, il numero massimo di giocatori.

È inoltre presente una finestra nel menù di sinistra che visualizza tutti i movimenti effettuati dal giocatore corrente, oltre alla tabella contenente tutti i giocatori attivi con le relative pedine.

<div align="center">
  <img src="..\img\screenshot.png" />
</div>


### Aurora Laghi


L'implementazione di `Dice` è stata possibile grazie al **pattern Singleton** che implica l'utilizzo di un costruttore privato vietando di creare direttamente istanze della classe stessa. Inoltre, si è reso necessario l'utilizzo del rispettivo **companion object** (`object Dice`) che fornisce il metodo factory `apply` e il metodo statico`rollDice`. In egual modo si è deciso di realizzare anche `Player.scala`.

Con la creazione di `Parser.scala` si può notare l'impiegato del trait `Parser[T]` e del **pattern Strategy** rappresentato da `object SpecialCellParser`. In particolare, *parser* definisce un'interfaccia generica per il parsing, consentendo flessibilità nella gestione dei diversi tipi di dati mentre l'implementazione concreta del metodo parse, resa possibile dallo strategy, fornisce una logica specifica per i rispettivi tipi di dati. Si tratta di un approvvio che definisce riutilizzabilità e menutenibilità del codice.

In `PrologGameUtils.scala` si osservano due importanti pattern di design: il **pattern Facade** e il **pattern Adapter**.

1. Pattern Facade:
l'object PrologGameUtils funge da **facciata** per semplificare l'interazione con le funzionalità del motore Prolog. Offre metodi di alto livello come `getCellInGrid`, `getFreeSlotInCell` e `randomDice`, nascondendo la complessità di costruire query Prolog e processare le soluzioni. Fra i vantaggi principali è possibile notare l'interfaccia che semplifica l'utilizzo del motore Prolog da parte di sviluppatori che non hanno familiarità con il linguaggio Prolog. Il codice risulta più leggibile e comprensibile, concentrandosi sulle funzionalità desiderate piuttosto che sui dettagli di implementazione. Infine, nascondendo i dettagli di implementazione del motore Prolog, la facciata protegge il codice applicativo da eventuali modifiche future nel motore.

3. Pattern Adapter:
per integrare TuProlog in modo più fluido, si è utilizzato un metodo che sfrutta un *engine* interno per risolvere le query in input. Tuttavia, la soluzione ottenuta non era direttamente utilizzabile nell'applicazione. Per risolvere questo problema, sono stati definiti metodi di utilità per ricostruire il risultato finale (una coppia di interi o un singolo intero nel caso del lancio dei dadi). Questi metodi utilizzano il principio delle conversioni implicite (definite con l'`object given`) per facilitare il parsing del risultato del motore Prolog. Si noti che le conversioni implicite permettono di adattare il formato del risultato Prolog alle esigenze specifiche dell'applicazione, oltre ad evitare la necessità di scrivere codice *boilerplate* per la conversione manuale dei risultati.

Con `SpecialCellBuilder.scala` è possibile vedere l'applicazione del **pattern Builder**. Questo pattern conferisce maggiore flessibilità con la possibilità di aggiungere nuovi tipi di caselle speciali e la loro relativa azione senza modifcare la logica principale, trattandosi di una classe *final* cioè immutabile viene conferita maggiore immutabilità all'applicativo e per concludere aiuta a separare la creazione degli oggetti dalla logica interna migliorando la leggibilità del codice.

