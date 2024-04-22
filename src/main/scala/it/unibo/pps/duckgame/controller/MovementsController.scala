package it.unibo.pps.duckgame.controller

object MovementsController:

  def firstRoundMoves(dicePair: (Int, Int)): Unit = dicePair match
    case (6, 3) | (3, 6) if GameReader.currentPlayer.actualPosition == 0 => LogicController.moveCurrentPlayer(26)
    case (5, 4) | (4, 5) if GameReader.currentPlayer.actualPosition == 0 => LogicController.moveCurrentPlayer(53)
    case _ => LogicController.moveCurrentPlayer(dicePair._1 + dicePair._2)

  def standardMove(dicePair: (Int, Int)): Unit =
    LogicController.moveCurrentPlayer(dicePair._1 + dicePair._2)

  def specialCellMove(steps: Int): Unit =
    LogicController.moveCurrentPlayer(steps)
    
  def fixedPositionMove(position: Int): Unit =
    LogicController.setNewPositionOfCurrentPlayer(position)  