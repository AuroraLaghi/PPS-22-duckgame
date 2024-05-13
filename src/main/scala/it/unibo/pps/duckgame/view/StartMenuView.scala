package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.view.StartMenuController
import it.unibo.pps.duckgame.utils.FxmlUtils
import it.unibo.pps.duckgame.utils.resources.CssResources.GAME_STYLE
import it.unibo.pps.duckgame.utils.resources.{CssResources, FxmlResources, ImgResources}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.{AnchorPane, BorderPane}
import javafx.scene.{control as jfxsc, layout as jfxsl}
import javafx.{event as jfxe, fxml as jfxf, scene as jfxs}
import scalafx.Includes.*
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.stage.Screen

import java.net.URL
import java.util.ResourceBundle

/** This class represents the Start Menu view of the game */
class StartMenuView extends Initializable:
  private def WIDTH = 0.4
  private def HEIGHT = 0.4
  @FXML
  private var pane: BorderPane = _
  @FXML
  private var logo: ImageView = _

  /** This method is called after the FXML view is loaded.
    *
    * @param url
    *   The URL of the FXML file.
    * @param resourceBundle
    *   The resource bundle used for localization (optional).
    */
  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    FxmlUtils.initUIElements(pane, None, GAME_STYLE, WIDTH, HEIGHT)
    logo.setImage(new Image(getClass.getResource(ImgResources.LOGO.path).toString))
    logo.setFitWidth(FxmlUtils.getResolution._1 * WIDTH)
    logo.setFitHeight(FxmlUtils.getResolution._2 * HEIGHT)

  /** Handles the click of the "Play Game" button. Delegates the game start logic to the StartMenuController */
  def playGame(): Unit =
    StartMenuController.playGame()

  /** Handles the click of the "Exit Game" button. Delegates the game exit logic to the StartMenuController */
  def exitGame(): Unit =
    StartMenuController.closeGame()
