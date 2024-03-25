package it.unibo.pps.duckgame

import it.unibo.pps.duckgame.controller.GameController
import it.unibo.pps.duckgame.model.{Dice, Game}
import it.unibo.pps.duckgame.view.CLI

object Main:
  @main
  def startGame(): Unit =
    val model = new Game
    val view = new CLI
    val controller = new GameController(model, view)

    controller.initialize
    controller.run
