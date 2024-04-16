package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.Player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestGame extends AnyFlatSpec with should.Matchers:
  val player1: Player = Player("luigi")
  val player2: Player = Player("marco")
  val player3: Player = Player("elena")
  val players: List[Player] = List(player1, player2, player3)

  players.foreach(p => Game.addPlayer(p))

  "Method addPlayer" should "add a player to the list of existent ones" in {
    val player4: Player = Player("gianluca")
    Game.addPlayer(player4)
    Game.players shouldBe List(player4, player3, player2, player1)
    Game.removePlayer(player4)
  }

  "Method removePlayer" should "remove the player from the list of existent ones" in {
    Game.removePlayer(player1)
    Game.players shouldBe List(player3, player2)
  }

  "Game winner" should "be empty at creation" in {
    Game.winner shouldBe None
  }

  "Winner setter" should "set the game winner" in {
    Game.winner = Some(player1)
    Game.winner shouldBe Some(player1)
  }

  "When game resets it" should "have an empty list of players" in {
    Game.reset()
    Game.players shouldBe List.empty
  }