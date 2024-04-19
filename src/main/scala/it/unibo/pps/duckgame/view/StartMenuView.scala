package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.GameController
import it.unibo.pps.duckgame.utils.FxmlUtils
import it.unibo.pps.duckgame.utils.resources.{CssResources, FxmlResources}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.layout.AnchorPane
import javafx.{event as jfxe, fxml as jfxf, scene as jfxs}
import javafx.scene.{control as jfxsc, layout as jfxsl}
import scalafx.scene.Scene
import java.net.URL
import java.util.ResourceBundle
import scalafx.Includes.*
import scalafx.scene.control.Button
import scalafx.stage.Screen

class StartMenuView extends Initializable:

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var startButton: Button = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var exitButton: Button = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var startMenuPane: AnchorPane = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    startMenuPane.getStylesheets.add(getClass.getResource(CssResources.GAME_STYLE.path).toExternalForm)
    FxmlUtils.setPaneResolution(startMenuPane, 0.6, 0.6)

  def playGame(): Unit =
    FxmlUtils.changeScene(FxmlResources.PLAYER_MENU.path)

  def exitGame(): Unit =
    GameController.exitGame()
