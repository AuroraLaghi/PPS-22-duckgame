package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.view.GameBoardController
import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.controller.logic.EndGameController
import it.unibo.pps.duckgame.model.{Player, Token}
import it.unibo.pps.duckgame.utils.resources.CssResources.GAME_STYLE
import it.unibo.pps.duckgame.utils.resources.ImgResources
import it.unibo.pps.duckgame.utils.{FxmlUtils, GameUtils}
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.{FXML, Initializable}
import javafx.geometry.{Insets, Pos}
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.*
import javafx.scene.{image, layout}
import javafx.stage.Screen
import javafx.{fxml, geometry}
import scalafx.beans.property.StringProperty

import java.net.URL
import java.util.ResourceBundle
import scala.collection.immutable.Map as MMap

/** This class represents the Game Board view of the game */
class GameBoardView extends Initializable:

  private def NMENU = 2
  private def N_COLS_IN_CELL = 3
  private def N_ROWS_IN_CELL = 2

  @FXML
  private var actionsMenu: VBox = _

  @FXML
  private var throwDiceButton: Button = _

  @FXML
  private var endTurnButton: Button = _

  @FXML
  private var gameBoard: ImageView = _

  @FXML
  private var pane: BorderPane = _

  @FXML
  private var playerListBox: VBox = _

  @FXML
  private var mainGrid: GridPane = _

  @FXML
  private var currentPlayer: Label = _

  @FXML
  private var diceImage1: ImageView = _

  @FXML
  private var diceImage2: ImageView = _

  @FXML
  private var playersTable: TableView[Player] = _

  @FXML
  private var playerNameColumn: TableColumn[Player, String] = _

  @FXML
  private var playerTokenColumn: TableColumn[Player, ImageView] = _

  @FXML
  private var movementMessage: TextArea = _

  private var cellsGrid: MMap[(Int, Int), GridPane] = MMap.empty

  private var tokensMap: MMap[Token, ImageView] = MMap.empty

  /** This method is called after the FXML view is loaded.
    *
    * @param url
    *   The URL of the FXML file.
    * @param resourceBundle
    *   The resource bundle used for localization (optional).
    */
  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    FxmlUtils.initUIElements(
      pane,
      Some(gameBoard),
      GAME_STYLE,
      FxmlUtils.DEFAULT_WIDTH_PERC,
      FxmlUtils.DEFAULT_HEIGHT_PERC
    )
    initializeCellGrid()
    val menuWidth = FxmlUtils.getResolution._1 - pane.getPrefHeight
    actionsMenu.setPrefWidth(menuWidth / NMENU)
    playerListBox.setPrefWidth(menuWidth / NMENU)
    currentPlayer.setPrefWidth(menuWidth / NMENU)
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

  /** Handles the click of the "Quit" button (likely). Disables the current player's token on the board, displays a
    * message, and updates the game state.
    */
  def quitButtonClick(): Unit =
    tokensMap(GameReader.currentPlayer.token).setDisable(true)
    movementMessage.setText("Il giocatore " + GameReader.currentPlayer.name + " ha abbandonato la partita.")
    GameBoardController.currentPlayerQuit()
    if GameReader.players.nonEmpty then
      setCurrentPlayer()
      setButtonsForTurnEnding(false)
      updatePlayersTable()

  /** Enables or disables buttons related to ending a turn based on the provided flag.
    *
    * @param can
    *   A boolean indicating whether the buttons should be enabled.
    */
  private def setButtonsForTurnEnding(can: Boolean): Unit =
    endTurnButton.setDisable(!can)
    throwDiceButton.setDisable(can)

  /** Handles the click of the "Throw Dice" button (likely). Clears any previous message, throws the dice using the game
    * board controller, and updates the game state.
    */
  def throwDiceButtonClick(): Unit =
    movementMessage.setText("")
    val (dice1, dice2) = GameBoardController.throwDice()
    afterThrow(dice1, dice2)

  /** Handles the click of the "End Turn" button (likely). Ends the current player's turn using the game board
    * controller, sets the new current player, and updates button states.
    */
  def endTurnButtonClick(): Unit =
    GameBoardController.endTurn()
    setCurrentPlayer()
    setButtonsForTurnEnding(false)

  /** Initializes the grid of cells on the game board */
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

      /** Creates and adds columns to a GridPane with a specified width.
        *
        * This method is likely used to create a grid-like structure within a cell on the game board.
        *
        * @param grid
        *   The GridPane to which the columns will be added.
        * @param numCol
        *   The number of columns to create.
        */
      def spawnColumns(grid: GridPane, numCol: Int): Unit =
        val col = new ColumnConstraints()
        col.setPercentWidth(CONSTRAINT_PERC)
        for _ <- 0 until numCol do grid.getColumnConstraints.add(col)

      /** Creates and adds rows to a GridPane with a specified width.
        *
        * This method is likely used to create a grid-like structure within a cell on the game board.
        *
        * @param grid
        *   The GridPane to which the rows will be added.
        * @param numRows
        *   The number of rows to create.
        */
      def spawnRows(grid: GridPane, numRows: Int): Unit =
        val row = new RowConstraints()
        row.setPercentHeight(CONSTRAINT_PERC)
        for _ <- 0 until numRows do grid.getRowConstraints.add(row)

  /** Updates the player's position on the game board UI.
    *
    * @param player
    *   The player whose position needs to be updated.
    */
  private def updatePlayerPosition(player: Player): Unit =
    val cellGrid = cellsGrid(GameUtils.getCoordinateFromPosition(player.actualPosition))
    val (col, row) = GameUtils.getNthSlotFromCell(cellGrid.getChildren.size() + 1)(N_COLS_IN_CELL, N_ROWS_IN_CELL)
    if !cellGrid.getChildren.contains(tokensMap(player.token)) then cellGrid.add(tokensMap(player.token), col, row)

  /** Handles updates after the dice are thrown.
    *
    * @param dice1
    *   The value of the first die.
    * @param dice2
    *   The value of the second die.
    */
  private def afterThrow(dice1: Int, dice2: Int): Unit =
    updatePlayerPosition(GameReader.currentPlayer)
    updateDiceImg(dice1, dice2)
    val position: Int = GameReader.currentPlayer.actualPosition
    playerMovement("Il giocatore è sulla casella: " + position.toString)
    if GameBoardController.checkVictory() then GameBoardController.showVictory()
    else if EndGameController.isGameLocked then GameBoardController.showGameLocked()
    else if dice1 != dice2 || position == 19 || position == 31 || position == 52 then setButtonsForTurnEnding(true)
    else playerMovement("Dal lancio dei dadi si è ottenuto un doppio, quindi il giocatore può effettuare un altro tiro")

  /** Updates the movement message text area with the provided message.
    *
    * This method is likely used to display messages related to player movement on the game board.
    *
    * @param message
    *   The message to be appended to the movement text area.
    */
  def playerMovement(message: String): Unit =
    movementMessage.appendText(message + "\n")

  /** Sets the current player indicator text.
    *
    * This method likely updates a UI element that displays the name of the player whose turn it is.
    */
  private def setCurrentPlayer(): Unit =
    currentPlayer.setText("É il turno di: " + GameReader.currentPlayer.name)

  /** Sets the dimensions of an ImageView based on the game board size and cell properties.
    *
    * @param imgView
    *   The ImageView whose dimensions will be set.
    */
  private def setImageViewDimensions(imgView: ImageView): Unit =
    imgView.setPreserveRatio(false)
    imgView.setFitWidth(gameBoard.getFitWidth / (GameUtils.CELLS_IN_SIDE + 1) / N_COLS_IN_CELL)
    imgView.setFitHeight(gameBoard.getFitHeight / (GameUtils.CELLS_IN_SIDE + 1) / N_COLS_IN_CELL)

  /** Sets the dimensions of a dice image view based on the game board size and number of cells.
    *
    * @param diceImage
    *   The ImageView representing the dice that will be resized.
    */
  private def setDiceImage(diceImage: ImageView): Unit =
    diceImage.setFitWidth(pane.getPrefHeight / (GameReader.gameBoard.size / GameUtils.CELLS_IN_SIDE + 1))

  /** Updates the images for both dice based on their rolled values.
    *
    * @param dice1
    *   The value of the first die.
    * @param dice2
    *   The value of the second die.
    */
  private def updateDiceImg(dice1: Int, dice2: Int): Unit =
    updateSingleDiceImg(dice1, diceImage1)
    updateSingleDiceImg(dice2, diceImage2)

  /** Updates the image for a single die based on its rolled value.
    *
    * @param dice
    *   The value of the rolled die.
    * @param diceImage
    *   The ImageView representing the die to be updated.
    */
  private def updateSingleDiceImg(dice: Int, diceImage: ImageView): Unit =
    val dicePath: String = ImgResources.valueOf("DICE_" + dice.toString).path
    diceImage.setImage(new Image(getClass.getResource(dicePath).toString))

  /** Updates the players table on the UI.
    *
    * This method likely refreshes a UI element (playersTable) that displays information about the players in the game.
    */
  private def updatePlayersTable(): Unit =
    val rowHeight = 35
    playersTable.getItems.clear()
    GameReader.players.foreach(p => playersTable.getItems.add(p))
    val numRows = playersTable.getItems.size() + 1
    val newHeight = numRows * rowHeight
    playersTable.setPrefHeight(newHeight)
