package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.SpaceName.{SpaceName, SpecialCell}
import it.unibo.pps.duckgame.model.{Dice, Game, GameBoard, Player}
import it.unibo.pps.duckgame.utils.GameUtils
import it.unibo.pps.duckgame.view.CLI

import scala.annotation.tailrec
import scala.util.Try

class GameController:
  private val _game: Game = new Game
  private val _gameBoard: GameBoard = new GameBoard
  private val _dice: Dice = new Dice
  private val _view: CLI = new CLI
  
  def gameBoard: GameBoard = _gameBoard

  private def dice: Dice = _dice
  
  def game: Game = _game
  
  def currentPlayer: Player = game.currentPlayer

  def initialize(): Unit =
    _view.setController(this)

  def run(): Unit =
    _view.showGameBoard(game)
    _view.showGameStart(game)

  def startGame(): Unit =
    game.currentPlayer_(new Player)

  def exitGame(): Unit =
    sys.exit(0)

  def newGame(): Unit =
    game.reset()

  def moveCurrentPlayer(): Unit =
    dice.rollDices()
    dice.sum
    game.currentPlayer.actualPosition =
      GameUtils.addSumToPosition(dice.sum, game.currentPlayer.actualPosition, gameBoard)
    checkPosition(game.currentPlayer)
    if (dice.controlSame)
      moveCurrentPlayer()

  private def getBoxFromPlayerPosition(player: Player): SpaceName =
    gameBoard.gameBoardMap(player.actualPosition)

  private def tryToInt(s: String) = Try(s.toInt).toOption

  private def checkPosition(player:Player): Unit = player.actualPosition match
    case 63 =>
      println(s"From dices rolling you obtained ${dice.sum} and now you're in position " +
        s"${getBoxFromPlayerPosition(player)}")
      println("player wins!")
      exitGame()
    case _ =>
      println(s"From dices rolling you obtained ${dice.sum} and now you're in position " +
        s"${getBoxFromPlayerPosition(player)}")


