package it.unibo.pps.duckgame

import it.unibo.pps.duckgame.controller.Game
import it.unibo.pps.duckgame.model.{Dice, Player}
import it.unibo.pps.duckgame.utils.FxmlUtils.createPrimaryStage
import scalafx.application.JFXApp3

/** The main class of the application extending JFXApp3.
  *
  * This class serves as the entry point for the JavaFX application.
  */
object Main extends JFXApp3:
  /** This method is called when the application starts.
    *
    * It likely initializes the primary stage for the application.
    */
  override def start(): Unit =
    createPrimaryStage()
