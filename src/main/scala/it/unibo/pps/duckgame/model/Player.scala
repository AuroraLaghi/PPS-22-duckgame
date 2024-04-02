package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.utils.GameUtils

case class Player(actualPosition: Int):
  
  def move(steps: Int): Player =
    Player(GameUtils.addSumToPosition(steps, actualPosition))
    
object Player:
  
  private val DEFAULT_STARTING_POSITION = 0
  
  def apply(): Player =
    Player(DEFAULT_STARTING_POSITION)  

  
