package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.view.GameBoardController
import it.unibo.pps.duckgame.controller.{Game, GameReader}
import it.unibo.pps.duckgame.model.CellStatus
import it.unibo.pps.duckgame.utils.{AnyOps, GameUtils}

/** Class that control all players' movements and manages special cells */
object MovementsController:

  /** This method is called when a player needs to move in the first round of game If a player gets 9 in first round, it
    * doesn't go on cell 9 otherwise it'll win the game, instead it goes in cell 26 or 53 depending which numbers it
    * obtained from dices
    *
    * @param dicePair
    *   numbers obtained from dice throw
    */
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

  /** Called when a player needs to move checks if player ends on a special cell and triggers the related action
    *
    * @param steps
    *   number of steps player has to move forward
    */
  def moveWithSteps(steps: Int): Unit =
    LogicController.moveCurrentPlayer(steps)
    checkSpecialCell(steps)

  /** Called when player ends on a special cells and needs to go to a predefined cell
    *
    * @param position
    *   new player's position
    */
  def fixedPositionMove(position: Int): Unit =
    LogicController.setNewPositionOfCurrentPlayer(position)

  /** Checks if current player ended in well or jail cell and got locked
    *
    * @param player
    *   index of current player
    */
  def playerCantPlay(player: Int): Unit = GameReader.players(player).actualPosition match
    case 31 =>
      if GameReader.playerInWell() =/= -1 then
        GameBoardController.viewPlayerMovement(
          "Ora " + GameReader.players(GameReader.playerInWell()).name + " può ricominciare a giocare"
        )
      GameReader.playerGoesInWellOrJail(player, true)
    case 52 =>
      if GameReader.playerInJail() =/= -1 then
        GameBoardController.viewPlayerMovement(
          "Ora " + GameReader.players(GameReader.playerInJail()).name + " può ricominciare a giocare"
        )
      GameReader.playerGoesInWellOrJail(player, false)
    case _ =>

  /** Checks if player finished on a special cells and calls the related action
    *
    * @param steps
    *   number of steps done by the player
    */
  private def checkSpecialCell(steps: Int): Unit =
    LogicController.checkCellType match
      case CellStatus.SPECIAL_CELL =>
        if !(GameReader.isFirstRound && steps == 9) then
          val specialCell = GameUtils.getSpecialCellFromPlayerPosition
          GameBoardController.viewPlayerMovement(specialCell.get.message)
          specialCell.foreach(PlayerController.playerOnSpecialCell(_, steps))
      case CellStatus.STANDARD_CELL =>
