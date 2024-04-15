package it.unibo.pps.duckgame

import it.unibo.pps.duckgame.controller.{Game, GameController}
import it.unibo.pps.duckgame.model.{Dice, Player}
import it.unibo.pps.duckgame.utils.FxmlUtils.createPrimaryStage
import scalafx.application.JFXApp3

object Main extends JFXApp3:
  @main
  override def start(): Unit =
    val p1 = Player("p1")
    val p2 = Player("p2")

    GameController.addPlayer(p1)
    GameController.addPlayer(p2)
    createPrimaryStage()
