package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.GameController
import it.unibo.pps.duckgame.model.{Game, GameBoard, Player}

import scala.annotation.tailrec

class CLI:

  private var gameController: GameController = _

  def setController(controller: GameController): Unit =
    gameController = controller

  def getController: GameController = gameController

  def showGameBoard(game: Game): Unit =
    //print game board
    println("Tabellone duckgame:")
    printDuckGameBoard(game.gameBoard)
    println("\n")
    println("-------------------------------")


  private def printDuckGameBoard(board: GameBoard): Unit =
    val rows: List[List[Int]] = List(
      List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
      List(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13),
      List(38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, -1, 14),
      List(37, -1, -1, -1, -1, -1, -1, -1, -1, -1, 49, -1, 15),
      List(36, -1, 62, 63, -1, -1, -1, -1, -1, -1, 50, -1, 16),
      List(35, -1, 61, -1, -1, -1, -1, -1, -1, -1, 51, -1, 17),
      List(34, -1, 60, 59, 58, 57, 56, 55, 54, 53, 52, -1, 18),
      List(33, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 19),
      List(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20)
    )
    for(row <- rows)
      //stampa il contenuto delle celle
      for (cell <- row)
        drawCell(board, cell)
      println("")

  private def drawCell(gameBoard: GameBoard, cellId: Int): Unit =
    val box = cellId match
      case x if x > -1 => gameBoard.gameBoardMap.getOrElse(cellId, "").toString.padTo(10, ' ')
      case _ => " ".padTo(10, ' ')
    drawCellWithContainer(box)

  private def drawCellWithContainer(cellContent: String): Unit =
    if(cellContent == null || cellContent.isEmpty)
      print("")
    else
      print(f"| ${cellContent}")

  def showGameStart: Unit =
    println("Welcome to The Duck Game")
    getInput match
      case 'G' => gameController.startGame
      case 'E' => gameController.exitGame

  @tailrec
  private def getInput: Char =
    println("Press 'G' to play, or 'E' to exit game")
    scala.io.StdIn.readChar() match
      case 'g' | 'G' => 'G'
      case 'e' | 'E' => 'E'
      case _ => println("Insert a valid char")
        getInput


