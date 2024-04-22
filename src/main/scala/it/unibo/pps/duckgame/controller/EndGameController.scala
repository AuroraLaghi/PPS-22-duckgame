package it.unibo.pps.duckgame.controller

object EndGameController:

  private val MIN_PLAYERS = 2
  
  def checkVictory(): Boolean =
    GameReader.currentPlayer.actualPosition match
      case 63 => Game.winner = Game.players.headOption
        true
      case _ => false

  /** Checks if there's only one player actually playing the game
   *
   * @return
   * true if only one player is left playing, false otherwise
   */
  def checkVictoryForSurrender(): Boolean = GameReader.players.size match
    case n if n == MIN_PLAYERS - 1 =>
      Game.winner = Game.players.headOption
      true
    case _ => false
