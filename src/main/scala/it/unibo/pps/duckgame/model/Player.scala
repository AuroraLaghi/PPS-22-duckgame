package it.unibo.pps.duckgame.model

final case class Player():
  
  private var _actualPosition: Int = 0
  
  def actualPosition: Int = _actualPosition
  def actualPosition_= (value: Int): Unit =
    _actualPosition = value
  
