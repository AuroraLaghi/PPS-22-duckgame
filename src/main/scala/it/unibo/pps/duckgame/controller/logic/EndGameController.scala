package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.{Game, GameReader}

object EndGameController:

  private val MIN_PLAYERS = 2

  /** Determines if a winner has been declared in the current game state.
    *
    * @return
    *   `true` if a winner has been declared, `false` otherwise.
    */
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

  /** Sets the current player as the winner of the game. This method does not return a value (hence `Unit`), but it
    * modifies the internal game state.
    */
  def setWinner(): Unit =
    GameReader.setWinner(Option(GameReader.currentPlayer))

  /** Checks if the game is in a locked state, where only two players remain and both are either in jail or the well.
    *
    * @return
    *   `true` if the game is locked (two players, both in jail or well), `false` otherwise.
    */
  def isGameLocked: Boolean =
    GameReader.players.length == 2 && GameReader.playerInJail() != -1 && GameReader.playerInWell() != -1
