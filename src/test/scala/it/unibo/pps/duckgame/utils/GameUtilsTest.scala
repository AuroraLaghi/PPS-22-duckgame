package it.unibo.pps.duckgame.utils

import alice.tuprolog.exceptions.NoSolutionException
import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.controller.logic.{LogicController, MovementsController}
import it.unibo.pps.duckgame.model.cell.specialCell.SpecialCell
import it.unibo.pps.duckgame.model.{CellStatus, Dice, Player}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.util.Random

class GameUtilsTest extends AnyFlatSpec with should.Matchers:
  val MAX_DICE_VALUE = 6
  val RANDOM_POSITION: Int = Random.between(1, GameReader.gameBoard.size / 2)
  val RANDOM_STEPS: Int = Random.between(1, MAX_DICE_VALUE * 2 + 1)

  "Method addSumToPosition" should "make arithmetic sum of given values" in {
    GameUtils.addSumToPosition(RANDOM_STEPS, RANDOM_POSITION) shouldBe RANDOM_STEPS + RANDOM_POSITION
  }

  "If you get a position value bigger than the gameboard size you" should "go backward in the gameboard" in {
    GameUtils.addSumToPosition(RANDOM_POSITION, GameReader.gameBoard.size) should be < GameReader.gameBoard.size
  }

  val COORD_FIRST_SIDE: ((Int, Int), Int) = ((4, 0), 4)
  val COORD_INSIDE: ((Int, Int), Int) = ((2, 2), 48)
  val COORD_LAST_CELL: ((Int, Int), Int) = ((3, 4), 63)

  "Method GetCoordinateFromPosition" should "return col, row position of a given cell in a 8x8 grid" in {
    GameUtils.getCoordinateFromPosition(COORD_FIRST_SIDE._2) shouldBe COORD_FIRST_SIDE._1
    GameUtils.getCoordinateFromPosition(COORD_INSIDE._2) shouldBe COORD_INSIDE._1
    GameUtils.getCoordinateFromPosition(COORD_LAST_CELL._2) shouldBe COORD_LAST_CELL._1
  }

  val DEFAULT_GRID_SIZE: (Int, Int) = (3, 2)
  val ILLEGAL_GRID_SIZE: (Int, Int) = (0, 1)
  val LAST_CELL: (Int, Int) = (2, 1)
  val STARTING_CELL: (Int, Int) = (0, 0)
  val FIRST_CELL_SECOND_ROW: (Int, Int) = (0, 1)
  val DEFAULT_STARTING_VALUE = 1
  val LAST_CELL_VALUE = 6
  val VALUE_FIRST_CELL_OF_SECOND_ROW = 4

  "Method getNthCellInGridWithStartingPos" should "throws exception when are given wrong arguments" in {
    an[NoSolutionException] should be thrownBy {
      GameUtils.getNthSlotFromCell(
        DEFAULT_STARTING_VALUE,
        ILLEGAL_GRID_SIZE
      )
    }
  }

  "Method getNthCellInGridWithStartingPos" should "return correct values with given arguments" in {
    GameUtils.getNthSlotFromCell(
      DEFAULT_STARTING_VALUE,
      DEFAULT_GRID_SIZE
    ) shouldBe STARTING_CELL

    GameUtils.getNthSlotFromCell(
      VALUE_FIRST_CELL_OF_SECOND_ROW,
      DEFAULT_GRID_SIZE
    ) shouldBe FIRST_CELL_SECOND_ROW

    GameUtils.getNthSlotFromCell(
      LAST_CELL_VALUE,
      DEFAULT_GRID_SIZE
    ) shouldBe LAST_CELL
  }

  val p1: Player = Player("p1")
  val p2: Player = Player("p2")
  val players: List[Player] = List(p1, p2)
  players.foreach(p => GameReader addPlayer p)

  "Method getSpecialCellFromPlayerPosition" should "give an empty return if current player is on a standard cell" in {
    GameUtils.getSpecialCellFromPlayerPosition shouldBe empty
  }

  "Method getSpecialCellFromPlayerPosition" should "return, if present, the special cell located in player's position" in {
    MovementsController.fixedPositionMove(6)
    GameUtils.getSpecialCellFromPlayerPosition should not be empty
    LogicController.checkCellType shouldBe CellStatus.SPECIAL_CELL
  }

  "Method getSpecialCellFromPlayerPosition" should "throws NoSuchElementException if it is invoked get method on an empty return" in {
    LogicController.endTurn()
    a[NoSuchElementException] shouldBe thrownBy(GameUtils.getSpecialCellFromPlayerPosition.get)
  }
