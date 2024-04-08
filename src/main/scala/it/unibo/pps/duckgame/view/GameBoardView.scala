package it.unibo.pps.duckgame.view
import it.unibo.pps.duckgame.controller.{GameController, GameStats}
import it.unibo.pps.duckgame.model.Player
import it.unibo.pps.duckgame.utils.FxmlUtils
import it.unibo.pps.duckgame.utils.resources.ImgResources
import javafx.fxml
import javafx.fxml.{FXML, Initializable}
import javafx.geometry.Pos
import javafx.scene.{control, image, layout}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
import scalafx.stage.Screen

import java.net.URL
import java.util.ResourceBundle

class GameBoardView extends Initializable:
  @FXML
  private var playerList: VBox = _

  @FXML
  private var bottomMenu: VBox = _

  @FXML
  private var turnLabel: Label = _

  @FXML
  private var throwDiceButton: Button = _

  @FXML
  private var endTurnButton: Button = _

  @FXML
  private var exitButton: Button = _

  @FXML
  private var gameBoard: ImageView = _

  @FXML
  private var pane: BorderPane = _

  private var playersHBox: Map[Player,HBox] = Map.empty

  @FXML
  private var mainGrid: GridPane = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    val imageURL = new Image(getClass.getResource(ImgResources.GAMEBOARD.path).toString)
    gameBoard.setImage(imageURL)
    gameBoard.setPreserveRatio(false)
    setResolution()
    temp()

  private def setResolution(): Unit =
    FxmlUtils.setResolution(pane, 0.9, 0.9)
    val(width,height) = FxmlUtils.getResolution

    val gameBoardSize = pane.getPrefHeight

    gameBoard.setFitWidth(gameBoardSize)
    gameBoard.setFitHeight(gameBoardSize)
    val menuWidth = width - gameBoardSize
    bottomMenu.setPrefWidth(menuWidth / 2)
    playerList.setPrefWidth(menuWidth / 2)

  private def temp(): Unit =
    val p1 = Player("p1")
    val p2 = Player("p2")

    GameController.addPlayer(p1)
    GameController.addPlayer(p2)
    GameController.startGame()

  def quitButtonClick(): Unit =
    playersHBox(GameStats.currentPlayer).setDisable(true)
    GameController.currentPlayerQuit()

  def throwDiceButtonClick(): Unit =
    val (dice1, dice2) = GameController.throwDice()
    println("Dices: " + dice1 + " " + dice2)
    endTurnButton.setDisable(false)
    throwDiceButton.setDisable(true)

  def endTurnButtonClick(): Unit =
    GameController.endTurn()
    endTurnButton.setDisable(true)
    throwDiceButton.setDisable(false)

  private def createPlayerBox(player: Player): Unit =
    val playerHBox: HBox = new HBox()
    playerList.children.add(playerHBox)
    val playerLabel: Label = new Label(player.name)
    playerHBox.children.add(playerLabel)
    playerHBox.setSpacing(5)
    playerHBox.setAlignment(Pos.CENTER)
    playersHBox += (player -> playerHBox)





