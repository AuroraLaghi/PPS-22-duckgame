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

  def currentPlayerIndex: Int = Game.currentPlayer

  /** Return list of all players
    *
    * @return
    *   players' list
    */
  def players: List[Player] = Game.players

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

  def resetGame(): Unit = Game.reset()

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

  def setWinner(player: Option[Player]): Unit = Game.winner = player

  def playerInWell(): Int =
    Game.playerInWell

  def playerGoesInWellOrJail(player: Int, place: Boolean): Unit =
    if place then Game.playerInWell = player
    else Game.playerInJail = player

  def playerInJail(): Int =
    Game.playerInJail

  def isFirstRound: Boolean = Game.firstRound

  def endFirstRound(): Unit = Game.firstRound = false

  def availableTokens(): List[Token] =
    Game.availableTokens

  def nextPlayer(): Unit =
    Game.currentPlayer = (currentPlayerIndex + 1) % players.length

  def afterPlayerQuit(playerToDelete: Player): Unit =
    val nextPlayer = currentPlayer
    Game removePlayer playerToDelete
    Game.currentPlayer = players.indexOf(nextPlayer)
