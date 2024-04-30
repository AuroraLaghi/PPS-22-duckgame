package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.controller
import it.unibo.pps.duckgame.model.CellStatus.SPECIAL_CELL
import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellType}
import it.unibo.pps.duckgame.model.specialCell.SpecialCellType.JAIL
import it.unibo.pps.duckgame.model.{Cell, CellStatus, Dice, GameBoard, Player}
import it.unibo.pps.duckgame.utils.resources.FxmlResources
import it.unibo.pps.duckgame.utils.{AlertUtils, FxmlUtils, GameUtils}
import it.unibo.pps.duckgame.view.GameBoardView

import scala.annotation.tailrec
import scala.util.Try

/** Controller for the [[it.unibo.pps.duckgame.view.GameBoardView]] */
object GameBoardController:
  private var _view: GameBoardView = _

  def view: GameBoardView = _view

  def view_=(value: GameBoardView) : Unit =
    _view = value

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
    if GameReader.isFirstRound then
      MovementsController.firstRoundMoves(dicePair)
    else
      MovementsController.standardMove(dicePair)
      
  def showGameLocked(): Unit =
    AlertUtils.gameLockedWarning()
    LogicController.newGame()
    FxmlUtils.changeScene(FxmlResources.START_MENU.path)

  def viewPlayerMovement(message: String): Unit =
    if _view != null then
      _view.playerMovement(message)
