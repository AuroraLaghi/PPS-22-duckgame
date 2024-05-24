package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.model.Player
import scalafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import java.util.Optional

object AlertUtils:

  /** Displays a victory dialog for the winner of the game.
    *
    * @param winner
    *   The player who won the game.
    * @return
    *   An Optional[ButtonType] value representing the button clicked by the
    *   user in the dialog:
    *   - Some(ButtonType.OK) if the user clicked the "OK" button.
    *   - None if the dialog was closed without the user clicking any button.
    */
  def showVictory(winner: Player): Optional[ButtonType] =
    FxmlUtils.showAlert(AlertType.Information)("VITTORIA")(
      s"Complimenti, ${winner.name} è il vincitore!"
    )("")

  /** Displays a warning dialog informing the user that there are not enough
    * players to start the game.
    *
    * @return
    *   An Optional[ButtonType] value representing the button clicked by the
    *   user in the dialog:
    *   - Some(ButtonType.OK) if the user clicked the "OK" button.
    *   -None if the dialog was closed without the user clicking any button.
    */
  def notEnoughPlayersWarning(): Optional[ButtonType] =
    FxmlUtils.showAlert(AlertType.Warning)("Duckgame")(
      "Impossibile iniziare la partita"
    )(
      "É necessario che ci siano almeno due giocatori"
    )

  /** Displays a warning dialog informing the user that a player's name is
    * empty.
    *
    * @return
    *   An Optional[ButtonType] value representing the button clicked by the
    *   user in the dialog:
    *   - Some(ButtonType.OK) if the user clicked the "OK" button.
    *   - None if the dialog was closed without the user clicking any button.
    */
  def emptyPlayerNameWarning(): Optional[ButtonType] =
    FxmlUtils.showAlert(AlertType.Warning)("Duckgame")(
      "Impossibile aggiungere il giocatore"
    )(
      "Il campo del nome del giocatore è vuoto"
    )

  /** Displays a warning dialog informing the user that both players are stuck
    * in the well or prison and that they need to return to the main menu.
    *
    * @return
    *   An Optional[ButtonType] value representing the button clicked by the
    *   user in the dialog: * Some(ButtonType.OK) if the user clicked the "OK"
    *   button. * None if the dialog was closed without the user clicking any
    *   button.
    */
  def gameLockedWarning(): Optional[ButtonType] =
    FxmlUtils.showAlert(AlertType.Warning)("Duckgame")(
      "Entrambi i giocatori sono bloccati nel pozzo o nella prigione"
    )(
      "Premere ok per tornare alla schermata iniziale"
    )

  def exchangePlayerInWellOrJailInfo(name: String): Optional[ButtonType] =
    FxmlUtils.showAlert(AlertType.Information)("Duckgame")(
      "Il giocatore " + name + " è bloccato nel pozzo/prigione\nSe sulla casella era già presente un altro " +
        "giocatore,\nora può riprendere a giocare"
    )(
      "Premere ok per continuare"
    )
