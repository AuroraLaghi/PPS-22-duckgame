package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{Cell, CellStatus, Dice, GameBoard, Player}
import it.unibo.pps.duckgame.utils.GameUtils
import it.unibo.pps.duckgame.view.CLI

import scala.annotation.tailrec
import scala.util.Try

object GameController:
  private var _view: CLI = new CLI()

  def view: CLI = _view
  
  def view_=(value: CLI): Unit = _view = value
  
  def addPlayer(player: Player): Unit =
    Game addPlayer player
    
  def removePlayer(player: Player): Unit =
    Game removePlayer player
    
  def run(): Unit =
    _view.showGameBoard()
    _view.showGameStart()

  def startGame(): Unit =
    Game.players = GameUtils MixPlayers Game.players

  def exitGame(): Unit =
    sys.exit(0)

  def newGame(): Unit =
    Game.reset()

  def moveCurrentPlayer(steps: Int): Unit =
    PlayerController.updatePlayerWith(Game.currentPlayer, GameStats.currentPlayer.move(steps))
    if GameStats.checkVictory() then showVictory() 
    
  def currentPlayerQuit(): Unit =
    val playerToDelete = GameStats.currentPlayer
    endTurn()
    val nextPlayer = GameStats.currentPlayer
    Game removePlayer playerToDelete
    Game.currentPlayer = Game.players.indexOf(nextPlayer)
    if GameStats.checkVictoryForSurrender() then showVictory()
    
  def endTurn(): Unit =
    Game.currentPlayer = (Game.currentPlayer + 1) % Game.players.length
    
  def throwDice(): (Int, Int) =
    val dicePair = Dice().roll()
    println("Dices: " + dicePair._1.toString + " " + dicePair._2.toString)
    moveCurrentPlayer(dicePair._1 + dicePair._2)
    dicePair

  private def tryToInt(s: String) = Try(s.toInt).toOption
  
  private def showVictory(): Unit =
    GameStats.winner.foreach(w => 
      println(s"${w.name} IS THE WINNER!\nGAME OVER!")
      exitGame())



