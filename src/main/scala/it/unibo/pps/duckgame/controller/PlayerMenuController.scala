package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.Player
import it.unibo.pps.duckgame.utils.FxmlUtils
import it.unibo.pps.duckgame.utils.resources.FxmlResources

object PlayerMenuController:

  def addPlayer(player: Player): Unit =
    GameController addPlayer player

  def removePlayer(player: Player): Unit =
    GameController removePlayer player

  def canAddPlayer: Boolean =
    GameStats.canAddPlayer

  def canStartGame: Boolean =
    GameStats.canStartGame

  def playGame(): Unit =
    GameController.startGame()
    FxmlUtils.changeScene(FxmlResources.GAME_VIEW.path)

  def exitGame(): Unit =
    GameController.exitGame()


