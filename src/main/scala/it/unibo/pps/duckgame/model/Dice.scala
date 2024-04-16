package it.unibo.pps.duckgame.model

import scala.util.Random
/** Represents a dice manager that can roll two dice.
  * @param dice1
  *   first dice
  * @param dice2
  *   second dice
  */
final case class Dice(dice1: Int, dice2: Int):

  /** Rolls the two dices and returns the result.
    *
    * @return
    *   a tuple containing the result of the two dice
    */
  def roll(): (Int, Int) = (Dice.rollDice(), Dice.rollDice())

object Dice:

  val MIN_DICE_VALUE = 1
  val MAX_DICE_VALUE = 6

  def apply(): Dice = new Dice(rollDice(), rollDice())

  /** roll one dice
    *
    * @return
    *   a random number between 1 and 6
    */
  private def rollDice(): Int =
    Random.between(MIN_DICE_VALUE, MAX_DICE_VALUE + 1)
