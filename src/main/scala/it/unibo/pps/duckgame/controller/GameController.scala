package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.SpaceName.{SpaceName, SpecialCell}
import it.unibo.pps.duckgame.model.{Dice, Game, GameBoard, Player}
import it.unibo.pps.duckgame.utils.GameUtils
import it.unibo.pps.duckgame.view.CLI

import scala.annotation.tailrec
import scala.util.Try

class GameController(
    game: Game,
    view: CLI,
    _gameBoard: GameBoard = new GameBoard,
    _dice: Dice = new Dice):


  def gameBoard: GameBoard = _gameBoard

  def dice: Dice = _dice

  def initialize: Unit =
    view.setController(this)

  def run: Unit =
    view.showGameBoard(game)
    view.showGameStart

  def startGame: Unit =
    game.currentPlayer_(new Player)
    moveUser(game)

  def exitGame: Unit =
    sys.exit(0)

  def moveCurrentPlayer: Unit =
    dice.rollDices
    dice.sum
    game.currentPlayer.actualPosition =
      GameUtils.addSumToPosition(dice.sum, game.currentPlayer.actualPosition, gameBoard)
    checkPosition(game.currentPlayer)
    println(f"${game.currentPlayer.actualPosition}")

  def getBoxFromPlayerPosition(player: Player): SpaceName =
    gameBoard.gameBoardMap(player.actualPosition)

  @tailrec
  private def moveUser (game: Game): Unit =
    println("press 1 to move into the board, or 2 to quit the game")
    tryToInt(scala.io.StdIn.readLine()) match
      case Some(position: Int) =>
        position match
          case 1 => moveCurrentPlayer
            if(dice.controlSame)
              moveCurrentPlayer
          case 2 => println("Exiting from game...")
            exitGame
          case _ => showInvalidInput
      case _ => showInvalidInput
    moveUser(game)


  private def showInvalidInput: Unit =
    println("Invalid input")


  private def tryToInt(s: String) = Try(s.toInt).toOption

  private def checkPosition(player:Player): Unit = player.actualPosition match
    case 63 =>
      println(s"From dices rolling you obtained ${dice.sum} and now you're in position " +
        s"${getBoxFromPlayerPosition(player)}")
      println("player wins!")
      exitGame
    case _ =>
      println(s"From dices rolling you obtained ${dice.sum} and now you're in position " +
        s"${getBoxFromPlayerPosition(player)}")


