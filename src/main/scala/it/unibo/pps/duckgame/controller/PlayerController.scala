package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.Player

protected object PlayerController:
  
  def updatePlayerWith(index: Int, playerUpdated: Player): Unit =
    Game.players = Game.players.updated(index, playerUpdated)