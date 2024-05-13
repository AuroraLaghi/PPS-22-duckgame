package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.model.Player
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class EndGameControllerTest extends AnyFlatSpec with should.Matchers with BeforeAndAfterEach:
  val p1: Player = Player("p1")
  val p2: Player = Player("p2")
  val players: List[Player] = List(p1, p2)

  override def beforeEach(): Unit =
    LogicController.newGame()
    players.foreach(p => GameReader addPlayer p)

  "When a player wins the game, method checkWinner" should "return true" in {
    EndGameController.setWinner()
    EndGameController.checkWinner() shouldBe true
  }

  "If there are only two players and they both end in well and jail cells, the game" should "stuck" in {
    MovementsController.moveWithSteps(31)
    LogicController.endTurn()
    MovementsController.moveWithSteps(52)
    EndGameController.isGameLocked shouldBe true
  }

  "When one of the two players quit, then the other player" should "become the winner" in {
    LogicController.currentPlayerQuit()
    EndGameController.checkVictoryForSurrender() shouldBe true
    GameReader.winner shouldBe Option(p1)
  }
