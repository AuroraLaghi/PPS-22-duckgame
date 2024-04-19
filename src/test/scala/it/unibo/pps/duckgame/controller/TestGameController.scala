package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{Dice, Player}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TestGameController extends AnyFlatSpec with should.Matchers:
  val player1: Player = Player("luigi")
  val player2: Player = Player("marco")
  val player3: Player = Player("elena")
  val players: List[Player] = List(player1, player2, player3)

  players.foreach(p => Game.addPlayer(p))

  GameController.startGame()

  "When game is started it" should "have already a non-empty list of players" in {
    GameStats.players.length shouldBe players.length
  }

  "When the turn of the current player is ended, current player's value" should "point at the index of the next player in the list" in {
    val shuffledPlayers = GameStats.players
    for i <- 1 until players.length
    do
      GameController.endTurn()
      GameStats.currentPlayer shouldBe shuffledPlayers(i)
    GameController.endTurn()
    GameStats.currentPlayer shouldBe shuffledPlayers.head
  }

  val DEFAULT_STARTING_POSITION = 0
  val startPosition: Int = GameStats.currentPlayer.actualPosition

  "Current player starting position" should "be 0" in {
    startPosition shouldBe DEFAULT_STARTING_POSITION
  }

  "When current player moves it" should "have a new position equal to the starting one plus the number got with dices" in {
    val dicePair = Dice().roll()
    val sum = dicePair._1 + dicePair._2
    GameController.moveCurrentPlayer(sum)
    dicePair._1.isValidInt shouldBe true
    dicePair._2.isValidInt shouldBe true
    GameStats.currentPlayer.actualPosition shouldBe startPosition + sum
  }

  "When a player quits, the list of players" should "remove it" in {
    for i <- players.length - 1 to 2
    do
      val playerToDelete = GameStats.currentPlayer
      GameController.currentPlayerQuit()
      GameStats.players.length shouldBe i
      GameStats.players.contains(playerToDelete) shouldBe false
  }

  "When the game starts it" should "have at least two players" in {
    Game.reset()
    GameStats.canStartGame shouldBe false
    Game.addPlayer(player1)
    GameStats.canStartGame shouldBe false
    Game.addPlayer(player2)
    GameStats.canStartGame shouldBe true
  }

  "Each game" should "have at maximum 6 players" in {
    Game.reset()
    GameStats.canAddPlayer shouldBe true
    players.foreach(p => Game.addPlayer(p))
    GameStats.canAddPlayer shouldBe true
    Game.addPlayer(player1)
    GameStats.canAddPlayer shouldBe true
    Game.addPlayer(player2)
    GameStats.canAddPlayer shouldBe true
    Game.addPlayer(player3)
    GameStats.canAddPlayer shouldBe false
  }

  "When there's a game winner it" should "be set somewhere" in {
    GameStats.winner shouldBe None
    Game.winner = Some(player1)
    GameStats.winner shouldBe Some(player1)
  }
