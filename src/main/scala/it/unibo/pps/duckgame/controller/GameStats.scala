package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{GameBoard, Player}

/**
 * Class that keep tracks of all the statistics of the game
 */
object GameStats:
  private val MIN_PLAYERS = 2
  private val MAX_PLAYERS = 6

  /** Return current player
   * 
   * @return
   *  current player
   */
  def currentPlayer: Player = players(Game.currentPlayer)

  /** Return list of all players
   * 
   * @return
   *  players' list
   */
  def players: List[Player] = Game.players

  /** Optional element that returns the eventual game winner
   * 
   * @return
   *  The (eventual) game winner
   */
  def winner: Option[Player] = Game.winner

  /** Return game board
   * 
   * @return
   *  gameboard
   */
  def gameBoard: GameBoard = Game.gameBoard

  /** Checks if everything's ok to start the game
   * 
   * @return
   *  true if the game can begin, false otherwise
   */
  def canStartGame: Boolean =
    Game.players.length match
      case n if n < MIN_PLAYERS => false
      case _ => true

  /** Tell if it's possible to add another player to the game
   * 
   * @return
   *  true if it can be added a new player, false otherwise
   */
  def canAddPlayer: Boolean =
    Game.players.length match
      case n if n < MAX_PLAYERS => true
      case _ => false

  /** Return if there's a game winner, so the game is done
   * 
   * @return
   *  true if a player has won, false otherwise
   */
  def checkVictory(): Boolean =
    currentPlayer.actualPosition match
      case 63 =>
        Game.winner = Game.players.headOption
        true
      case _ => false

  /** Checks if there's only one player actually playing the game
   * 
   * @return
   * true if only one player is left playing, false otherwise
   */
  def checkVictoryForSurrender(): Boolean = players.size match
    case n if n == MIN_PLAYERS - 1 =>
      Game.winner = Game.players.headOption
      true
    case _ => false
