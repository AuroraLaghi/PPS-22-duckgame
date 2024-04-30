package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.CellStatus
import it.unibo.pps.duckgame.utils.GameUtils

object MovementsController:

  def firstRoundMoves(dicePair: (Int, Int)): Unit =
    dicePair match
      case (6, 3) | (3, 6) if GameReader.currentPlayer.actualPosition == 0 =>
        LogicController.moveCurrentPlayer(26)
        GameBoardController.viewPlayerMovement(
          "Dal tiro dei dadi è uscito 3 e 6 quindi il giocatore va alla casella 26."
        )
      case (5, 4) | (4, 5) if GameReader.currentPlayer.actualPosition == 0 =>
        LogicController.moveCurrentPlayer(53)
        GameBoardController.viewPlayerMovement(
          "Dal tiro dei dadi è uscito 4 e 5 quindi il giocatore va alla casella 53."
        )
      case _ =>
        val steps: Int = dicePair._1 + dicePair._2
        LogicController.moveCurrentPlayer(steps)
        checkSpecialCell(steps)

  def standardMove(dicePair: (Int, Int)): Unit =
    LogicController.moveCurrentPlayer(dicePair._1 + dicePair._2)
    checkSpecialCell(dicePair._1 + dicePair._2)

  def specialCellMove(steps: Int): Unit =
    LogicController.moveCurrentPlayer(steps)
    checkSpecialCell(steps)

  def fixedPositionMove(position: Int): Unit =
    LogicController.setNewPositionOfCurrentPlayer(position)

  def playerGoToJail(player: Int): Unit =
    if GameReader.playerInJail() != -1 then
      GameBoardController.viewPlayerMovement(
        "Ora " + GameReader.players(GameReader.playerInJail()).name + " può ricominciare a giocare"
      )
    Game.playerInJail = player

  def playerGoIntoWell(player: Int): Unit =
    if GameReader.playerInWell() != -1 then
      GameBoardController.viewPlayerMovement(
        "Ora " + GameReader.players(GameReader.playerInWell()).name + " può ricominciare a giocare"
      )
    Game.playerInWell = player

  private def checkSpecialCell(steps: Int): Unit =
    LogicController.checkCellType match
      case CellStatus.SPECIAL_CELL =>
        if !(GameReader.isFirstRound && steps == 9) then
          val specialCell = GameUtils.getSpecialCellFromPlayerPosition
          GameBoardController.viewPlayerMovement(specialCell.get.message)
          specialCell.foreach(PlayerController.playerOnSpecialCell(_, steps))
      case CellStatus.STANDARD_CELL =>
