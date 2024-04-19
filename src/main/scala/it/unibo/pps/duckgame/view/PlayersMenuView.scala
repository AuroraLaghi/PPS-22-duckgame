package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.{GameController, GameStats, PlayerMenuController}
import it.unibo.pps.duckgame.model.Player
import it.unibo.pps.duckgame.utils.{AlertUtils, FxmlUtils}
import it.unibo.pps.duckgame.utils.resources.{CssResources, FxmlResources}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.layout.{AnchorPane, BorderPane}
import javafx.{event as jfxe, fxml as jfxf, scene as jfxs}
import javafx.scene.{control as jfxsc, layout as jfxsl}
import javafx.scene.Scene

import java.net.URL
import java.util.ResourceBundle
import scalafx.Includes.*
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import scalafx.beans.property.StringProperty
import javafx.scene.control.{Button, TableColumn, TableView, TextField}
import javafx.scene.layout.VBox
import javafx.stage.Screen

class PlayersMenuView extends Initializable:

  private def NMENU = 2

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var startButton: Button = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var exitButton: Button = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var pane: BorderPane = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var leftBorderPaneVBox: VBox = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var rightBorderPaneVBox: VBox = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var tableView: TableView[Player] = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var playerNameColumn: TableColumn[Player, String] = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var playerTokenColumn: TableColumn[Player,String] = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var addPlayerNameTextField: TextField = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var addPlayerButton: Button = _

  @FXML
  @SuppressWarnings(Array("org.wartremover.warts.Null", "org.wartremover.warts.Var"))
  private var removePlayerButton: Button = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    pane.getStylesheets.add(getClass.getResource(CssResources.GAME_STYLE.path).toExternalForm)
    FxmlUtils.setPaneResolution(pane, 0.6, 0.6)

    initializeTableView()
    removePlayerButton.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel.getSelectedItems))

  private def initializeTableView(): Unit =
    playerNameColumn.setCellValueFactory(p => StringProperty(p.getValue.name))
    playerTokenColumn.setCellValueFactory(p => StringProperty(p.getValue.name))
    tableView.setItems(FXCollections.observableArrayList[Player]())

  private def addPlayerToTableView(): Unit = addPlayerNameTextField.getText match
    case name if name.isEmpty => AlertUtils.emptyPlayerNameWarning()
    case _ =>
      val newPlayer = Player(addPlayerNameTextField.getText)
      tableView.getItems.add(newPlayer)
      PlayerMenuController.addPlayer(newPlayer)
      updateAddAndRemoveButton()
      addPlayerNameTextField.clear()

  private def updateAddAndRemoveButton(): Unit =
    if PlayerMenuController.canAddPlayer then
      addPlayerButton.setDisable(false)
    else
      addPlayerButton.setDisable(true)

  def removePlayerFromTableView(): Unit =
    val selectedPlayer = tableView.getSelectionModel.getSelectedItem
    tableView.getItems.remove(selectedPlayer)
    PlayerMenuController.removePlayer(selectedPlayer)
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
