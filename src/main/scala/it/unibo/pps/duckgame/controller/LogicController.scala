package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.Player
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

  /** Called when a player ends its turn */
  def endTurn(): Unit =
    Game.currentPlayer = (Game.currentPlayer + 1) % Game.players.length

  /** Called when a player quits the game */
  def currentPlayerQuit(): Unit =
    val playerToDelete = GameReader.currentPlayer
    endTurn()
    val nextPlayer = GameReader.currentPlayer
    Game removePlayer playerToDelete
    Game.currentPlayer = Game.players.indexOf(nextPlayer)
