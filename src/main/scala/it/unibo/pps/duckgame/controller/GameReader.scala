package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{GameBoard, Player, Token}
import it.unibo.pps.duckgame.utils.GameUtils

/** Class that keep tracks of all the statistics of the game */
object GameReader:
  private val MIN_PLAYERS = 2
  private val MAX_PLAYERS = 6

  /** Return current player
    *
    * @return
    *   current player
    */
  def currentPlayer: Player = players(Game.currentPlayer)

  /** Gets the index of the current player in the turn order.
    *
    * @return
    *   The index of the current player (integer). The meaning of the index value depends on the game's player
    *   management system.
    */
  def currentPlayerIndex: Int = Game.currentPlayer

  /** Return list of all players
    *
    * @return
    *   players' list
    */
  def players: List[Player] = Game.players

  /** Updates the player information at a specific index in the game's player list.
    *
    * @param index
    *   The index of the player to be updated in the `Game.players` list.
    * @param updatedPlayer
    *   The `Player` object containing the new information for the player.
    */
  def updatePlayers(index: Int, updatedPlayer: Player): Unit = Game.players = Game.players.updated(index, updatedPlayer)

  /** Optional element that returns the eventual game winner
    *
    * @return
    *   The (eventual) game winner
    */
  def winner: Option[Player] = Game.winner

  /** Return game board
    *
    * @return
    *   gameboard
    */
  def gameBoard: GameBoard = Game.gameBoard

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

  /** Resets the game state to its initial configuration */
  def resetGame(): Unit = Game.reset()

  /** Initializes the game state and starts a new game session */
  def startGame(): Unit =
    Game.firstRound = true
    Game.players = GameUtils MixPlayers Game.players

  /** Checks if everything's ok to start the game
    *
    * @return
    *   true if the game can begin, false otherwise
    */
  def canStartGame: Boolean =
    Game.players.length match
      case n if n < MIN_PLAYERS => false
      case _ => true

  /** Tell if it's possible to add another player to the game
    *
    * @return
    *   true if it can be added a new player, false otherwise
    */
  def canAddPlayer: Boolean =
    Game.players.length match
      case n if n < MAX_PLAYERS => true
      case _ => false

  /** Return if there's a game winner, so the game is done
    *
    * @return
    *   true if a player has won, false otherwise
    */
  def checkVictory(): Boolean =
    currentPlayer.actualPosition match
      case 63 =>
        Game.winner = Game.players.headOption
        true
      case _ => false

  /** Sets the winner of the game.
    *
    * @param player
    *   An `Option[Player]` containing the winning player object (if any).
    */
  def setWinner(player: Option[Player]): Unit = Game.winner = player

  /** Gets the index of the player currently in the well (if any).
    *
    * @return
    *   The index of the player in the well (integer), or -1 if no player is in the well.
    */
  def playerInWell(): Int =
    Game.playerInWell

  /** A player goes onto the well or jail based on the provided flag.
    *
    * @param player
    *   The index of the player to be sent.
    * @param place
    *   `true` the player go onto the well, `false` to send them to jail.
    */
  def playerGoesInWellOrJail(player: Int, place: Boolean): Unit =
    if place then Game.playerInWell = player
    else Game.playerInJail = player

  /** Gets the index of the player currently in jail (if any).
    *
    * @return
    *   The index of the player in jail (integer), or -1 if no player is in jail.
    */
  def playerInJail(): Int = Game.playerInJail

  /** Checks if the current game round is the first round.
    *
    * @return
    *   `true` if the current round is the first round, `false` otherwise.
    */
  def isFirstRound: Boolean = Game.firstRound

  /** This method sets the `firstRound` flag in the `Game` object to `false`, signifying that the first round is over */
  def endFirstRound(): Unit = Game.firstRound = false

  /** Gets a reference to the list of currently available tokens in the game.
    *
    * @return
    *   An immutable list of `Token` objects representing the currently available tokens. Modifying the returned list
    *   will not affect the internal game state.
    */
  def availableTokens(): List[Token] = Game.availableTokens

  /** Advances the turn to the next player in the turn order */
  def nextPlayer(): Unit =
    Game.currentPlayer = (currentPlayerIndex + 1) % players.length

  /** Handles the game state after a player quits.
    *
    * @param playerToDelete
    *   The `Player` object representing the player who has quit the game.
    */
  def afterPlayerQuit(playerToDelete: Player): Unit =
    val nextPlayer = currentPlayer
    Game removePlayer playerToDelete
    Game.currentPlayer = players.indexOf(nextPlayer)
