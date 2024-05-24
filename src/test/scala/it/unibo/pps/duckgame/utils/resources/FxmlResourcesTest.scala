package it.unibo.pps.duckgame.utils.resources

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class FxmlResourcesTest extends AnyFlatSpec with should.Matchers:

  private val START_MENU_POSITION = 0
  private val GAMEVIEW_POSITION = 1
  private val NUM_FXML_RESOURCES = 3

  "Resource path for starting menu" should "be correct" in {
    FxmlResources.START_MENU.path shouldBe "/fxml/StartMenuFXML.fxml"
  }

  "Resource path for game view" should "be correct" in {
    FxmlResources.GAME_VIEW.path shouldBe "/fxml/GameFXML.fxml"
  }

  "FxmlResources" should "return the right path at given position" in {
    FxmlResources.fromOrdinal(START_MENU_POSITION) shouldBe FxmlResources.START_MENU
    FxmlResources.fromOrdinal(GAMEVIEW_POSITION) shouldBe FxmlResources.GAME_VIEW
  }

  "If asked an element in a non-existent position it" should "throw an exception" in {
    an [NoSuchElementException] should be thrownBy{
      FxmlResources.fromOrdinal(FxmlResources.values.length + 1)
    }
  }

  "Resources obtained" should "be non-null" in {
    getClass.getResource(FxmlResources.START_MENU.path) should not be null
    getClass.getResource(FxmlResources.GAME_VIEW.path) should not be null
  }
  
  "Number of resources" should "have length of resources.values" in {
    FxmlResources.values.length shouldBe NUM_FXML_RESOURCES
  }
  
  





