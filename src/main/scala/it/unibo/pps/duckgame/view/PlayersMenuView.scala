package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.view.{GameBoardController, PlayerMenuController}
import it.unibo.pps.duckgame.model.{Player, Token}
import it.unibo.pps.duckgame.utils.resources.CssResources.GAME_STYLE
import it.unibo.pps.duckgame.utils.resources.{CssResources, FxmlResources}
import it.unibo.pps.duckgame.utils.{AlertUtils, FxmlUtils}
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.*
import javafx.scene.layout.{AnchorPane, BorderPane, VBox}
import javafx.scene.{Scene, control as jfxsc, layout as jfxsl}
import javafx.stage.Screen
import javafx.{event as jfxe, fxml as jfxf, scene as jfxs}
import scalafx.Includes.*
import scalafx.beans.property.{ObjectProperty, StringProperty}

import java.net.URL
import java.util.ResourceBundle

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
  private var playerTokenColumn: TableColumn[Player,Token] = _

  @FXML
  private var addPlayerNameTextField: TextField = _

  @FXML
  private var addPlayerButton: Button = _

  @FXML
  private var removePlayerButton: Button = _

  @FXML
  private var addTokenComboBox: ComboBox[Token] = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    FxmlUtils.initUIElements(pane, GAME_STYLE, WIDTH, HEIGHT)

    initializeTableView()
    updateTokenComboBox()
    removePlayerButton.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel.getSelectedItems))

  private def initializeTableView(): Unit =
    playerNameColumn.setCellValueFactory(p => StringProperty(p.getValue.name))
    playerTokenColumn.setCellValueFactory(p => ObjectProperty(p.getValue.token))
    tableView.setItems(FXCollections.observableArrayList[Player]())

  private def addPlayerToTableView(): Unit = addPlayerNameTextField.getText match
    case name if name.isEmpty => AlertUtils.emptyPlayerNameWarning()
    case _ =>
      val newPlayer = Player(addPlayerNameTextField.getText, addTokenComboBox.getValue)
      tableView.getItems.add(newPlayer)
      PlayerMenuController.addPlayer(newPlayer)
      updateTokenComboBox()
      updateAddAndRemoveButton()
      addPlayerNameTextField.clear()

  private def updateTokenComboBox(): Unit =
    addTokenComboBox.getItems.setAll(FXCollections.observableArrayList(PlayerMenuController.availableToken(): _*))
    addTokenComboBox.getSelectionModel.selectFirst()

  private def updateAddAndRemoveButton(): Unit =
    if PlayerMenuController.canAddPlayer then
      addPlayerButton.setDisable(false)
    else
      addPlayerButton.setDisable(true)

  def removePlayerFromTableView(): Unit =
    val selectedPlayer = tableView.getSelectionModel.getSelectedItem
    tableView.getItems.remove(selectedPlayer)
    PlayerMenuController.removePlayer(selectedPlayer)
    updateTokenComboBox()
    updateAddAndRemoveButton()

  def checkAndAddPlayerToTableView(): Unit =
    if PlayerMenuController.canAddPlayer then
      addPlayerToTableView()

  def playGame(): Unit =
    if PlayerMenuController.canStartGame then
      PlayerMenuController.playGame()
    else
      AlertUtils.notEnoughPlayersWarning()

  def exitGame(): Unit =
    PlayerMenuController.exitGame()
