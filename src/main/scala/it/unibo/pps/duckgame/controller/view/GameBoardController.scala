package it.unibo.pps.duckgame.controller.view

import it.unibo.pps.duckgame.controller
import it.unibo.pps.duckgame.controller.logic.{EndGameController, LogicController, MovementsController}
import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.model.CellStatus.SPECIAL_CELL
import it.unibo.pps.duckgame.model.specialCell.SpecialCellType.JAIL
import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellType}
import it.unibo.pps.duckgame.model.*
import it.unibo.pps.duckgame.utils.resources.FxmlResources
import it.unibo.pps.duckgame.utils.{AlertUtils, AnyOps, FxmlUtils, GameUtils}
import it.unibo.pps.duckgame.view.GameBoardView

import scala.annotation.tailrec
import scala.util.Try

/** Controller for the [[it.unibo.pps.duckgame.view.GameBoardView]] */
object GameBoardController:
  private var _view: Option[GameBoardView] = None

  def view: Option[GameBoardView] = _view

  def view_=(value: GameBoardView): Unit =
    _view = Some(value)

  /** Called when a player quits the game */
  def currentPlayerQuit(): Unit =
    LogicController.currentPlayerQuit()
    if EndGameController.checkVictoryForSurrender() then showVictory()

  /** Called when a player ends its turn */
  def endTurn(): Unit =
    LogicController.endTurn()

  /** Method that throws two dices and return their result
    *
    * @return
    *   dices' values
    */
  def throwDice(): (Int, Int) =
    val dicePair = Dice().roll()
    movePlayer(dicePair)
    dicePair

  /** Prints victory message with the name of the winner, then closes the application */
  def showVictory(): Unit =
    GameReader.winner.foreach(w =>
      AlertUtils.showVictory(w)
      LogicController.newGame()
      FxmlUtils.changeScene(FxmlResources.START_MENU.path)
    )

  def checkVictory(): Boolean =
    EndGameController.checkWinner()

  def movePlayer(dicePair: (Int, Int)): Unit =
    LogicController.movePlayer(dicePair)

  def showGameLocked(): Unit =
    AlertUtils.gameLockedWarning()
    LogicController.newGame()
    FxmlUtils.changeScene(FxmlResources.START_MENU.path)

  def viewPlayerMovement(message: String): Unit =
    if _view.isDefined then _view.get.playerMovement(message)

  /** Emits an alert telling that current player got locked If the special cell was already occupied, tells that old
    * player locked now is unlocked
    *
    * @param player
    *   player that ended in jail or well cell
    */
  def playerLockedAlert(player: Player): Unit =
    if _view.isDefined then
      player.actualPosition match
        case 31 =>
          if GameReader.playerInWell() =/= -1 then
            AlertUtils.exchangePlayerInWellOrJailInfo(player.name, GameReader.players(GameReader.playerInWell()).name)
        case 52 =>
          if GameReader.playerInJail() =/= -1 then
            AlertUtils.exchangePlayerInWellOrJailInfo(player.name, GameReader.players(GameReader.playerInWell()).name)
        case _ => AlertUtils.exchangePlayerInWellOrJailInfo(player.name, "")
