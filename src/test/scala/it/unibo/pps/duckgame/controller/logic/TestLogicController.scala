package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.controller.logic.LogicController
import it.unibo.pps.duckgame.controller.view.GameBoardController
import it.unibo.pps.duckgame.model.{CellStatus, Player}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestLogicController extends AnyFlatSpec with should.Matchers with BeforeAndAfterEach:
  val p1: Player = Player("p1")
  val p2: Player = Player("p2")
  val p3: Player = Player("p3")
  val players: List[Player] = List(p1, p2, p3)

  override def beforeEach(): Unit =
    LogicController.newGame()
    players.foreach(p => GameReader addPlayer p)

  "When startGame is called, firstRound variable" should "be true" in {
    LogicController.initializeGame()
    GameReader.isFirstRound shouldBe true
  }

  "If two players are locked in jail and well cells it" should "only play one player" in {
    LogicController.endTurn()
    GameBoardController.movePlayer((15, 16))
    LogicController.endTurn()
    GameBoardController.movePlayer((30, 22))
    LogicController.endTurn()
    GameReader.currentPlayer shouldBe p3
    LogicController.endTurn()
    GameReader.currentPlayer shouldBe p3
    LogicController.endTurn()
    GameReader.currentPlayer shouldBe p3
  }

  "When a player end in the house cell it" should "appear as locked for one turn" in {
    LogicController.lockUnlockTurnPlayer(true)
    val currentPlayer = GameReader.currentPlayer
    currentPlayer.isLocked shouldBe true
  }

  "If a player get 9 by dices at first turn and ends in duck cell it" should "not win but go in cell 26 or 53, depending the result of dices throw" in {
    GameBoardController.movePlayer((3, 6))
    GameReader.currentPlayer.actualPosition shouldBe 26
    LogicController.endTurn()
    GameBoardController.movePlayer((4, 5))
    GameReader.currentPlayer.actualPosition shouldBe 53
  }

  "When all players have played the first turn, the related variable" should "become false" in {
    GameReader.isFirstRound shouldBe true
    LogicController.endTurn()
    GameReader.isFirstRound shouldBe true
    LogicController.endTurn()
    GameReader.isFirstRound shouldBe true
    LogicController.endTurn()
    GameReader.isFirstRound shouldBe false
  }

  "When a player go into a special cell it" should "be noted" in {
    LogicController.moveCurrentPlayer(6)
    LogicController.checkCellType shouldBe CellStatus.SPECIAL_CELL
  }

  "If one player is blocked in well, another one has to stop for one turn and the third one leaves game, " +
    "then the new current player" should "be the one stopped for one turn" in {
    LogicController.movePlayer((10, 9))
    LogicController.endTurn()
    LogicController.movePlayer((20, 11))
    LogicController.endTurn()
    LogicController.currentPlayerQuit()
    GameReader.currentPlayer.name shouldBe p3.name
  }
