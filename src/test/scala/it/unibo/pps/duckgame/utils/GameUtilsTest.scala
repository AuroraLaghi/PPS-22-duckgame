package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.controller.GameStats
import it.unibo.pps.duckgame.model.Dice
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.util.Random

class GameUtilsTest extends AnyFlatSpec with should.Matchers:
  val RANDOM_POSITION: Int = Random.between(1, GameStats.gameBoard.size / 2)
  val RANDOM_STEPS: Int = Random.between(1, Dice.MAX_DICE_VALUE * 2 + 1)
  val NEGATIVE_POSITION: Int = -1
  val OVERMAX_POSITION: Int = GameStats.gameBoard.size

  "Method addSumToPosition" should "make arithmetic sum of given values" in {
    GameUtils.addSumToPosition(RANDOM_STEPS, RANDOM_POSITION) shouldBe RANDOM_STEPS + RANDOM_POSITION
  }

  "If you get a position value bigger than the gameboard size you" should "go backward in the gameboard" in {
    GameUtils.addSumToPosition(RANDOM_POSITION, GameStats.gameBoard.size) should be < GameStats.gameBoard.size
  }

  "Getting position from invalid coordinates" should "throw IllegalArgumentException" in {
    an [IllegalArgumentException] should be thrownBy{
      GameUtils.getCoordinateFromPosition(NEGATIVE_POSITION)
    }

    an [IllegalArgumentException] should be thrownBy{
      GameUtils.getCoordinateFromPosition(OVERMAX_POSITION)
    }
  }

  val COORD_FIRST_SIDE: ((Int, Int), Int) = ((4, 0), 4)
  val COORD_INSIDE: ((Int, Int), Int) = ((2,2), 48)
  val COORD_LAST_CELL: ((Int, Int), Int) = ((3, 4), 63)

  "Method GetCoordinateFromPosition" should "return col, row position of a given cell in a 8x8 grid" in {
    GameUtils.getCoordinateFromPosition(COORD_FIRST_SIDE._2) shouldBe COORD_FIRST_SIDE._1
    GameUtils.getCoordinateFromPosition(COORD_INSIDE._2) shouldBe COORD_INSIDE._1
    GameUtils.getCoordinateFromPosition(COORD_LAST_CELL._2) shouldBe COORD_LAST_CELL._1
  }

  val DEFAULT_GRID_SIZE: (Int, Int) = (3,2)
  val ILLEGAL_GRID_SIZE: (Int, Int) = (0,1)
  val LAST_CELL: (Int, Int) = (2,1)
  val STARTING_CELL: (Int, Int) = (0,0)
  val FIRST_CELL_SECOND_ROW: (Int, Int) = (0, 1)
  val DEFAULT_STARTING_VALUE = 1
  val ILLEGAL_STARTING_VALUE = 0
  val LAST_CELL_VALUE = 6
  val VALUE_FIRST_CELL_OF_SECOND_ROW = 4

  "Method getNthCellInGridWithStartingPos" should "throws exception when are given wrong arguments" in {
    an [IllegalArgumentException] should be thrownBy{
      GameUtils.getNthCellInGridWithStartingPos(
        DEFAULT_STARTING_VALUE,
        ILLEGAL_GRID_SIZE,
        STARTING_CELL
      )
    }
    an [IllegalArgumentException] should be thrownBy{
      GameUtils.getNthCellInGridWithStartingPos(
        DEFAULT_STARTING_VALUE,
        ILLEGAL_GRID_SIZE.swap,
        STARTING_CELL
      )
    }
    an [IllegalArgumentException] should be thrownBy{
      GameUtils.getNthCellInGridWithStartingPos(
        ILLEGAL_STARTING_VALUE,
        DEFAULT_GRID_SIZE,
        STARTING_CELL
      )
    }
    an [IllegalArgumentException] should be thrownBy{
      GameUtils.getNthCellInGridWithStartingPos(
        LAST_CELL_VALUE + 1,
        DEFAULT_GRID_SIZE,
        STARTING_CELL
      )
    }
  }

  "Method getNthCellInGridWithStartingPos" should "return correct values with given arguments" in {
    GameUtils.getNthCellInGridWithStartingPos(
      DEFAULT_STARTING_VALUE,
      DEFAULT_GRID_SIZE,
      STARTING_CELL
    ) shouldBe STARTING_CELL

    GameUtils.getNthCellInGridWithStartingPos(
      VALUE_FIRST_CELL_OF_SECOND_ROW,
      DEFAULT_GRID_SIZE,
      STARTING_CELL
    ) shouldBe FIRST_CELL_SECOND_ROW

    GameUtils.getNthCellInGridWithStartingPos(
      LAST_CELL_VALUE,
      DEFAULT_GRID_SIZE,
      STARTING_CELL
    ) shouldBe LAST_CELL
  }





