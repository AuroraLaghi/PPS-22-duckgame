package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.controller.GameReader.{currentPlayerIndex, nextPlayer, players}
import it.unibo.pps.duckgame.controller.view.GameBoardController
import it.unibo.pps.duckgame.model.CellStatus
import it.unibo.pps.duckgame.utils.{AnyOps, GameUtils}

import scala.annotation.tailrec
/** Object who manages the game logic */
object LogicController:
  /** Initialize players' list mixing its values */
  def initializeGame(): Unit =
    GameReader.startGame()

  /** Close the game */
  def exitGame(): Unit =
    sys.exit(0)

  /** Reset the game to the initial stats */
  def newGame(): Unit =
    GameReader.resetGame()

  def movePlayer(dicePair: (Int, Int)): Unit =
    if GameReader.isFirstRound then MovementsController.firstRoundMoves(dicePair)
    else MovementsController.moveWithSteps(dicePair._1 + dicePair._2)

  /** Sets current player's new position
    *
    * @param steps
    *   Number of cells to sum to old position
    */
  def moveCurrentPlayer(steps: Int): Unit =
    PlayerController.updatePlayerWith(GameReader.currentPlayerIndex, GameReader.currentPlayer.move(steps))

  /** Locks or unlocks the current player in the game.
    *
    * @param lock
    *   A boolean flag indicating whether to lock (`true`) or unlock (`false`) the current player.
    */

  def lockUnlockTurnPlayer(lock: Boolean): Unit =
    PlayerController.updatePlayerWith(GameReader.currentPlayerIndex, GameReader.currentPlayer.lockUnlockPlayer(lock))


  /** Called when a player ends its turn */
  def endTurn(): Unit =
    nextPlayerFree()
    if checkFirstRoundDone() then GameReader.endFirstRound()

  /** Called when a player quits the game */
  def currentPlayerQuit(): Unit =
    val playerToDelete = GameReader.currentPlayer
    endTurn()
    //control for particular case if the other active players are locked for 1 turn
    if GameReader.currentPlayer === playerToDelete then endTurn()
    GameReader.afterPlayerQuit(playerToDelete)

  /** Checked if the first round is done */
  private def checkFirstRoundDone(): Boolean =
    GameReader.currentPlayerIndex == 0 && GameReader.isFirstRound

  /** Determines the type of cell the current player is on.
    *
    * @return
    *   CellStatus: `SPECIAL_CELL` or `STANDARD_CELL`
    */
  def checkCellType: CellStatus =
    val cell = GameUtils.getSpecialCellFromPlayerPosition
    cell match
      case Some(_) => CellStatus.SPECIAL_CELL
      case _ => CellStatus.STANDARD_CELL

  /** Finds the next available player, skipping locked players or those in jail/well. */
  @tailrec
  private def nextPlayerFree(): Unit =
    GameReader.nextPlayer()
    GameReader.currentPlayer.isLocked match
      case true =>
        LogicController.lockUnlockTurnPlayer(false)
        nextPlayerFree()
      case false
          if GameReader.currentPlayerIndex != GameReader.playerInWell()
            && GameReader.currentPlayerIndex != GameReader.playerInJail() =>
      case _ => nextPlayerFree()

  /** Locks current player in well or jail and eventually unlock the precedent one
    *
    * @param player
    *   index of current player
    */
  def playerCantPlay(player: Int): Unit =
    GameBoardController.playerLockedAlert(GameReader.players(player))
    GameReader.players(player).actualPosition match
      case 31 => GameReader.playerGoesInWellOrJail(player, true)
      case 52 => GameReader.playerGoesInWellOrJail(player, false)
      case _ =>
