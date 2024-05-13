package it.unibo.pps.duckgame.controller.view

import it.unibo.pps.duckgame.controller
import it.unibo.pps.duckgame.controller.logic.{EndGameController, LogicController, MovementsController}
import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.model.CellStatus.SPECIAL_CELL
import it.unibo.pps.duckgame.model.specialCell.SpecialCellType.JAIL
import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellType}
import it.unibo.pps.duckgame.model.*
import it.unibo.pps.duckgame.utils.resources.FxmlResources
import it.unibo.pps.duckgame.utils.{AlertUtils, AnyOps, FxmlUtils, GameUtils}
import it.unibo.pps.duckgame.view.GameBoardView

import scala.annotation.tailrec
import scala.util.Try

/** Controller for the [[it.unibo.pps.duckgame.view.GameBoardView]] */
object GameBoardController:
  private var _view: GameBoardView = _

  /** Gets a reference to the currently associated game board view.
    *
    * @return
    *   The currently associated `GameBoardView` object, or `null` if no view is set.
    */
  def view: GameBoardView = _view

  /** Sets the associated game board view for this object.
    *
    * @param value
    *   The `GameBoardView` object to be associated with this object.
    */
  def view_=(value: GameBoardView): Unit =
    _view = value

  /** Called when a player quits the game */
  def currentPlayerQuit(): Unit =
    LogicController.currentPlayerQuit()
    if EndGameController.checkVictoryForSurrender() then showVictory()

  /** Called when a player ends its turn */
  def endTurn(): Unit =
    LogicController.endTurn()

  /** Method that throws two dices and return their result
    *
    * @return
    *   dices' values
    */
  def throwDice(): (Int, Int) =
    val dicePair = Dice().roll()
    movePlayer(dicePair)
    dicePair

  /** Prints victory message with the name of the winner, then closes the application */
  def showVictory(): Unit =
    GameReader.winner.foreach(w =>
      AlertUtils.showVictory(w)
      LogicController.newGame()
      FxmlUtils.changeScene(FxmlResources.START_MENU.path)
    )

  /** Determines if a winner has been declared in the current game state.
    *
    * @return
    *   `true` if a winner has been declared, `false` otherwise.
    */
  def checkVictory(): Boolean =
    EndGameController.checkWinner()

  /** Moves the current player on the game board based on the rolled dice.
    *
    * @param dicePair
    *   A tuple containing the values of the two rolled dice (first element, second element).
    */
  def movePlayer(dicePair: (Int, Int)): Unit =
    LogicController.movePlayer(dicePair)

  /** Handles the scenario where the game is locked (unable to proceed).
    *
    * This method performs the following actions when the game is locked:
    *   1. Displays a warning message 2. Resets the game state 3. Transitions the user interface to the start menu
    *      screen
    */
  def showGameLocked(): Unit =
    AlertUtils.gameLockedWarning()
    LogicController.newGame()
    FxmlUtils.changeScene(FxmlResources.START_MENU.path)

  /** Updates the UI to display a message about player movement, if a view object is available.
    *
    * @param message
    *   The message string to be displayed related to player movement.
    */
  def viewPlayerMovement(message: String): Unit =
    if _view =/= null then _view.playerMovement(message)
