package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.utils.GameUtils

/** Represents a player in the game.
  *
  * @param actualPosition
  *   The player's current position on the game board.
  * @param name
  *   The name of the player.
  * @param oneTurnStop
  *   A boolean flag indicating whether the player has a one-turn stop (potentially due to an action or penalty).
  * @param token
  *   A Token object representing the token currently held by the player (if any).
  */
final case class Player(actualPosition: Int, name: String, oneTurnStop: Boolean, token: Token):

  /** Moves the player by the specified number of steps.
    *
    * @param steps
    *   The number of steps to move the player.
    * @return
    *   A new `Player` instance with the updated position.
    */
  def move(steps: Int): Player =
    Player(GameUtils.addSumToPosition(steps)(actualPosition), name, oneTurnStop, token)

  /** Creates a new `Player` object with an updated position.
    *
    * @param position
    *   The new position for the player on the game board.
    * @return
    *   A new `Player` object with the updated position.
    */
  def newPosition(position: Int): Player =
    Player(position, name, oneTurnStop, token)

  /** Checks if the player is currently suspended for one turn.
    * @return
    *   `true` if the player has a one-turn stop, `false` otherwise.
    */
  def isLocked: Boolean = this.oneTurnStop

  /** Creates a new `Player` object with the lock/unlock status updated.
    *
    * @param flag
    *   A boolean flag indicating whether to lock (true) or unlock (false) the player.
    * @return
    *   A new `Player` object with the updated lock/unlock status.
    */
  def lockUnlockPlayer(flag: Boolean): Player =
    Player(actualPosition, name, flag, token)

object Player:

  private val DEFAULT_STARTING_POSITION = 0

  def apply(name: String, token: Token): Player =
    Player(DEFAULT_STARTING_POSITION, name, false, token)

  def apply(name: String): Player =
    Player(DEFAULT_STARTING_POSITION, name, false, Token.RED)
