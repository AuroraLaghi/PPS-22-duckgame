# 6. Retrospettiva

## Sviluppo con metodologia SCRUM-inspired

Come descritto nel capitolo [1](01-sviluppo.md), il progetto è stato sviluppato utilizzando un approccio ispirato alla metodologia SCRUM. In questo contesto, il team ha adottato un ciclo di sprint bisettimanali con l'obiettivo di completare diversi _product backlog_ in grado di fornire risultati tangibili per l'utente finale.

## *Definition of Done*

Prima di definire i _product backlog_ di prodotto e di avviare gli sprint, il team di lavoro ha concordato una _"definition of Done"_ per ogni task. In sostanza, è stato stabilito il criterio per determinare quando un prodotto può essere considerato effettivamente completato.

Un prodotto si considera completato quando:
- Tutti i task individuati per la sua realizzazione sono stati portati a termine.
- Il codice di implementazione è stato adeguatamente testato (con esito positivo).
- La documentazione del codice è stata realizzata utilizzando _Scala doc_.

Le seguenti sezioni riassumono le diverse iterazioni di sviluppo svolte dal team, considerando anche una fase iniziale di avvio del progetto in cui sono stati definiti i requisiti e impostato l'ambiente di lavoro.

Molte delle informazioni relative allo sviluppo del progetto sono disponibili nei file relativi al processo di sviluppo. In particolare, gli sprint e i backlog di prodotto sono stati documentati nella directory `/process` del progetto.

## Avviamento

Nella prima fase del processo di sviluppo del progetto, i membri del gruppo si sono occupati di definire i requisiti dell'applicativo da realizzare.
In particolare, sono stati definiti i requisiti di business, utente, funzionali, non funzionali e implementativi che l'applicazione deve rispettare.

A seguire, il gruppo ha definito l'ambiente di lavoro per il progetto, impegandosi a creare diverse directories per contenere i files secondo la logica MVC. In conclusione, sono stati impostati i flussi di lavoro per il processo di _Continuous Integration_.

Per la gestione del _repository_, si è impiegata la metodologia _Git flow_, quindi è stato creato il branch principale `master` e il branch di sviluppo `develop`, inoltre per ogni _feature_ è stato creato un branch per la rispettiva implementazione.
