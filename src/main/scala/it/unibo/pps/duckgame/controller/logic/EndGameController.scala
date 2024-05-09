package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.{Game, GameReader}

object EndGameController:

  private val MIN_PLAYERS = 2

  def checkWinner(): Boolean =
    GameReader.winner.isDefined

  /** Checks if there's only one player actually playing the game
    *
    * @return
    *   true if only one player is left playing, false otherwise
    */
  def checkVictoryForSurrender(): Boolean = GameReader.players.size match
    case n if n == MIN_PLAYERS - 1 =>
      setWinner()
      true
    case _ => false

  def setWinner(): Unit =
    GameReader.setWinner(Option(GameReader.currentPlayer))
    
  def isGameLocked: Boolean =
    GameReader.players.length == 2 && GameReader.playerInJail() != -1 && GameReader.playerInWell() != -1
