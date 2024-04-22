package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.controller.{GameReader, LogicController}
import it.unibo.pps.duckgame.model.specialCell.SpecialCell
import it.unibo.pps.duckgame.model.{Cell, GameBoard, Player}

import scala.util.Random

object GameUtils:

  val CELLS_IN_SIDE = 7

  /** Method that returns the new position of current player If the position is bigger than the gameboard's size, it
    * will return back from the last cell
    *
    * @param sum
    *   result of the dices
    * @param position
    *   player's position to be updated
    * @return
    *   new player's position
    */
  def addSumToPosition(sum: Int, position: Int): Int = sum + position match
    case result if result >= GameReader.gameBoard.size =>
      val updatedPosition = 63 - Math.abs(63 - result)
      updatedPosition
    case result => result

  def MixPlayers(players: List[Player]): List[Player] =
    val mixedList = Random shuffle players
    mixedList

  /** Returns the cell's coordinates given player's position First int is column index, the second is the row
    *
    * @param position
    *   player's position
    * @return
    *   coordinates in (col, row) of the cell representing player's position
    */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def getCoordinateFromPosition(position: Int): (Int, Int) =
    position match
      case _ if position < 0 =>
        throw new IllegalArgumentException("Position cannot be negative")
      case _ if position >= GameBoard().size =>
        throw new IllegalArgumentException(
          "Position cannot be greater than board size"
        )
      case _ if position < CELLS_IN_SIDE =>
        (position, 0)
      case _ if position < CELLS_IN_SIDE * 2 =>
        (CELLS_IN_SIDE, position - CELLS_IN_SIDE)
      case _ if position < CELLS_IN_SIDE * 3 =>
        (CELLS_IN_SIDE * 3 - position, CELLS_IN_SIDE)
      case _ if position < CELLS_IN_SIDE * 4 - 1 =>
        (0, CELLS_IN_SIDE * 4 - position)
      case _ if position < CELLS_IN_SIDE * 5 - 2 =>
        (position - (CELLS_IN_SIDE * 4 - 1), 1)
      case _ if position < CELLS_IN_SIDE * 5 + 3 =>
        (CELLS_IN_SIDE - 1, position - ((CELLS_IN_SIDE + 1) * 4))
      case _ if position <= CELLS_IN_SIDE * 6 =>
        ((CELLS_IN_SIDE * 6 + 2) - position, 6)
      case _ if position < CELLS_IN_SIDE * CELLS_IN_SIDE - 2 =>
        (1, CELLS_IN_SIDE * CELLS_IN_SIDE - position)
      case _ if position < CELLS_IN_SIDE * CELLS_IN_SIDE + 2 =>
        (position - (CELLS_IN_SIDE * CELLS_IN_SIDE - 3), 2)
      case _ if position < CELLS_IN_SIDE * 8 - 2 =>
        (5, position - (CELLS_IN_SIDE * CELLS_IN_SIDE))
      case _ if position <= CELLS_IN_SIDE * 8 =>
        ((CELLS_IN_SIDE * 8 + 3) - position, 5)
      case _ if position < CELLS_IN_SIDE * 8 + 3 =>
        (2, (CELLS_IN_SIDE * 9 - 1) - position)
      case _ if position < CELLS_IN_SIDE * 9 - 2 =>
        (position - (CELLS_IN_SIDE * 8 + 1), 3)
      case _ if position < CELLS_IN_SIDE * 9 =>
        (4, position - (CELLS_IN_SIDE * 8 + 2))
      case _ => (3, 4)

  /** Return the coordinate of the nth cell in a grid of gridSize dimensions, starting from startingCell.
    * @param n
    *   The number of the cell which coordinates are to be returned.
    * @param gridSize
    *   The dimensions of the grid.
    * @param startingCell
    *   The starting cell of the grid.
    * @return
    *   The coordinates of the nth cell.
    */
  def getNthCellInGridWithStartingPos(
      n: Int,
      gridSize: (Int, Int),
      startingCell: (Int, Int)
  ): (Int, Int) =
    getNthCellInGrid(n, gridSize)

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  private def getNthCellInGrid(n: Int, gridSize: (Int, Int)): (Int, Int) =
    n match
      case _ if gridSize._1 <= 0 =>
        throw new IllegalArgumentException("Grid columns must be positive")
      case _ if gridSize._2 <= 0 =>
        throw new IllegalArgumentException("Grid rows must be positive")
      case _ if n <= 0 =>
        throw new IllegalArgumentException("N must be positive")
      case _ if n > gridSize._1 * gridSize._2 =>
        throw new IllegalArgumentException("N cannot be greater than grid size")
      case _ if n % gridSize._1 != 0 => (n % gridSize._1 - 1, n / gridSize._1)
      case _ => (gridSize._1 - 1, n / gridSize._1 - 1)

  def getSpecialCellFromPlayerPosition(): Option[SpecialCell] =
    GameReader.gameBoard.specialCells.find(
      _.number == GameReader.currentPlayer.actualPosition
    )
