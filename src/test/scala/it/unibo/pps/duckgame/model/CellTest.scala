package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class CellTest extends AnyFlatSpec with should.Matchers:
  private def SKELETON_CELL = 58
  private def STARTING_CELL = 0
  private def FINAL_CELL = 63

  "Skeleton's special cell in the gameboard cells's list" should "have index 58" in {
    GameReader.gameBoard.specialCells.find(cell => cell.number == SKELETON_CELL) shouldBe defined
  }

  "Standard cells" should "be returned as they are" in {
    GameReader.gameBoard.gameBoardList(STARTING_CELL) shouldBe a [Cell]
  }

  "GameBoard special cells list" should "contain correct cells' types" in {
    GameReader.gameBoard.specialCells.find(_.number == FINAL_CELL).orNull.specialCellType shouldBe SpecialCellType.FINAL
  }


