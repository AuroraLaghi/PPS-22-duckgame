package it.unibo.pps.duckgame.model

import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*
import org.scalatest.matchers.should.Matchers

import scala.language.{implicitConversions, postfixOps}

class DiceTest extends AnyFlatSpec with should.Matchers:

  val dices: Dice = Dice()
  val MIN_DICE_VALUE = 1
  val MAX_DICE_VALUE = 6

  "first dice value" should "be greater or equal to 1" in {
    dices.dice._1 should be >= MIN_DICE_VALUE
  }
  it should "be smaller or equal to 6" in {
    dices.dice._1 should be <= MAX_DICE_VALUE
  }

  "second dice value" should "be greater or equal to 1" in {
    dices.dice._2 should be >= MIN_DICE_VALUE
  }
  it should "be smaller or equal than 6" in {
    dices.dice._2 should be <= MAX_DICE_VALUE
  }

  val sum: Int = dices.dice._1 + dices.dice._2
  "dices values sum" should "be greater or equal to 2 and less or equal to 12" in {
    sum should be >= 2 
  }
  it should "be less or equal to 12" in {
    sum should be <= 12
  }
  
  val DICE1_VALUE = 1
  val DICE2_VALUE = 2
  val newDice: Dice = Dice((DICE1_VALUE, DICE2_VALUE))
  
  "Dice element with given values" should "have correct values, for first dice" in {
    newDice.dice._1 shouldBe DICE1_VALUE 
  }
  it should "have correct value even in the second dice" in {
    newDice.dice._2 shouldBe DICE2_VALUE
  }