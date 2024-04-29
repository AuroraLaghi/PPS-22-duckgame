package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.Player
import it.unibo.pps.duckgame.model.specialCell.{SpecialCellBuilder, SpecialCellType}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class PlayerControllerTest extends AnyFlatSpec with should.Matchers with BeforeAndAfterEach:
  val player1: Player = Player("pippo")
  val player2: Player = Player("paperino")
  val players: List[Player] = List(player1, player2)

  override def beforeEach(): Unit =
    LogicController.newGame()
    players.foreach(p => LogicController.addPlayer(p))

  "Method update player" should "update current player with new information" in {
    GameReader.currentPlayer.actualPosition shouldBe 0
    PlayerController.updatePlayerWith(GameReader.currentPlayerIndex, GameReader.currentPlayer.move(10))
    GameReader.currentPlayer.actualPosition shouldBe 10
  }

  "Method update player" should "return back when the number of steps is bigger than the gameboard" in {
    GameBoardController.endTurn()
    GameReader.currentPlayer.actualPosition shouldBe 0
    PlayerController.updatePlayerWith(Game.currentPlayer, GameReader.currentPlayer.move(67))
    GameReader.currentPlayer.actualPosition shouldBe 59
  }

  "When a player goes into a special cell, it" should "be applied special cell's action" in {
    PlayerController.updatePlayerWith(GameReader.currentPlayerIndex, GameReader.currentPlayer.move(6))
    PlayerController.playerOnSpecialCell(SpecialCellBuilder(6, SpecialCellType.BRIDGE, "").build(), 6)
    GameReader.currentPlayer.actualPosition shouldBe 12
  }