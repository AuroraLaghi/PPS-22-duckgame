package it.unibo.pps.duckgame.utils.resources

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TxtResourcesTest extends AnyFlatSpec with should.Matchers:

  private val NUM_TXT_RESOURCES = 1
  private val SPECIAL_CELLS_POSITION = 0

  "Resource path for special cells txt file" should "be correct" in {
    TxtResources.SPECIAL_CELL_TXT.path shouldBe "/config/specialCells.txt"
  }

  "TxtResources class" should "return the right path at given position" in {
    TxtResources.fromOrdinal(SPECIAL_CELLS_POSITION) shouldBe TxtResources.SPECIAL_CELL_TXT
  }

  "If asked an element in a non-existent position it" should "throw an exception" in {
    an[NoSuchElementException] should be thrownBy {
      TxtResources.fromOrdinal(TxtResources.values.length + 1)
    }
  }

  "Resources obtained" should "be non-null" in {
    getClass.getResource(TxtResources.SPECIAL_CELL_TXT.path) should not be null
  }

  "Number of resources" should "have length of resources.values" in {
    TxtResources.values.length shouldBe NUM_TXT_RESOURCES
  }
