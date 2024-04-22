package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.controller
import it.unibo.pps.duckgame.model.CellStatus.SPECIAL_CELL
import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellType}
import it.unibo.pps.duckgame.model.specialCell.SpecialCellType.JAIL
import it.unibo.pps.duckgame.model.{Cell, CellStatus, Dice, GameBoard, Player}
import it.unibo.pps.duckgame.utils.resources.FxmlResources
import it.unibo.pps.duckgame.utils.{AlertUtils, FxmlUtils, GameUtils}

import scala.annotation.tailrec
import scala.util.Try

/** Controller for the [[it.unibo.pps.duckgame.view.GameBoardView]] */
object GameBoardController:
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

  private def movePlayer(dicePair: (Int, Int)): Unit =
    if Game.firstRound then
      MovementsController.firstRoundMoves(dicePair)
    else
      MovementsController.standardMove(dicePair)
    checkSpecialCell(dicePair)

  private def checkSpecialCell(dicePair: (Int, Int)): Unit =
    val steps = dicePair._1 + dicePair._2
    LogicController.checkCellType match
      case CellStatus.SPECIAL_CELL =>
        if !(Game.firstRound && steps == 9) then
          val specialCell = GameUtils.getSpecialCellFromPlayerPosition()
          specialCell.foreach(PlayerController.playerOnSpecialCell(_, steps))
      case CellStatus.STANDARD_CELL =>
