package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.model.Player
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class MovementsControllerTest extends AnyFlatSpec with should.Matchers with BeforeAndAfterEach:
  val p1: Player = Player("p1")
  val p2: Player = Player("p2")
  val p3: Player = Player("p3")
  val players: List[Player] = List(p1, p2, p3)

  override def beforeEach(): Unit =
    LogicController.newGame()
    players.foreach(p => GameReader addPlayer p)

  "If, on the first turn, I got from dices 6 and 3 or 4 and 5, then I" should "go respectively in cell 26 or 53, " +
    "instead of arriving to duck cell 9, which would let current player win the game" in {
    MovementsController.firstRoundMoves((6,3))
    GameReader.currentPlayer.actualPosition shouldBe 26
    LogicController.endTurn()
    MovementsController.firstRoundMoves((4,5))
    GameReader.currentPlayer.actualPosition shouldBe 53
  }

  "If I am on the starting cell and I end in bridge cell (number 6), then I " should "go directly to cell 12" in {
    MovementsController.moveWithSteps(6)
    GameReader.currentPlayer.actualPosition shouldBe 12
  }