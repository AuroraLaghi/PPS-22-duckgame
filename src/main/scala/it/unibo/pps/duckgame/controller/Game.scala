package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{GameBoard, Player, Token}
import it.unibo.pps.duckgame.utils.{AnyOps, GameUtils}

/** Game object that contains the current player, the list of players and, eventually, the winner */
protected object Game:

  private def DEFAULT_CURRENT_PLAYER: Int = 0
  private var _currentPlayer: Int = DEFAULT_CURRENT_PLAYER
  private var _players: List[Player] = List.empty
  private var _winner: Option[Player] = None
  private var _gameBoard: GameBoard = GameBoard()
  private var _firstRound: Boolean = false
  private var _playerInJail: Int = -1
  private var _playerInWell: Int = -1
  private var _availableTokens: List[Token] = Token.values.toList

  /** Return game board
    *
    * @return
    *   current gameboard
    */
  def gameBoard: GameBoard = _gameBoard

  /** Sets current gameboard
    *
    * @param value
    *   gameboard to set
    */
  def gameBoard_=(value: GameBoard): Unit =
    _gameBoard = value

  /** Checks if the current game round is the first round.
    *
    * @return
    *   `true` if the current round is the first round, `false` otherwise.
    */
  def firstRound: Boolean = _firstRound

  /** Sets a flag indicating whether the current game round is the first round.
    *
    * @param value
    *   `true` to set the current round as the first round, `false` otherwise.
    */
  def firstRound_=(value: Boolean): Unit =
    _firstRound = value

  /** Sets the current player
    *
    * @return
    *   the current player
    */
  def currentPlayer: Int = _currentPlayer

  /** Sets the current player
    *
    * @param value
    *   the new current player's position in the players' list
    */
  def currentPlayer_=(value: Int): Unit =
    _currentPlayer = value

  /** Return the list of players
    *
    * @return
    *   the list of players
    */
  def players: List[Player] = _players

  /** Sets the list of players
    *
    * @param value
    *   the new list of players
    */
  def players_=(value: List[Player]): Unit =
    _players = value

  /** Return the winner
    *
    * @return
    *   the winner
    */
  def winner: Option[Player] = _winner

  /** Sets the winner of the game
    * @param value
    *   the new winner*
    */
  def winner_=(value: Option[Player]): Unit =
    _winner = value

  /** Gets the index of the player currently in jail (if any).
    *
    * @return
    *   The index of the player in jail (integer), or -1 if no player is in jail.
    */
  def playerInJail: Int = _playerInJail

  /** Sets the index of the player who is in jail (or -1 if no player is in jail).
    *
    * @param value
    *   The index of the player to be marked as in jail, or -1 to indicate no player in jail.
    */
  def playerInJail_=(value: Int): Unit =
    _playerInJail = value

  /** Gets the index of the player currently in the well (if any).
    *
    * @return
    *   The index of the player in the well (integer), or -1 if no player is in the well.
    */
  def playerInWell: Int = _playerInWell

  /** Sets the index of the player who is in the well (or -1 if no player is in the well).
    *
    * @param value
    *   The index of the player to be marked as in the well, or -1 to indicate no player in the well.
    */
  def playerInWell_=(value: Int): Unit =
    _playerInWell = value

  /** Gets a reference to the list of currently available tokens in the game.
    *
    * @return
    *   An immutable list of `Token` objects representing the currently available tokens. Modifying the returned list
    *   will not affect the internal state.
    */
  def availableTokens: List[Token] = _availableTokens

  /** Sets the list of currently available tokens in the game.
    *
    * @param value
    *   The list of `Token` objects to be set as the new available tokens.
    */
  def availableTokens_=(value: List[Token]): Unit =
    _availableTokens = value

  /** Add a player to the game
    *
    * @param player
    *   the new player to be added
    */
  def addPlayer(player: Player): Unit =
    players = player :: players
    availableTokens = availableTokens.filter(_ =/= player.token)

  /** Removes a player from the game
    *
    * @param player
    *   the player to be removed
    */
  def removePlayer(player: Player): Unit =
    availableTokens = player.token :: availableTokens
    players = players.filter(_ =/= player)

  /** Reset the game to the initial parameters */
  def reset(): Unit =
    currentPlayer = DEFAULT_CURRENT_PLAYER
    players = List.empty
    winner = None
    gameBoard = GameBoard()
    firstRound = true
    playerInJail = -1
    playerInWell = -1
    availableTokens = Token.values.toList
