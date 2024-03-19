# 2. Requisiti

In questa sezione saranno discussi i requisiti dell'applicazione, partendo da quelli di business per arrivare a quelli
funzionali e non funzionali.

## 2.1 Requisiti di business

I requisiti di business richiesti sono:

- Creazione di un sistema che consenta di giocare al _Gioco dell'oca_ seguendo le regole di gioco originali.
- Il sistema potrà supportare partite interattive con un numero variabile di giocatori (minimo 2), in modalità _hotseat_,
dove i giocatori si alternano sul medesimo dispositivo.

## 2.2 Requisiti utente

L'utente che farà utilizzo dell'applicazione potrà:

- Interagire con il sistema attraverso l'interfaccia grafica:
  - Inserire il proprio nome e scegliere una pedina segnaposto 
  - Definire il numero di giocatori 
  - Avviare una partita
- Durante lo svolgimento di una partita:
  - lanciare i dadi
  - abbandonare la partita

## 2.3 Requisiti funzionali

Fra i requisiti funzionali, sono stati indivuati:

- Il gioco di svolge interativamente mantenendo corretto ordine dei giocatori durante i turni
- Il campo di gioco consiste in un tabellone a spirale, composto da 63 caselle
- Ciascun giocatore è rappresentato da una pedina all'interno del tabellone
- Le pedine si spostano lungo le caselle del tabellone
- Un giocatore si muove lanciando entrambi i dadi. Il numero ottenuto, compreso tra 2 e 12, indica di quante caselle avanzare. In caso di due numeri uguali, il giocatore ripete il turno
- Esistono diversi tipi di caselle:
  - Caselle d'oca, il giocatore che si muove su una casella dell’oca si muove di un numero di caselle pari a quelle di cui si è appena spostato
  - Ponte, si ripete il movimento
  - Casa, si rimane fermi per tre turni
  - Prigione, si rimane fermi finchè non si ottiene un doppio coi dadi oppure un giocatore finisce sulla stessa casella
  - Pozzo, si viene eliminati dalla partita
  - Labirinto, torna indietro fino ad una determinata casella
  - Scheletro, torna all'inizio del tabellone
- Per vincere è necessario arrivare sull’ultima casella con un lancio esatto, terminando il proprio movimento sulla casella 63
- Se si ottiene un numero più alto di quello necessario per raggiungere l’ultima casella, dopo aver raggiunto la casella 63 si dovrà tornare indietro
- Se vengono elimitati tutti gli avversari vince il giocatore rimasto in partita

## 2.4 Requisiti non funzionali

Sono stati identificati i seguenti requisiti non funzionali:

- L'applicazione deve essere _cross-platform_, cioè eseguibile su SO Windows, Linux e MacOS, o comunque qualsiasi sistema operativo in grado di supportare _Java Runtime Environment_ versione 17 o successive.
- Il sistema deve essere responsivo alle azioni del giocatore ed in grado di gestire il carico di lavoro necessario per gestire una partita
- L'applicazione deve essere stabile e affidabile, evitando crash o errori critici
- Il sistema deve essere intuitivo e semplice da utilizzare con un'interfaccia utente chiara ed oragnizzata. I giocatori devono essere in grad di 
interagire con il gioco senza difficoltà e comprenderne gli avvenimenti
- L'applicazione deve essere scalabile in seguito all'aggiunta di nuovi componenti
- La documentazione che accompagna il sistema deve essere chiara e completa, esponendo le regole del gioco e le funzionalità del software

## 2.5 Requisiti implementativi

Utilizzo di:

- _Scala 3.x_ 
- _JDK 17+_
- _tuProlog 4.x_
- _ScalaTest_ TODO

## 2.5 Requisiti opzionali

Sono stati individuati dei requisiti non obbligatori del progetto ma che contribuirebbero ad accrescerne il valore:

- Possibilità di effettuare una partita in modalità _playerVSbot_
- Scelta tabellone di gioco tra quelli proposti
- Aggiunta della possibilità di mettere in pausa la partita e salvarne i progressi