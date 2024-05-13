package it.unibo.pps.duckgame.model.specialCell

import it.unibo.pps.duckgame.controller.logic.{EndGameController, LogicController, MovementsController}
import it.unibo.pps.duckgame.controller.view.PlayerMenuController
import it.unibo.pps.duckgame.controller.{Game, GameReader}
import it.unibo.pps.duckgame.model.CellStatus
import it.unibo.pps.duckgame.model.specialCell.SpecialCellType.BLANK

final case class SpecialCellBuilder(number: Int, specialCellType: SpecialCellType, message: String):
  private def jumpToStart(steps: Int): Unit =
    MovementsController.fixedPositionMove(0)
    
  private def jumpToThirtyNine(steps: Int): Unit =
    MovementsController.fixedPositionMove(39)
    
  private def jumpToTwelve(steps: Int): Unit =
    MovementsController.fixedPositionMove(12)  

  private def moveForward(steps: Int): Unit =
    MovementsController.moveWithSteps(steps)

  private def setWinner(steps: Int): Unit =
    EndGameController.setWinner()
    
  private def goInJail(steps: Int): Unit =
    LogicController.playerCantPlay(GameReader.currentPlayerIndex)
    
  private def goInsideWell(steps: Int): Unit =
    LogicController.playerCantPlay(GameReader.currentPlayerIndex)

  private def lockOneTurn(steps: Int): Unit =
    LogicController.lockUnlockTurnPlayer(true)

  private def noAction(steps: Int): Unit = {}

  def build(): SpecialCell =
    val action = specialCellType match
      case SpecialCellType.BRIDGE => jumpToTwelve
      case SpecialCellType.DUCK => moveForward
      case SpecialCellType.FINAL => setWinner
      case SpecialCellType.HOUSE => lockOneTurn
      case SpecialCellType.JAIL => goInJail
      case SpecialCellType.LABYRINTH => jumpToThirtyNine
      case SpecialCellType.SKELETON => jumpToStart
      case SpecialCellType.WELL => goInsideWell
      case SpecialCellType.BLANK => noAction

    new SpecialCell(number, specialCellType, message, action)
