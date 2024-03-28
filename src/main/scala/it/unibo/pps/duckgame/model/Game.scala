package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.utils.GameUtils

class Game:

  private[this] var _currentPlayer: Player = null
  private[this] val _gameBoard: GameBoard = new GameBoard

  def currentPlayer: Player = _currentPlayer

  def currentPlayer_(value: Player): Unit =
    _currentPlayer = value

  def gameBoard: GameBoard = _gameBoard

  def reset(): Unit =
    _currentPlayer = null






