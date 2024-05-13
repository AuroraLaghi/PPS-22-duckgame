package it.unibo.pps.duckgame.utils.resources

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ImgResourcesTest extends AnyFlatSpec with should.Matchers:
  private val NUM_IMG_RESOURCES = 14
  private val GAMEBOARD_POSITION = 0
  private val NUM_TOKEN = 6
  private val LAST_POSITION = 13

  "Resource path for gameboard img file" should "be correct" in {
    ImgResources.GAMEBOARD.path shouldBe "/img/gameboard.png"
  }

  "Resource path for dices imgs files" should "be correct" in {
    ImgResources.DICE_1.path shouldBe "/img/dice/1.png"
  }

  "imgResources class" should "return the right path at given position" in {
    ImgResources.fromOrdinal(GAMEBOARD_POSITION) shouldBe ImgResources.GAMEBOARD
    ImgResources.fromOrdinal(LAST_POSITION) shouldBe ImgResources.DICE_6
  }

  "If asked an element in a non-existent position it" should "throw an exception" in {
    an[NoSuchElementException] should be thrownBy {
      ImgResources.fromOrdinal(ImgResources.values.length + 1)
    }
  }

  "Img resources of tokens" should "be 6 elements" in {
    ImgResources.values.filter(v => v.path.contains("token")).toList.size shouldBe NUM_TOKEN
  }

  "Resources obtained" should "be non-null" in {
    getClass.getResource(ImgResources.TOKEN_YELLOW.path) should not be null
  }

  "Number of resources" should "have length of resources.values" in {
    ImgResources.values.length shouldBe NUM_IMG_RESOURCES
  }
