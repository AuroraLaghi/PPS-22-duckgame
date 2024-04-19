package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{Cell, CellStatus, Dice, GameBoard, Player}
import it.unibo.pps.duckgame.utils.resources.FxmlResources
import it.unibo.pps.duckgame.utils.{AlertUtils, FxmlUtils, GameUtils}

import scala.annotation.tailrec
import scala.util.Try

/**
 * Controller for the [[it.unibo.pps.duckgame.view.GameBoardView]]
 */
object GameController:

  /** Add a player to the players' list
   *
   * @param player
   *  the player to be added
   */
  def addPlayer(player: Player): Unit =
    Game addPlayer player

  /** Removes a player from the game
   *
   * @param player
   *  player entity to be removed
   */
  def removePlayer(player: Player): Unit =
    Game removePlayer player

  /**
   * Initialize players' list mixing its values
   */
  def startGame(): Unit =
    Game.players = GameUtils MixPlayers Game.players

  /**
   * Close the game
   */
  def exitGame(): Unit =
    sys.exit(0)

  /**
   * Reset the game to the initial stats
   */
  def newGame(): Unit =
    Game.reset()

  /**
   * Sets current player's new position
   * @param steps
   *  Number of cells to sum to old position
   */
  def moveCurrentPlayer(steps: Int): Unit =
    PlayerController.updatePlayerWith(Game.currentPlayer, GameStats.currentPlayer.move(steps))
    if GameStats.checkVictory() then showVictory()

  /**
   * Called when a player quits the game
   */
  def currentPlayerQuit(): Unit =
    val playerToDelete = GameStats.currentPlayer
    endTurn()
    val nextPlayer = GameStats.currentPlayer
    Game removePlayer playerToDelete
    Game.currentPlayer = Game.players.indexOf(nextPlayer)
    if GameStats.checkVictoryForSurrender() then showVictory()

  /**
   * Called when a player ends its turn
   */
  def endTurn(): Unit =
    Game.currentPlayer = (Game.currentPlayer + 1) % Game.players.length

  /** Method that throws two dices and return their result
   *
   * @return
   *  dices' values
   */
  def throwDice(): (Int, Int) =
    val dicePair = Dice().roll()
    moveCurrentPlayer(dicePair._1 + dicePair._2)
    dicePair

  /**
   * Prints victory message with the name of the winner, then closes the application
   */
  private def showVictory(): Unit =
    GameStats.winner.foreach(w =>
      AlertUtils.showVictory(w)
      newGame()
      FxmlUtils.changeScene(FxmlResources.START_MENU.path)
    )
