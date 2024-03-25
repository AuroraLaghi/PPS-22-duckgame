package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.model.GameBoard

object GameUtils:
  
  def addSumToPosition(sum: Int, position: Int, gameBoard: GameBoard): Int =
    sum + position match
      case result if result >= gameBoard.size => 
        val updatedPosition = 63 - Math.abs(63 - result)
        updatedPosition
      case result => result
      
  