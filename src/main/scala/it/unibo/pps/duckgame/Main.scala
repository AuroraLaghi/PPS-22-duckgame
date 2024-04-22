package it.unibo.pps.duckgame

import it.unibo.pps.duckgame.controller.Game
import it.unibo.pps.duckgame.model.{Dice, Player}
import it.unibo.pps.duckgame.utils.FxmlUtils.createPrimaryStage
import scalafx.application.JFXApp3

object Main extends JFXApp3:
  override def start(): Unit =
    createPrimaryStage()
