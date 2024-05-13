package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.utils.PrologGameUtils.PrologEngine.PrologEngine
import it.unibo.pps.duckgame.utils.resources.PrologResources
import it.unibo.pps.duckgame.utils.PrologGameUtils.PrologTheory.given
import it.unibo.pps.duckgame.utils.PrologGameUtils.{PrologSolution, PrologTheory, ConversionTerm}
import scala.util.Random

/** Represents a dice manager that can roll two dice.
  * @param dice
  *   a pair of dice
  */
final case class Dice(dice: (Int, Int)):

  /** Rolls the two dices and returns the result.
    *
    * @return
    *   a tuple containing the result of the two dice
    */
  def roll(): (Int, Int) = Dice.rollDice()

object Dice:
  private val pathTheory = PrologResources.ROLLDICE_PROLOG.path
  private val prolog: PrologEngine = PrologEngine(pathTheory)

  private val MAX_DICE_VALUE = 6

  def apply(): Dice = new Dice(rollDice())

  /** roll one dice
    *
    * @return
    *   a random number between 1 and 6
    */
  private def rollDice(): (Int, Int) =
    prolog.randomDice(MAX_DICE_VALUE)
