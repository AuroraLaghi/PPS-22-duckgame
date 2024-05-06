package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.Player
import it.unibo.pps.duckgame.model.specialCell.SpecialCell
import it.unibo.pps.duckgame.utils.{AlertUtils, FxmlUtils}
import it.unibo.pps.duckgame.utils.resources.FxmlResources

/** Object class that controls player's movements */
protected object PlayerController:

  /** Update player at given index with the new data
    *
    * @param index
    *   index of player to update inside playerslist
    * @param playerUpdated
    *   updated player to replace in the list
    */
  def updatePlayerWith(index: Int, playerUpdated: Player): Unit =
    Game.players = Game.players.updated(index, playerUpdated)

  def playerOnSpecialCell(specialCell: SpecialCell, steps: Int): Unit =
    specialCell.action(steps)
