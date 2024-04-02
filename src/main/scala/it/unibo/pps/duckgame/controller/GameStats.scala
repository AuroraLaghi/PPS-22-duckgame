package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{GameBoard, Player}

object GameStats:
  private val MIN_PLAYERS = 1
  private val MAX_PLAYERS = 6
  
  def currentPlayer: Player = players(Game.currentPlayer)
  def players: List[Player]= Game.players
  def winner: Option[Player] = Game.winner
  
  def gameBoard: GameBoard = Game.gameBoard
  
  def canStartGame(): Boolean =
    Game.players.length match
      case n if n < MIN_PLAYERS => false
      case _                    => true
      
  def canAddPlayer(): Boolean =
    Game.players.length match
      case n if n < MAX_PLAYERS => true
      case _                    => false
      
  def checkVictory(): Boolean =
    currentPlayer.actualPosition match
      case 63 => Game.winner = Game.players.headOption
        true
      case _ => false  