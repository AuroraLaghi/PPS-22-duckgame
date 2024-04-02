package it.unibo.pps.duckgame.view

import it.unibo.pps.duckgame.controller.{Game, GameController, GameStats}
import it.unibo.pps.duckgame.model.{GameBoard, Player}

import scala.annotation.tailrec
import scala.util.Try

class CLI:
  
  def showGameBoard(): Unit =
    //print game board
    println("Tabellone duckgame:")
    printDuckGameBoard()
    println("\n")
    println("-------------------------------")


  private def printDuckGameBoard(): Unit =
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
        drawCell(cell)
      println("")

  private def drawCell(cellId: Int): Unit =
    val box = cellId match
      case x if x.equals(63) => "END".padTo(10, ' ')
      case x if x > -1 => x.toString.padTo(10, ' ')
      case _ => " ".padTo(10, ' ')
    drawCellWithContainer(box)

  private def drawCellWithContainer(cellContent: String): Unit =
    if(cellContent == null || cellContent.isEmpty)
      print("")
    else
      print(f"| $cellContent")

  def showGameStart(): Unit =
    println("Welcome to The Duck Game")
    getInput match
      case 'G' => showGameStartPlay()
      case 'E' => GameController.exitGame()

  def showGameStartPlay(): Unit =
    println("Start Game")
    GameController.startGame()
    showRollDiceOrQuit()

  private def tryToInt(s: String) = Try(s.toInt).toOption

  @tailrec
  private def showRollDiceOrQuit(): Unit =
    println("press 1 to move into the board, or 2 to quit the game")
    tryToInt(scala.io.StdIn.readLine()) match
      case Some(position: Int) =>
        position match
          case 1 => throwDice()
          case 2 => println("Exiting from game...")
            exitGame()
          case _ => showInvalidInput()
      case _ => showInvalidInput()
      showRollDiceOrQuit()
      
  @tailrec
  private def throwDice(): Unit =
    val pairDice = GameController.ThrowDice()
    showPosition(pairDice)
    if (pairDice._1 == pairDice._2)
      throwDice()

  def exitGame(): Unit =
    println("Exiting form game")
    GameController.exitGame()

  private def showInvalidInput(): Unit =
    println("Invalid input")

  @tailrec
  private def getInput: Char =
    println("Press 'G' to play, or 'E' to exit game")
    scala.io.StdIn.readChar() match
      case 'g' | 'G' => 'G'
      case 'e' | 'E' => 'E'
      case _ => println("Insert a valid char")
        getInput
        
  def showVictory(winner: Player): Unit =
    println("WE HAVE A WINNER!\nGAME OVER!")

  def showPosition(dices: (Int, Int)): Unit = 
    println(s"From dices rolling you obtained ${dices._1 + dices._2} and now you're in position " +
        s"${GameStats.currentPlayer.actualPosition}")  


