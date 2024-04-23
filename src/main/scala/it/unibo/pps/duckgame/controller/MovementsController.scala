package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.CellStatus
import it.unibo.pps.duckgame.utils.GameUtils

object MovementsController:

  def firstRoundMoves(dicePair: (Int, Int)): Unit = dicePair match
    case (6, 3) | (3, 6) if GameReader.currentPlayer.actualPosition == 0 => LogicController.moveCurrentPlayer(26)
    case (5, 4) | (4, 5) if GameReader.currentPlayer.actualPosition == 0 => LogicController.moveCurrentPlayer(53)
    case _ => LogicController.moveCurrentPlayer(dicePair._1 + dicePair._2)

  def standardMove(dicePair: (Int, Int)): Unit =
    LogicController.moveCurrentPlayer(dicePair._1 + dicePair._2)

  def specialCellMove(steps: Int): Unit =
    LogicController.moveCurrentPlayer(steps)
    LogicController.checkCellType match
      case CellStatus.SPECIAL_CELL =>
        val specialCell = GameUtils.getSpecialCellFromPlayerPosition
        specialCell.foreach(PlayerController.playerOnSpecialCell(_, steps))
      case CellStatus.STANDARD_CELL =>
    
  def fixedPositionMove(position: Int): Unit =
    LogicController.setNewPositionOfCurrentPlayer(position)

  def playerGoToJail(player: Int): Unit =
    Game.playerInJail = player

  def playerGoIntoWell(player: Int): Unit =
    Game.playerInWell = player