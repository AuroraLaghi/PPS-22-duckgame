package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.utils.resources.{CssResources, FxmlResources, ImgResources}
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.{AnchorPane, Pane}
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.BorderPane
import scalafx.stage.{Screen, Stage}

import java.io.IOException

object FxmlUtils:
  private var _stage: Stage = _
  private var width: Double = _
  private var height: Double = _

  val DEFAULT_WIDTH_PERC: Double = 0.8
  val DEFAULT_HEIGHT_PERC: Double = 0.8

  def stage: Stage = _stage

  def stage_=(value: Stage): Unit =
    _stage = value

  private def loadFXMLResource(fxmlPath: String): Scene =
    val fxmlFile = getClass.getResource(fxmlPath)
    if (fxmlFile == null)
      throw new IOException("Cannot load resource: " + fxmlPath)
    val root: Parent = FXMLLoader.load(fxmlFile)
    new Scene(root)

  def changeScene(fxmlPath: String): Unit =
    val scene = loadFXMLResource(fxmlPath)
    stage.setScene(scene)

  def createPrimaryStage(): Unit =
    stage = new PrimaryStage:
      title.value = "THE DUCK GAME"
      scene = FxmlUtils.loadFXMLResource(FxmlResources.START_MENU.path)
      resizable = false

  def getResolution: (Double, Double) = (width, height)

  /** Initialize the UI elements of the game.
    *
    * @param pane
    *   the pane to initialize
    * @param gameBoard
    *   the game board imageView
    * @param cssResources
    *   the css style to apply
    * @param width_perc
    *   the width percentage
    * @param height_perc
    *   the height percentage
    */
  def initUIElements(
      pane: BorderPane,
      gameBoard: ImageView,
      cssResources: CssResources,
      width_perc: Double,
      height_perc: Double
  ): Unit =
    setPaneResolution(pane, width_perc, height_perc)
    setGameBoardImage(gameBoard)
    setGameBoardSize(pane, gameBoard)
    setPaneStyle(pane, cssResources)

  def setPaneResolution(
      pane: Pane,
      widthPerc: Double,
      heightPerc: Double
  ): Unit =
    val screenResolution = Screen.primary.bounds
    width = screenResolution.getWidth * widthPerc
    height = screenResolution.getHeight * heightPerc
    pane.setPrefWidth(width)
    pane.setPrefHeight(height)

  private def setPaneStyle(pane: BorderPane, cssResources: CssResources): Unit =
    pane.getStylesheets.add(
      getClass.getResource(cssResources.path).toExternalForm
    )

  private def setGameBoardSize(pane: BorderPane, gameBoard: ImageView): Unit =
    val gameBoardSize = pane.getPrefHeight
    gameBoard.setFitWidth(gameBoardSize)
    gameBoard.setFitHeight(gameBoardSize)

  private def setGameBoardImage(gameBoard: ImageView): Unit =
    gameBoard.setImage(
      new Image(
        getClass.getResource(ImgResources.GAMEBOARD.path).toString
      )
    )
    gameBoard.setPreserveRatio(false)
