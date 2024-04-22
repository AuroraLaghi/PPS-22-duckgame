package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.StartMenuController
import it.unibo.pps.duckgame.utils.FxmlUtils
import it.unibo.pps.duckgame.utils.resources.CssResources.GAME_STYLE
import it.unibo.pps.duckgame.utils.resources.{CssResources, FxmlResources, ImgResources}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.image.Image
import javafx.scene.layout.{AnchorPane, BorderPane}
import javafx.{event as jfxe, fxml as jfxf, scene as jfxs}
import javafx.scene.{control as jfxsc, layout as jfxsl}
import scalafx.scene.Scene

import java.net.URL
import java.util.ResourceBundle
import scalafx.Includes.*
import scalafx.scene.control.Button
import javafx.scene.image.ImageView
import scalafx.stage.Screen

class StartMenuView extends Initializable:
  
  private def WIDTH = 0.4
  private def HEIGHT = 0.4

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var pane: BorderPane = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var startButton: Button = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var exitButton: Button = _

  @FXML
  private var logo: ImageView = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    FxmlUtils.initUIElements(pane, GAME_STYLE, WIDTH, HEIGHT)
    logo.setImage(new Image(getClass.getResource(ImgResources.LOGO.path).toString))
    logo.setFitWidth(FxmlUtils.getResolution._1 * WIDTH)
    logo.setFitHeight(FxmlUtils.getResolution._2 * HEIGHT)

  def playGame(): Unit =
    StartMenuController.playGame()

  def exitGame(): Unit =
    StartMenuController.closeGame()


