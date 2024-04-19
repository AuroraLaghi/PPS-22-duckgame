package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.Player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class PlayerControllerTest extends AnyFlatSpec with should.Matchers:
  val player1: Player = Player("pippo")
  val player2: Player = Player("paperino")
  val players: List[Player] = List(player1, player2)
  players.foreach(p => Game.addPlayer(p))

  "Method update player" should "update current player with new information" in {
    GameStats.currentPlayer.actualPosition shouldBe 0
    PlayerController.updatePlayerWith(Game.currentPlayer, GameStats.currentPlayer.move(10))
    GameStats.currentPlayer.actualPosition shouldBe 10
  }

  "Method update player" should "return back when the number of steps is bigger than the gameboard" in {
    GameController.endTurn()
    GameStats.currentPlayer.actualPosition shouldBe 0
    PlayerController.updatePlayerWith(Game.currentPlayer, GameStats.currentPlayer.move(67))
    GameStats.currentPlayer.actualPosition shouldBe 59
  }