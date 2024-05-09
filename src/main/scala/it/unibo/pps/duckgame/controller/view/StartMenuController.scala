package it.unibo.pps.duckgame.controller.view

import it.unibo.pps.duckgame.controller.logic.LogicController
import it.unibo.pps.duckgame.utils.FxmlUtils
import it.unibo.pps.duckgame.utils.resources.FxmlResources

/**
 * Controller for the [[it.unibo.pps.duckgame.view.StartMenuView]]
 */
object StartMenuController:

  /**
   * Called when PLAY button is pressed
   */
  def playGame(): Unit =
    LogicController.startGame()
    FxmlUtils.changeScene(FxmlResources.PLAYER_MENU.path)

  /**
   * Called when EXIT button is pressed
   */
  def closeGame(): Unit =
    LogicController.exitGame()  