package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.{EndGameController, GameBoardController, GameReader}
import it.unibo.pps.duckgame.model.{Player, Token}
import it.unibo.pps.duckgame.utils.{FxmlUtils, GameUtils}
import it.unibo.pps.duckgame.utils.resources.CssResources.GAME_STYLE
import it.unibo.pps.duckgame.utils.resources.ImgResources
import javafx.{fxml, geometry}
import javafx.fxml.{FXML, Initializable}
import javafx.geometry.{Insets, Pos}
import javafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, HBox, RowConstraints, VBox}
import javafx.stage.Screen
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Button, Label, ListView, TableColumn, TableView}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.{image, layout}
import org.scalactic.TypeCheckedTripleEquals.convertToCheckingEqualizer

import scala.collection.immutable.Map as MMap
import java.net.URL
import java.util.ResourceBundle
import scala.::

class GameBoardView extends Initializable:

  private def DEFAULT_SPACING = 10
  private def N_COLS_IN_CELL = 3
  private def N_ROWS_IN_CELL = 2

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var actionsMenu: VBox = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var turnLabel: Label = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var throwDiceButton: Button = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var endTurnButton: Button = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var exitButton: Button = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var gameBoard: ImageView = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var pane: BorderPane = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var playerListBox: VBox = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var mainGrid: GridPane = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var currentPlayer: VBox = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var dicesView: VBox = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var diceImage1: ImageView = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var diceImage2: ImageView = _

  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var playersHBox: MMap[String, HBox] = MMap.empty

  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var cellsGrid: MMap[(Int, Int), GridPane] = MMap.empty

  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var tokensMap: MMap[Token, ImageView] = MMap.empty

  private val currentPlayerLabel: Label = new Label()


  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    FxmlUtils.initUIElements(pane, gameBoard, GAME_STYLE, FxmlUtils.DEFAULT_WIDTH_PERC, FxmlUtils.DEFAULT_HEIGHT_PERC)
    initializeCellGrid()
    val menuWidth = FxmlUtils.getResolution._1 - pane.getPrefHeight
    actionsMenu.setPrefWidth(menuWidth / 2)
    playerListBox.setPrefWidth(menuWidth / 2)
    currentPlayer.setPrefWidth(menuWidth / 2)
    playerListBox.getChildren.add(new Label("Elenco giocatori:"))
    currentPlayer.getChildren.add(new Label("È il turno di"))
    currentPlayer.getChildren.add(currentPlayerLabel)
    currentPlayer.setSpacing(DEFAULT_SPACING)
    currentPlayer.setAlignment(geometry.Pos.CENTER)
    setCurrentPlayer()
    GameReader.players.foreach { p =>
      createPlayerBox(p)
      val tokenImage = new ImageView(new Image(getClass.getResource(p.token.img.path).toString))
      tokensMap += (p.token -> tokenImage)
      setImageViewDimensions(tokenImage)
      updatePlayerPosition(p)
    }

    setDiceImage(diceImage1)
    setDiceImage(diceImage2)


  def quitButtonClick(): Unit =
    tokensMap(GameReader.currentPlayer.token).setDisable(true)
    playersHBox(GameReader.currentPlayer.name).setDisable(true)
    GameBoardController.currentPlayerQuit()
    if GameReader.players.nonEmpty then setButtonsForTurnEnding(false)

  private def setButtonsForTurnEnding(can: Boolean): Unit =
    endTurnButton.setDisable(!can)
    throwDiceButton.setDisable(can)

  def throwDiceButtonClick(): Unit =
    val (dice1, dice2) = GameBoardController.throwDice()
    afterThrow(dice1, dice2)

  def endTurnButtonClick(): Unit =
    GameBoardController.endTurn()
    setCurrentPlayer()
    setButtonsForTurnEnding(false)

  private def createPlayerBox(player: Player): Unit =
    val playerHBox: HBox = new HBox()
    playerListBox.getChildren.add(playerHBox)
    val playerLabel: Label = new Label(player.name)
    playerHBox.getChildren.add(playerLabel)
    playerHBox.setSpacing(DEFAULT_SPACING)
    playerHBox.setAlignment(geometry.Pos.CENTER)
    playersHBox += (player.name -> playerHBox)

  private def initializeCellGrid(): Unit =
    val CONSTRAINT_PERC = 50
    for
      i <- 0 to GameUtils.CELLS_IN_SIDE
      j <- 0 to GameUtils.CELLS_IN_SIDE
    do
      val tempGrid = new GridPane()
      tempGrid.setPadding(new Insets(2, 5, 2, 5))
      spawnColumns(tempGrid, N_COLS_IN_CELL)
      spawnRows(tempGrid, N_ROWS_IN_CELL)

      mainGrid.add(tempGrid, i, j)
      cellsGrid += ((i, j) -> tempGrid)

      def spawnColumns(grid: GridPane, numCol: Int): Unit =
        val col = new ColumnConstraints()
        col.setPercentWidth(CONSTRAINT_PERC)
        for _ <- 0 until numCol do grid.getColumnConstraints.add(col)

      def spawnRows(grid: GridPane, numRows: Int): Unit =
        val row = new RowConstraints()
        row.setPercentHeight(CONSTRAINT_PERC)
        for _ <- 0 until numRows do grid.getRowConstraints.add(row)

  private def updatePlayerPosition(player: Player): Unit =
    val cellGrid = cellsGrid(GameUtils.getCoordinateFromPosition(player.actualPosition))
    val (col, row) = getFirstFreeCellStartingFrom(cellGrid, cellGrid.getChildren.size(), (0, 1))
    if !cellGrid.getChildren.contains(tokensMap(player.token)) then cellGrid.add(tokensMap(player.token), col, row)

  private def getFirstFreeCellStartingFrom(gridPane: GridPane, nthCell: Int, startingCell: (Int, Int)): (Int, Int) =
    GameUtils.getNthCellInGridWithStartingPos(nthCell + 1, (N_COLS_IN_CELL, N_ROWS_IN_CELL), startingCell)

  private def afterThrow(dice1: Int, dice2: Int): Unit =
    updatePlayerPosition(GameReader.currentPlayer)
    updateDiceImg(dice1, dice2)
    val position: Int = GameReader.currentPlayer.actualPosition
    if GameBoardController.checkVictory() then GameBoardController.showVictory()
    else if EndGameController.isGameLocked then GameBoardController.showGameLocked()
    else if dice1 != dice2 || position == 19 || position == 31 || position == 52 then setButtonsForTurnEnding(true)

  private def setCurrentPlayer(): Unit =
    currentPlayerLabel.setText(GameReader.currentPlayer.name)

  private def setImageViewDimensions(imgView: ImageView): Unit =
    imgView.setPreserveRatio(false)
    imgView.setFitWidth(gameBoard.getFitWidth / (GameUtils.CELLS_IN_SIDE + 1) / N_COLS_IN_CELL)
    imgView.setFitHeight(gameBoard.getFitHeight / (GameUtils.CELLS_IN_SIDE + 1) / N_COLS_IN_CELL)

  private def setDiceImage(diceImage: ImageView): Unit =
    diceImage.setFitWidth(pane.getPrefHeight / (GameReader.gameBoard.size / GameUtils.CELLS_IN_SIDE + 1))

  private def updateDiceImg(dice1: Int, dice2: Int): Unit =
    updateSingleDiceImg(dice1, diceImage1)
    updateSingleDiceImg(dice2, diceImage2)

  private def updateSingleDiceImg(dice: Int, diceImage: ImageView): Unit =
    val dicePath: String = ImgResources.valueOf("DICE_" + dice.toString).path
    diceImage.setImage(new Image(getClass.getResource(dicePath).toString))