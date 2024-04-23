package it.unibo.pps.duckgame.model.specialCell

import it.unibo.pps.duckgame.controller.{EndGameController, Game, GameReader, MovementsController, PlayerMenuController}
import it.unibo.pps.duckgame.model.CellStatus

final case class SpecialCellBuilder(number: Int, specialCellType: SpecialCellType):
  private def jumpToStart(steps: Int): Unit =
    MovementsController.fixedPositionMove(0)
    
  private def jumpToThirtyNine(steps: Int): Unit =
    MovementsController.fixedPositionMove(39)
    
  private def jumpToTwelve(steps: Int): Unit =
    MovementsController.fixedPositionMove(12)  

  private def moveForward(steps: Int): Unit =
    MovementsController.specialCellMove(steps)

  private def setWinner(steps: Int): Unit =
    EndGameController.setWinner()
    
  private def goInJail(steps: Int): Unit =
    MovementsController.playerGoToJail(GameReader.currentPlayerIndex)  
    
  private def goInsideWell(steps: Int): Unit =
    MovementsController.playerGoIntoWell(GameReader.currentPlayerIndex)  

  private def noAction(steps: Int): Unit =
    println("no action")

  def build(): SpecialCell =
    val action = specialCellType match
      case SpecialCellType.BRIDGE => jumpToTwelve
      case SpecialCellType.DUCK => moveForward
      case SpecialCellType.FINAL => setWinner
      case SpecialCellType.HOUSE => noAction
      case SpecialCellType.JAIL => goInJail
      case SpecialCellType.LABYRINTH => jumpToThirtyNine
      case SpecialCellType.SKELETON => jumpToStart
      case SpecialCellType.WELL => goInsideWell

    new SpecialCell(number, specialCellType, action)
