package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.model.Player
import scalafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType

import java.util.Optional

object AlertUtils:

  def showVictory(winner: Player): Optional[ButtonType] =
    FxmlUtils.showAlert(AlertType.Information, "VITTORIA", s"Complimenti, ${winner.name} è il vincitore!", "")

  def notEnoughPlayersWarning(): Optional[ButtonType] =
    FxmlUtils.showAlert(
      AlertType.Warning,
      "Duckgame",
      "Impossibile iniziare la partita",
      "É necessario che ci siano almeno due giocatori"
    )

  def emptyPlayerNameWarning(): Optional[ButtonType] =
    FxmlUtils.showAlert(
      AlertType.Warning,
      "Duckgame",
      "Impossibile aggiungere il giocatore",
      "Il campo del nome del giocatore è vuoto"
    )

  def gameLockedWarning(): Optional[ButtonType] =
    FxmlUtils.showAlert(
      AlertType.Warning,
      "Duckgame",
      "Entrambi i giocatori sono bloccati nel pozzo o nella prigione",
      "Premere ok per tornare alla schermata iniziale"
    )
    
  def exchangePlayerInWellOrJailInfo(newName: String, oldName: String): Optional[ButtonType] =
    var message = ""
    oldName match
      case "" => message = message.concat("Il giocatore " + newName + " è bloccato nel pozzo/prigione")
      case _ => message = message.concat("Il giocatore " + newName + " è bloccato nel pozzo/prigione e quindi " + oldName
        + " può riprendere il gioco")
    FxmlUtils.showAlert(
      AlertType.Information,
      "Duckgame",
      message,
      "Premere ok per tornare al gicoo"
    )  
