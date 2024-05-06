package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.{EndGameController, GameBoardController, GameReader}
import it.unibo.pps.duckgame.model.{Player, Token}
import it.unibo.pps.duckgame.utils.{FxmlUtils, GameUtils}
import it.unibo.pps.duckgame.utils.resources.CssResources.GAME_STYLE
import it.unibo.pps.duckgame.utils.resources.ImgResources
import javafx.beans.property.SimpleObjectProperty
import scalafx.beans.property.StringProperty
import javafx.{fxml, geometry}
import javafx.fxml.{FXML, Initializable}
import javafx.geometry.{Insets, Pos}
import javafx.scene.layout.{BorderPane, ColumnConstraints, GridPane, HBox, RowConstraints, VBox}
import javafx.stage.Screen
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.{Button, Label, ListView, TableColumn, TableView, TextArea}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.{image, layout}

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
  private var currentPlayer: Label = _

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

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var playersTable: TableView[Player] = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var playerNameColumn: TableColumn[Player, String] = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var playerTokenColumn: TableColumn[Player, ImageView] = _

  @FXML
  @SuppressWarnings(
    Array("org.wartremover.warts.Null", "org.wartremover.warts.Var")
  )
  private var movementMessage: TextArea = _

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

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    FxmlUtils.initUIElements(pane, gameBoard, GAME_STYLE, FxmlUtils.DEFAULT_WIDTH_PERC, FxmlUtils.DEFAULT_HEIGHT_PERC)
    initializeCellGrid()
    val menuWidth = FxmlUtils.getResolution._1 - pane.getPrefHeight
    actionsMenu.setPrefWidth(menuWidth / 2)
    playerListBox.setPrefWidth(menuWidth / 2)
    currentPlayer.setPrefWidth(menuWidth / 2)
    playersTable.setPrefWidth(menuWidth)
    movementMessage.setPrefWidth(menuWidth)
    currentPlayer.setAlignment(geometry.Pos.CENTER)
    setCurrentPlayer()
    GameReader.players.foreach { p =>
      val tokenImage = new ImageView(new Image(getClass.getResource(p.token.img.path).toString))
      tokensMap += (p.token -> tokenImage)
      setImageViewDimensions(tokenImage)
      updatePlayerPosition(p)
    }

    setDiceImage(diceImage1)
    setDiceImage(diceImage2)

    playerNameColumn.setCellValueFactory(p => StringProperty(p.getValue.name))

    val tokensMapForTable: Map[Token, ImageView] = tokensMap.foldLeft(Map.empty[Token, ImageView]) { (acc, entry) =>
      val (token, imageView) = entry
      val imageTokenTable = new ImageView(imageView.getImage)
      setImageViewDimensions(imageTokenTable)
      acc + (token -> imageTokenTable)
    }
    playerTokenColumn.setCellValueFactory(p => SimpleObjectProperty(tokensMapForTable(p.getValue.token)))
    updatePlayersTable()
    GameBoardController.view = this

  def quitButtonClick(): Unit =
    tokensMap(GameReader.currentPlayer.token).setDisable(true)
    movementMessage.setText("Il giocatore " + GameReader.currentPlayer.name + " ha abbandonato la partita.")
    GameBoardController.currentPlayerQuit()
    if GameReader.players.nonEmpty then
      setCurrentPlayer()
      setButtonsForTurnEnding(false)
      updatePlayersTable()

  private def setButtonsForTurnEnding(can: Boolean): Unit =
    endTurnButton.setDisable(!can)
    throwDiceButton.setDisable(can)

  def throwDiceButtonClick(): Unit =
    movementMessage.setText("")
    val (dice1, dice2) = GameBoardController.throwDice()
    afterThrow(dice1, dice2)

  def endTurnButtonClick(): Unit =
    GameBoardController.endTurn()
    setCurrentPlayer()
    setButtonsForTurnEnding(false)

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
    val (col, row) = GameUtils.getNthSlotFromCell(cellGrid.getChildren.size() + 1, (N_COLS_IN_CELL, N_ROWS_IN_CELL))
    if !cellGrid.getChildren.contains(tokensMap(player.token)) then cellGrid.add(tokensMap(player.token), col, row)

  private def afterThrow(dice1: Int, dice2: Int): Unit =
    updatePlayerPosition(GameReader.currentPlayer)
    updateDiceImg(dice1, dice2)
    val position: Int = GameReader.currentPlayer.actualPosition
    playerMovement("Il giocatore è sulla casella: " + position.toString)
    if GameBoardController.checkVictory() then GameBoardController.showVictory()
    else if EndGameController.isGameLocked then GameBoardController.showGameLocked()
      else if dice1 != dice2 || position == 19 || position == 31 || position == 52 then setButtonsForTurnEnding(true)
      else playerMovement("Il giocatore ha fatto doppio, deve ritirare.")
  def playerMovement(message: String) : Unit =
    movementMessage.appendText(message + "\n")

  private def setCurrentPlayer(): Unit =
    currentPlayer.setText("É il turno di: " + GameReader.currentPlayer.name)

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

  private def updatePlayersTable(): Unit =
    val rowHeight = 35
    playersTable.getItems.clear()
    GameReader.players.foreach(p => playersTable.getItems.add(p))
    val numRows = playersTable.getItems.size() + 1
    val newHeight = numRows * rowHeight
    playersTable.setPrefHeight(newHeight)
