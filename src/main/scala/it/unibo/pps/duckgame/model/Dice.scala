package it.unibo.pps.duckgame.model

import scala.util.Random

class Dice:
  private var _dice1: Int = 1
  private var _dice2: Int = 2
  
  rollDices
  
  def rollDices: Unit =
    _dice1 = Random.between(1, 7)
    _dice2 = Random.between(1, 7)
    
  def sum: Int =
    _dice1 + _dice2
    
  def controlSame: Boolean =
    _dice1 == _dice2  
    
  def dice1: Int = _dice1
  
  def dice2: Int = _dice2