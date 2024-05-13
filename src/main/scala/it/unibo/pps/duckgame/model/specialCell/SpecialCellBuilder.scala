package it.unibo.pps.duckgame.model.specialCell

import it.unibo.pps.duckgame.controller.logic.{EndGameController, LogicController, MovementsController}
import it.unibo.pps.duckgame.controller.view.PlayerMenuController
import it.unibo.pps.duckgame.controller.{Game, GameReader}
import it.unibo.pps.duckgame.model.CellStatus
import it.unibo.pps.duckgame.model.specialCell.SpecialCellType.BLANK

/** Builder class for creating `SpecialCell` objects.
  *
  * The builder pattern allows for optional chaining of methods to set the `action` property of the `SpecialCell`
  * object.
  *
  * @param number
  *   The unique identifier (integer) assigned to the special cell.
  * @param specialCellType
  *   The type of special effect associated with the cell (`SpecialCellType` enum value).
  * @param message
  *   The message displayed to the player when landing on the cell (string).
  */
final case class SpecialCellBuilder(number: Int, specialCellType: SpecialCellType, message: String):

  /** Potentially moves the player towards the starting position on the game board, ignoring the `steps` parameter
    *
    * @param steps
    *   Number of step
    */
  private def jumpToStart(steps: Int): Unit =
    MovementsController.fixedPositionMove(0)

  /** Moves the player directly to position 39 on the game board, ignoring the `steps` parameter.
    *
    * @param steps
    *   Number of steps
    */
  private def jumpToThirtyNine(steps: Int): Unit =
    MovementsController.fixedPositionMove(39)

  /** Moves the player directly to position 12 on the game board, ignoring the `steps` parameter.
    *
    * @param steps
    *   Number of steps
    */
  private def jumpToTwelve(steps: Int): Unit =
    MovementsController.fixedPositionMove(12)

  /** Moves the player forward on the game board by a specified number of steps.
    *
    * @param steps
    *   The number of positions the player should move forward.
    */
  private def moveForward(steps: Int): Unit =
    MovementsController.moveWithSteps(steps)

  /** Declares a winner in the game, likely triggering end-game procedures (steps parameter is ignored)
    *
    * @param steps
    *   Number of steps
    */
  private def setWinner(steps: Int): Unit =
    EndGameController.setWinner()

  /** The current player goes onto jail, so it can't play
    *
    * @param steps
    *   Number steps (not utilize)
    */
  private def goInJail(steps: Int): Unit =
    LogicController.playerCantPlay(GameReader.currentPlayerIndex)
    
    MovementsController.playerCantPlay(GameReader.currentPlayerIndex)

  /** The current player goes onto well, so it can't play
    *
    * @param steps
    *   Number steps (not utilize)
    */
  private def goInsideWell(steps: Int): Unit =
    LogicController.playerCantPlay(GameReader.currentPlayerIndex)

  /** The current player goes onto house, so it must stop for one turn
    * @param steps
    *   Number steps (not utilize)
    */
  private def lockOneTurn(steps: Int): Unit =
    LogicController.lockUnlockTurnPlayer(true)

  /** Does not perform any action for the current player */
  private def noAction(steps: Int): Unit = {}

  /** Creates a new `SpecialCell` object based on the provided `specialCellType`.
    *
    * Finally, the method creates a new `SpecialCell` object using the provided `number`, `specialCellType`, `message`
    * (likely a descriptive message associated with the cell), and the assigned `action`.
    *
    * @return
    *   A new `SpecialCell` object with the specified properties.
    */
  def build(): SpecialCell =
    val action = specialCellType match
      case SpecialCellType.BRIDGE => jumpToTwelve
      case SpecialCellType.DUCK => moveForward
      case SpecialCellType.FINAL => setWinner
      case SpecialCellType.HOUSE => lockOneTurn
      case SpecialCellType.JAIL => goInJail
      case SpecialCellType.LABYRINTH => jumpToThirtyNine
      case SpecialCellType.SKELETON => jumpToStart
      case SpecialCellType.WELL => goInsideWell
      case SpecialCellType.BLANK => noAction

    new SpecialCell(number, specialCellType, message, action)
