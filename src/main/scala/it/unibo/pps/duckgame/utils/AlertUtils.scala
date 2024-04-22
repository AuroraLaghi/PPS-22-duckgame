package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.model.Player
import scalafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType

import java.util.Optional

object AlertUtils:

  def showVictory(winner: Player): Optional[ButtonType]=
    FxmlUtils.showAlert(AlertType.Confirmation, "VITTORIA", s"Complimenti, ${winner.name} è il vincitore!", "")

  def notEnoughPlayersWarning(): Optional[ButtonType]=
    FxmlUtils.showAlert(AlertType.Warning, "Duckgame", "Impossibile iniziare la partita", "É necessario che ci siano almeno due giocatori")

  def emptyPlayerNameWarning(): Optional[ButtonType]=
    FxmlUtils.showAlert(AlertType.Warning, "Duckgame", "Impossibile aggiungere il giocatore", "Il campo del nome del giocatore è vuoto")
