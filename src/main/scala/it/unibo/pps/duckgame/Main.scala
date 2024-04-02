package it.unibo.pps.duckgame

import it.unibo.pps.duckgame.controller.{Game, GameController}
import it.unibo.pps.duckgame.model.Dice
import it.unibo.pps.duckgame.view.CLI

object Main:
  @main
  def startGame(): Unit =
    GameController.run()
