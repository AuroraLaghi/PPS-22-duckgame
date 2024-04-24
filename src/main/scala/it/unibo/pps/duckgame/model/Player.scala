package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.utils.GameUtils

/** Represents a player in the game.
 *
 * @param actualPosition
 *  The player's current position on the game board.
 * @param name
 *  The name of the player.
 */
final case class Player(
      actualPosition: Int,
      name: String,
      oneTurnStop: Boolean):

  /** Moves the player by the specified number of steps.
   *
   * @param steps
   *  The number of steps to move the player.
   * @return
   *  A new `Player` instance with the updated position.
   */
  def move(steps: Int): Player =
    Player(GameUtils.addSumToPosition(steps, actualPosition), name, oneTurnStop)
  
  def newPosition(position: Int): Player =
    Player(position, name, oneTurnStop)

  def isLocked: Boolean =
    this.oneTurnStop

  def lockUnlockPlayer(flag: Boolean): Player =
    Player(actualPosition, name, flag)

    
object Player:
  
  private val DEFAULT_STARTING_POSITION = 0
  
  def apply(name: String): Player =
    Player(DEFAULT_STARTING_POSITION, name, false)
  
  def apply(): Player =
    Player(DEFAULT_STARTING_POSITION, "", false)

  
