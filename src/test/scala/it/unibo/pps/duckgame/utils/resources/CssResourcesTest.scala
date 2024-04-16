package it.unibo.pps.duckgame.utils.resources

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class CssResourcesTest extends AnyFlatSpec with should.Matchers:

  private val GAMESTYLE_POSITION = 0
  private val NUM_CSS_RESOURCES = 1

  "Resource path for css file" should "be correct" in {
    CssResources.GAME_STYLE.path shouldBe "/css/UIStyle.css"
  }

  "CssResources" should "return the right path at given position" in {
    CssResources.fromOrdinal(GAMESTYLE_POSITION) shouldBe CssResources.GAME_STYLE
  }

  "If asked an element in a non-existent position it" should "throw an exception" in {
    an[NoSuchElementException] should be thrownBy {
      CssResources.fromOrdinal(CssResources.values.length + 1)
    }
  }

  "Resources obtained" should "be non-null" in {
    getClass.getResource(CssResources.GAME_STYLE.path) should not be null
  }

  "Number of resources" should "have length of resources.values" in {
    CssResources.values.length shouldBe NUM_CSS_RESOURCES
  }