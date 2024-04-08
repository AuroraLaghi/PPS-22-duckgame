package it.unibo.pps.duckgame

import it.unibo.pps.duckgame.controller.{Game, GameController}
import it.unibo.pps.duckgame.model.Dice
import it.unibo.pps.duckgame.utils.FxmlUtils.createPrimaryStage
import it.unibo.pps.duckgame.view.CLI
import scalafx.application.JFXApp3

object Main extends JFXApp3:
  @main
  override def start(): Unit =
    //GameController.run()
    createPrimaryStage()
