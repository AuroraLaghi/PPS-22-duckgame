# Design dettagliato 
In questo capitolo verrà descritta nel dettaglio l'architettura del progetto, analizzando i
principali componenti e le rispettive caratteristiche. Oltre a quelli già citati, alcuni dei quali
sono divisi in sotto-moduli, è presente 1 package ulteriore contenente vari oggetti di utility.

Nella figura sottostante è schematizzata la gerarchia su cui sono stati organizzati i vari moduli
del progetto.

<img src="../img/design_dettaglio.svg" width="500"/>

A seguire l'analisi di ogni singolo modulo coi rispettivi sotto-moduli.

## Controller
In figura viene mostrato il modulo *Controller* insieme ai rispettivi sotto-moduli *logic* e *view*.

<img src="../img/diagramma_controller.svg" />

### Sotto-modulo *view*
Al suo interno sono state raggruppate le logiche di interazione tra View e Model rispettando il pattern MVC attraverso le classi seguenti.

#### StartMenuController
Si occupa della gestione della comunicazione con la con la vista iniziale `StartMenuView` offrendo due comandi:
- `playGame`: passa alla schermata di configurazione `PlayersMenuView`
- `closeGame`: termina il gioco 

#### PlayerMenuController
Gestisce la comunicazione con la schermata `PlayersMenuView` offrendo una serie di metodi fra cui:
- `addPlayer`: aggiunge un nuovo giocatore
- `removePlayer`: rimuove un giocatore
- `availableToken`: restituisce la lista dei token ancora disponibili per i nuovi giocatori
  
#### GameBoardController
Ha il compito di gestire la comunicazione con la schermata di gioco `GameBoardView` esponendo metodi come:
- `currentPlayerQuit`: viene chiamato quando un giocatore abbandona la partita
- `endTurn`: indica la terminazione del turno di un giocatore
- `throwDice`: rappresenta il lancio dei dadi
- `movePlayer`: prende il ingresso la coppia di dadi lanciata e sposta il relativo giocatore di un numero di celle pari alla somma dei dadi
- `checkVictory`: dichiara se c'è un vincitore in quel momento del gioco restituendo un booleano che indica se l'operazione è andata a buon fine o meno 
- `showGameLocked`: gestisce lo scenario in cui il gioco è bloccato
- `playerLockedAlert`: prende in ingresso il giocatore attuale e informa che questo è bloccato su una casella speciale (Pozzo o Prigione), se era presente già un altro giocatore sulla stessa cella allora questo sarà libero di riprendere la partita
 
### Sotto-modulo *logic*
Qui è possibile trovare parte della logica di gioco.

#### LogicController

#### MovementsController

#### PlayerController
Object che si occupa di controllare i movimenti dei giocatori esponendo i metodi:
- `updatePlayerWith`: aggiorna un giocatore esistente nella lista dei giocatori in gioco, prendendo in ingresso l'index del giocatore che si desidera aggiornare e l'oggetto playerUpdated che contiene le informazioni da associare al giocatore indicato
- `playerOnSpecialCell`: gestisce l'azione innescata quando un giocatore atterra su una casella speciale durante una partita, prendendo in ingresso la SpecialCell (cella speciale su cui il giocatore atterra) e gli Steps (numero di passi che il giocatore ha compiuto prima per atterrare su quella cella)

#### EndGameController
È un object che si occupa della logica di fine gioco, attraverso metodi come:
- `checkVictoryForSurrender`: controlla se è rimasto un solo giocatore in gioco e nel caso lo setta come vincitore in `GameReader`
- `isGameLocked`: controlla se il gioco si trova in uno stato di 'stallo', cioè rimangono in gioco solo due giocatori ed entrambi sono bloccati sulle caselle speciali, Pozzo e Prigione.

### Game
Questo object modella il concetto di “gioco” ed è accessibile solamente all’interno del modulo *Controller*. In particolare, viene richiamato e modifficato solamente dal `GameReader`. Espone i seguenti metodi:
- `players`: lista dei giocatori
- `availableTokens`: lista delle pedine ancora disponibili 
- `gameBoard`: tabellone di gioco
- `winner`: eventuale vincitore del gioco
- `palyerInJail`: giocatore sulla casella speciale della prigione
- `playerInWell`: giocatore sulla casella speciale del pozzo
- `reset`: resetta lo stato di gioco (in particolare setta i valori di default `currentPlayer`, `players`, `winner`, `gameBoard`, `firstRound`, `playerInWell`, `playerInJail`, `availableTokens`

### GameReader
Tramite questo object è possibile leggere tutti i dati del `Game`, aggiornando le viste senza infrangere la logica del pattern MVC:
- `currentPlayer`:
- `canStartGame`:
- `canAddPlayer`:
- `checkVictory`:
- `playerGoesInWellOrJail`:
- `afterPlayerQuit`:
- `nextPlayer`:

## Model

## View

## Utils
