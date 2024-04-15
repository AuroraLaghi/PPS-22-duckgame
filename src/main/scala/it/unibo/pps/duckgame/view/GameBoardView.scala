package it.unibo.pps.duckgame.view
import it.unibo.pps.duckgame.controller.{GameController, GameStats}
import it.unibo.pps.duckgame.model.Player
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
import javafx.scene.layout

import scala.collection.mutable.{ListBuffer, Map as MMap}
import java.net.URL
import java.util.ResourceBundle
import scala.::

class GameBoardView extends Initializable:

  private def DEFAULT_SPACING = 10
  private def N_COLS_IN_CELL = 3
  private def N_ROWS_IN_CELL = 2

  @FXML
  private var actionsMenu: VBox = _

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

  private val playersHBox: MMap[String,HBox] = MMap.empty

  @FXML
  private var playerListBox: VBox = _

  @FXML
  private var mainGrid: GridPane = _

  @FXML
  private var currentPlayer: VBox = _

  private val currentPlayerLabel: Label = new Label()

  private val cellsGrid: MMap[(Int, Int), GridPane] = MMap.empty

  private val nameLabel: MMap[String, Label] = MMap.empty

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    GameController.startGame()
    FxmlUtils.initUIElements(pane, gameBoard, GAME_STYLE, FxmlUtils.DEFAULT_WIDTH_PERC,
      FxmlUtils.DEFAULT_HEIGHT_PERC)
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
    GameStats.players.foreach(p => {
      createPlayerBox(p)
      nameLabel.addOne(p.name, new Label(p.name))
      updatePlayerPosition(p)
    })

  def quitButtonClick(): Unit =
    nameLabel(GameStats.currentPlayer.name).setDisable(true)
    playersHBox(GameStats.currentPlayer.name).setDisable(true)
    GameController.currentPlayerQuit()
    if GameStats.players.nonEmpty then
      setButtonsForTurnEnding(false)

  private def setButtonsForTurnEnding(can: Boolean): Unit =
    endTurnButton.setDisable(!can)
    throwDiceButton.setDisable(can)

  def throwDiceButtonClick(): Unit =
    val (dice1, dice2) = GameController.throwDice()
    afterThrow(dice1, dice2)

  def endTurnButtonClick(): Unit =
    GameController.endTurn()
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
      cellsGrid.addOne((i, j), tempGrid)

      def spawnColumns(grid: GridPane, numCol: Int): Unit =
        val col = new ColumnConstraints()
        col.setPercentWidth(CONSTRAINT_PERC)
        for _ <- 0 until numCol
        do
          grid.getColumnConstraints.add(col)

      def spawnRows(grid: GridPane, numRows: Int): Unit =
        val row = new RowConstraints()
        row.setPercentHeight(CONSTRAINT_PERC)
        for _ <- 0 until numRows
        do
          grid.getRowConstraints.add(row)

  private def updatePlayerPosition(player: Player): Unit =
    val cellGrid = cellsGrid(GameUtils.getCoordinateFromPosition(player.actualPosition))
    val (col, row) = getFirstFreeCellStartingFrom(cellGrid, cellGrid.getChildren.size(), (0, 1))
    if !cellGrid.getChildren.contains(nameLabel(player.name)) then
      cellGrid.add(nameLabel(player.name), col, row)

  private def getFirstFreeCellStartingFrom(gridPane: GridPane, nthCell: Int, startingCell: (Int, Int)): (Int, Int) =
    GameUtils.getNthCellInGridWithStartingPos(nthCell + 1, (N_COLS_IN_CELL, N_ROWS_IN_CELL), startingCell)

  private def afterThrow(dice1: Int, dice2: Int): Unit =
    updatePlayerPosition(GameStats.currentPlayer)
    nameLabel.foreach(t =>
      GameStats.players.find(p => p.name == t._1) match
        case None => t._2.setDisable(true)
        case _ =>
    )
    if dice1 != dice2 then
      setButtonsForTurnEnding(true)

  private def setCurrentPlayer(): Unit =
    currentPlayerLabel.setText(GameStats.currentPlayer.name)

