package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.logic.LogicController
import it.unibo.pps.duckgame.controller.view.GameBoardController
import it.unibo.pps.duckgame.controller.{Game, GameReader}
import it.unibo.pps.duckgame.model.{Dice, Player}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestGameBoardController extends AnyFlatSpec with should.Matchers with BeforeAndAfterEach:
  val player1: Player = Player("luigi")
  val player2: Player = Player("marco")
  val player3: Player = Player("elena")
  val players: List[Player] = List(player1, player2, player3)
  val DEFAULT_STARTING_POSITION = 0
  override def beforeEach(): Unit =
    LogicController.newGame()
    players.foreach(p => Game.addPlayer(p))
    LogicController.initializeGame()

  "When game is started it" should "have already a non-empty list of players" in {
    GameReader.players.length shouldBe players.length
  }

  "When the turn of the current player is ended, current player's value" should "point at the index of the next player in the list" in {
    val shuffledPlayers = GameReader.players
    for i <- 1 until players.length
    do
      GameBoardController.endTurn()
      GameReader.currentPlayer shouldBe shuffledPlayers(i)
    GameBoardController.endTurn()
    GameReader.currentPlayer shouldBe shuffledPlayers.head
  }
  "Current player starting position" should "be 0" in {
    GameReader.currentPlayer.actualPosition shouldBe DEFAULT_STARTING_POSITION
  }
  "When current player moves it" should "have a new position equal to the starting one plus the number got with dices" in {
    val dicePair = Dice().roll()
    val sum = dicePair._1 + dicePair._2
    LogicController.moveCurrentPlayer(sum)
    dicePair._1.isValidInt shouldBe true
    dicePair._2.isValidInt shouldBe true
    GameReader.currentPlayer.actualPosition shouldBe DEFAULT_STARTING_POSITION + sum
  }

  "When a player quits, the list of players" should "remove it" in {
    for i <- players.length - 1 to 2
    do
      val playerToDelete = GameReader.currentPlayer
      GameBoardController.currentPlayerQuit()
      GameReader.players.length shouldBe i
      GameReader.players.contains(playerToDelete) shouldBe false
  }

  "When the game starts it" should "have at least two players" in {
    LogicController.newGame()
    GameReader.canStartGame shouldBe false
    Game.addPlayer(player1)
    GameReader.canStartGame shouldBe false
    Game.addPlayer(player2)
    GameReader.canStartGame shouldBe true
  }

  "Each game" should "have at maximum 6 players" in {
    GameReader.canAddPlayer shouldBe true
    Game.addPlayer(player1)
    GameReader.canAddPlayer shouldBe true
    Game.addPlayer(player2)
    GameReader.canAddPlayer shouldBe true
    Game.addPlayer(player3)
    GameReader.canAddPlayer shouldBe false
  }

  "When there's a game winner it" should "be set somewhere" in {
    GameReader.winner shouldBe None
    Game.winner = Some(player1)
    GameReader.winner shouldBe Some(player1)
  }
