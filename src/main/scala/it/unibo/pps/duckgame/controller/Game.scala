package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{GameBoard, Player}
import it.unibo.pps.duckgame.utils.GameUtils

/** Game object that contains the current player, the list of players and, eventually, the winner
 */
protected object Game:

  private val DEFAULT_CURRENT_PLAYER: Int = 0
  private var _currentPlayer: Int = DEFAULT_CURRENT_PLAYER
  private var _players: List[Player] = List.empty
  private var _winner: Option[Player] = None
  private var _gameBoard: GameBoard = GameBoard()

  /** Return game board
   * 
   * @return
   *  current gameboard
   */
  def gameBoard: GameBoard = _gameBoard

  /** Sets current gameboard
   * 
   * @param value
   *  gameboard to set
   */
  def gameBoard_=(value: GameBoard): Unit =
    _gameBoard = value

  /** Sets the current player
   * 
   * @return
   *  the current player
   */
  def currentPlayer: Int = _currentPlayer

  /** Sets the current player
   * 
   * @param value 
   *  the new current player's position in the players' list
   */
  def currentPlayer_=(value: Int): Unit =
    _currentPlayer = value

  /** Return the list of players
   *
   * @return
   * the list of players
   */
  def players: List[Player] = _players

  /** Sets the list of players
   * 
   * @param value
   *  the new list of players
   */
  def players_=(value: List[Player]): Unit =
    _players = value

  /** Return the winner
   * 
   * @return
   *  the winner
   */
  def winner: Option[Player] = _winner
  
  /** Sets the winner of the game
   * @param value
   *  the new winner*
   */
  def winner_=(value: Option[Player]): Unit =
    _winner = value

  /** Add a player to the game
   * 
    * @param player
   *  the new player to be added
   */  
  def addPlayer(player: Player): Unit =
    players = player::players

  /** Removes a player from the game
   * 
   * @param player
   *  the player to be removed
   */  
  def removePlayer(player: Player): Unit =
    players = players.filter(_ != player)

  /** Reset the game to the initial parameters
   */
  def reset(): Unit =
    currentPlayer = DEFAULT_CURRENT_PLAYER
    players = List.empty
    winner = None
    gameBoard = GameBoard()






