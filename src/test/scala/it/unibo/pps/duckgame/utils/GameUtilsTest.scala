package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.controller.GameStats
import it.unibo.pps.duckgame.model.Dice
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.util.Random

class GameUtilsTest extends AnyFlatSpec with should.Matchers:
  val RANDOM_POSITION: Int = Random.between(1, GameStats.gameBoard.size / 2)
  val RANDOM_STEPS: Int = Random.between(1, Dice.MAX_DICE_VALUE * 2 + 1)

  "Method addSumToPosition" should "make arithmetic sum of given values" in {
    GameUtils.addSumToPosition(RANDOM_STEPS, RANDOM_POSITION) shouldBe RANDOM_STEPS + RANDOM_POSITION
  }

  "If you get a position value bigger than the gameboard size you" should "go backward in the gameboard" in {
    GameUtils.addSumToPosition(RANDOM_POSITION, GameStats.gameBoard.size) should be < GameStats.gameBoard.size
  }


