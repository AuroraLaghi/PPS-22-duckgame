package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.model.SpaceName.{Cell, SpaceName, SpecialCell}

class GameBoard:
  private val _gameBoardMap: Map[Int, SpaceName] = {
    val numbers = (0 to 62).map(key => key -> Cell(key)).toMap
    val finalSpace = 63 -> SpecialCell("FINAL_SPACE")
    numbers + finalSpace
  }
  
  def gameBoardMap: Map[Int, SpaceName] = _gameBoardMap
  
  def size: Int = _gameBoardMap.size