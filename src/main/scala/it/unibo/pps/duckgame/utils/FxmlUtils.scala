package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.utils.resources.{CssResources, FxmlResources, ImgResources}
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.layout.{AnchorPane, Pane}
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.BorderPane
import scalafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import scalafx.stage.{Screen, Stage}

import java.io.IOException
import java.util.Optional

/** Utility object that provides methods to load FXML resources and change the current scene. */
object FxmlUtils:
  private var _stage: Stage = _
  private var width: Double = _
  private var height: Double = _

  val DEFAULT_WIDTH_PERC: Double = 0.8
  val DEFAULT_HEIGHT_PERC: Double = 0.8

  /** Returns the current stage.
    *
    * @return
    *   the current stage.
    */
  def stage: Stage = _stage

  /** Sets the current stage.
    *
    * @param value
    *   the stage to set.
    */
  def stage_=(value: Stage): Unit =
    _stage = value

  /** Loads an FXML resource file and creates a Scene from it.
    *
    * @param fxmlPath
    *   The path to the FXML resource file.
    * @return
    *   A Scene object created from the loaded FXML file.
    * @throws IOException
    *   If the FXML resource file cannot be loaded.
    */
  private def loadFXMLResource(fxmlPath: String): Scene =
    val fxmlFile = getClass.getResource(fxmlPath)
    if (fxmlFile === null)
      throw new IOException("Cannot load resource: " + fxmlPath)
    val root: Parent = FXMLLoader.load(fxmlFile)
    new Scene(root)

  /** Changes the current scene.
    *
    * @param fxmlPath
    *   the path of the FXML resource to load.
    */
  def changeScene(fxmlPath: String): Unit =
    val scene = loadFXMLResource(fxmlPath)
    stage.setScene(scene)

  /** Creates and configures the primary stage of the application.
    *
    * This method sets the title, scene, icon, and resizable property of the primary stage.
    */
  def createPrimaryStage(): Unit =
    stage = new PrimaryStage:
      title.value = "THE DUCK GAME"
      scene = FxmlUtils.loadFXMLResource(FxmlResources.START_MENU.path)
      icons += Image(this.getClass.getResourceAsStream("/img/duck.png"))
      resizable = false

  /** Gets the resolution of the screen.
    *
    * @return
    *   the resolution of the screen.
    */
  def getResolution: (Double, Double) = (width, height)

  /** Initialize the UI elements of the game.
    *
    * @param pane
    *   the pane to initialize
    * @param gameBoard
    *   an Option[ImageView] representing the game board image (can be None if no game board image is used).
    * @param cssResources
    *   the css style to apply
    * @param width_perc
    *   the width percentage
    * @param height_perc
    *   the height percentage
    */
  def initUIElements(
      pane: BorderPane,
      gameBoard: Option[ImageView],
      cssResources: CssResources,
      width_perc: Double,
      height_perc: Double
  ): Unit =
    setPaneResolution(pane, width_perc, height_perc)
    setPaneStyle(pane, cssResources)
    if gameBoard.isDefined then
      setGameBoardImage(gameBoard.get)
      setGameBoardSize(pane, gameBoard.get)

  /** Sets pane resolution
    *
    * @param pane
    *   element to be set
    * @param widthPerc
    *   new width
    * @param heightPerc
    *   new height
    */
  private def setPaneResolution(
      pane: Pane,
      widthPerc: Double,
      heightPerc: Double
  ): Unit =
    val screenResolution = Screen.primary.bounds
    width = screenResolution.getWidth * widthPerc
    height = screenResolution.getHeight * heightPerc
    pane.setPrefWidth(width)
    pane.setPrefHeight(height)

  /** Sets the CSS style for the BorderPane.
    *
    * @param pane
    *   The BorderPane to style.
    * @param cssResources
    *   The CSS resources object containing the path to the stylesheet.
    */
  private def setPaneStyle(pane: BorderPane, cssResources: CssResources): Unit =
    pane.getStylesheets.add(
      getClass.getResource(cssResources.path).toExternalForm
    )

  /** Sets gameboard size
    *
    * @param pane
    *   graphic element
    * @param gameBoard
    *   gameboard image
    */
  private def setGameBoardSize(pane: BorderPane, gameBoard: ImageView): Unit =
    val gameBoardSize = pane.getPrefHeight
    gameBoard.setFitWidth(gameBoardSize)
    gameBoard.setFitHeight(gameBoardSize)

  /** Sets the gameboard image
    *
    * @param gameBoard
    *   imageView of the gameboard
    */
  private def setGameBoardImage(gameBoard: ImageView): Unit =
    gameBoard.setImage(
      new Image(
        getClass.getResource(ImgResources.GAMEBOARD.path).toString
      )
    )
    gameBoard.setPreserveRatio(false)

  /** Displays a modal alert dialog with a specified type, title, header text, and content text.
    *
    * @param alertType
    *   The type of alert dialog (e.g., AlertType.Warning, AlertType.Information).
    * @param title
    *   The title of the dialog.
    * @param headerText
    *   The optional header text displayed below the title.
    * @param contentText
    *   The main content text displayed in the body of the dialog.
    * @return
    *   An Optional[ButtonType] representing the button clicked by the user (or None if the dialog was closed without
    *   clicking a button).
    */
  def showAlert(alertType: AlertType, title: String, headerText: String, contentText: String): Optional[ButtonType] =

    val alert = new Alert(alertType)
    alert setTitle title
    alert setHeaderText headerText
    alert setContentText contentText
    alert.showAndWait()
