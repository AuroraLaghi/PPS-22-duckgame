package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.controller.GameStats
import it.unibo.pps.duckgame.model.{GameBoard, Player}

import scala.util.Random

object GameUtils:
  
  def addSumToPosition(sum: Int, position: Int): Int = sum + position match  
    case result if result >= GameStats.gameBoard.size => val updatedPosition = 63 - Math.abs(63 - result)
        updatedPosition
    case result                                       => result
      
  def MixPlayers(players: List[Player]): List[Player] =
    val mixedList = Random shuffle players
    mixedList
      
  