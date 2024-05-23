package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.view.PlayerMenuController
import it.unibo.pps.duckgame.model.{Player, Token}
import it.unibo.pps.duckgame.utils.resources.CssResources
import it.unibo.pps.duckgame.utils.resources.CssResources.GAME_STYLE
import it.unibo.pps.duckgame.utils.{AlertUtils, FxmlUtils}
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.{control as jfxsc, layout as jfxsl}
import javafx.{fxml as jfxf, scene as jfxs}
import scalafx.beans.property.{ObjectProperty, StringProperty}

import java.net.URL
import java.util.ResourceBundle

/** This class represents the Players Menu view of the game */
@SuppressWarnings(Array("org.wartremover.warts.Null"))
class PlayersMenuView extends Initializable:

  private def WIDTH = 0.6

  private def HEIGHT = 0.6

  @FXML
  private var pane: BorderPane = _

  @FXML
  private var tableView: TableView[Player] = _

  @FXML
  private var playerNameColumn: TableColumn[Player, String] = _

  @FXML
  private var playerTokenColumn: TableColumn[Player, Token] = _

  @FXML
  private var addPlayerNameTextField: TextField = _

  @FXML
  private var addPlayerButton: Button = _

  @FXML
  private var removePlayerButton: Button = _

  @FXML
  private var addTokenComboBox: ComboBox[Token] = _

  /** This method is called after the FXML view is loaded.
    *
    * @param url
    *   The URL of the FXML file.
    * @param resourceBundle
    *   The resource bundle used for localization (optional).
    */
  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    FxmlUtils.initUIElements(pane, None, GAME_STYLE, WIDTH, HEIGHT)

    initializeTableView()
    updateTokenComboBox()
    removePlayerButton
      .disableProperty()
      .bind(Bindings.isEmpty(tableView.getSelectionModel.getSelectedItems))

  /** Initializes the TableView used to display player information */
  private def initializeTableView(): Unit =
    playerNameColumn.setCellValueFactory(p => StringProperty(p.getValue.name))
    playerTokenColumn.setCellValueFactory(p => ObjectProperty(p.getValue.token))
    tableView.setItems(FXCollections.observableArrayList[Player]())

  /** Adds a new player to the TableView and updates the game logic */
  private def addPlayerToTableView(): Unit =
    addPlayerNameTextField.getText match
      case name if name.isEmpty => AlertUtils.emptyPlayerNameWarning()
      case _ =>
        val newPlayer =
          Player(addPlayerNameTextField.getText, addTokenComboBox.getValue)
        tableView.getItems.add(newPlayer)
        PlayerMenuController.addPlayer(newPlayer)
        updateTokenComboBox()
        updateAddAndRemoveButton()
        addPlayerNameTextField.clear()

  /** Updates the available token options in the token combo box */
  private def updateTokenComboBox(): Unit =
    addTokenComboBox.getItems.setAll(
      FXCollections.observableArrayList(PlayerMenuController.availableToken()*)
    )
    addTokenComboBox.getSelectionModel.selectFirst()

  /** Updates the enabled/disabled state of the "Add Player" button based on
    * whether adding players is allowed
    */
  private def updateAddAndRemoveButton(): Unit =
    if PlayerMenuController.canAddPlayer then addPlayerButton.setDisable(false)
    else addPlayerButton.setDisable(true)

  /** Removes a selected player from the TableView and updates the game logic */
  def removePlayerFromTableView(): Unit =
    val selectedPlayer = tableView.getSelectionModel.getSelectedItem
    tableView.getItems.remove(selectedPlayer)
    PlayerMenuController.removePlayer(selectedPlayer)
    updateTokenComboBox()
    updateAddAndRemoveButton()

  /** Attempts to add a new player to the table view if adding players is
    * allowed
    */
  def checkAndAddPlayerToTableView(): Unit =
    if PlayerMenuController.canAddPlayer then addPlayerToTableView()

  /** Starts the game if there are enough players */
  def playGame(): Unit =
    if PlayerMenuController.canStartGame then PlayerMenuController.playGame()
    else AlertUtils.notEnoughPlayersWarning()

  /** Exits the game by delegating to the PlayerMenuController */
  def exitGame(): Unit =
    PlayerMenuController.exitGame()
