package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{CellStatus, Player}
import it.unibo.pps.duckgame.utils.GameUtils
/**
 * Object who manages the game logic
 */
object LogicController:
  /** Add a player to the players' list
    *
    * @param player
    *   the player to be added
    */
  def addPlayer(player: Player): Unit =
    Game addPlayer player

  /** Removes a player from the game
    *
    * @param player
    *   player entity to be removed
    */
  def removePlayer(player: Player): Unit =
    Game removePlayer player

  /** Initialize players' list mixing its values */
  def startGame(): Unit =
    Game.firstRound = true
    Game.players = GameUtils MixPlayers Game.players

  /** Close the game */
  def exitGame(): Unit =
    sys.exit(0)

  /** Reset the game to the initial stats */
  def newGame(): Unit =
    Game.reset()

  /** Sets current player's new position
    *
    * @param steps
    *   Number of cells to sum to old position
    */
  def moveCurrentPlayer(steps: Int): Unit =
    PlayerController.updatePlayerWith(Game.currentPlayer, GameReader.currentPlayer.move(steps))
    
  def setNewPositionOfCurrentPlayer(position: Int): Unit =
    PlayerController.updatePlayerWith(Game.currentPlayer, GameReader.currentPlayer.newPosition(position))

  /** Called when a player ends its turn */
  def endTurn(): Unit =
    Game.currentPlayer = (Game.currentPlayer + 1) % Game.players.length
    if checkFirstRoundDone() then Game.firstRound = false

  /** Called when a player quits the game */
  def currentPlayerQuit(): Unit =
    val playerToDelete = GameReader.currentPlayer
    endTurn()
    val nextPlayer = GameReader.currentPlayer
    Game removePlayer playerToDelete
    Game.currentPlayer = Game.players.indexOf(nextPlayer)

  private def checkFirstRoundDone(): Boolean =
    Game.currentPlayer == 0 && Game.firstRound
    
  def checkCellType: CellStatus =
    val cell = GameUtils.getSpecialCellFromPlayerPosition()
    println(cell)
    cell match
      case Some(_) => CellStatus.SPECIAL_CELL
      case _ => CellStatus.STANDARD_CELL  
