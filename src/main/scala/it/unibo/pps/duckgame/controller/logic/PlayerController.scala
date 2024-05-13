package it.unibo.pps.duckgame.controller.logic

import it.unibo.pps.duckgame.controller.{Game, GameReader}
import it.unibo.pps.duckgame.model.Player
import it.unibo.pps.duckgame.model.specialCell.SpecialCell
import it.unibo.pps.duckgame.utils.resources.FxmlResources
import it.unibo.pps.duckgame.utils.{AlertUtils, FxmlUtils}

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
    GameReader.updatePlayers(index, playerUpdated)

  /** Executes the action associated with a specific special cell for the current player.
    *
    * @param specialCell
    *   The `SpecialCell` object representing the special cell the player is on.
    * @param steps
    *   The number of steps the player rolled (potentially relevant for the special cell's action).
    */
  def playerOnSpecialCell(specialCell: SpecialCell, steps: Int): Unit =
    specialCell.action(steps)
