package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.utils.GameUtils

final case class Player(
      actualPosition: Int,
      name: String):
  
  def move(steps: Int): Player =
    Player(GameUtils.addSumToPosition(steps, actualPosition), name)
    
object Player:
  
  private val DEFAULT_STARTING_POSITION = 0
  
  def apply(name: String): Player =
    Player(DEFAULT_STARTING_POSITION, name)
  
  def apply(): Player =
    Player(DEFAULT_STARTING_POSITION, "")  

  
