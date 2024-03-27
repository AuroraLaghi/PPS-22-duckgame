package it.unibo.pps.duckgame

import it.unibo.pps.duckgame.model.{Game, Player}
import org.junit.jupiter.api.Test

@Test
abstract class BaseTest:
  var game: Game = new Game
  var player: Player = new Player

  def setUp(): Unit =
    game = new Game
    player = new Player
