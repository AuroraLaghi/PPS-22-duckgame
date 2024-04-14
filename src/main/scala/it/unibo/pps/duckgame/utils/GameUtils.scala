package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.controller.GameStats
import it.unibo.pps.duckgame.model.{GameBoard, Player}

import scala.util.Random

object GameUtils:

  val CELLS_IN_SIDE = 7

  def addSumToPosition(sum: Int, position: Int): Int = sum + position match  
    case result if result >= GameStats.gameBoard.size => val updatedPosition = 63 - Math.abs(63 - result)
        updatedPosition
    case result                                       => result
      
  def MixPlayers(players: List[Player]): List[Player] =
    val mixedList = Random shuffle players
    mixedList

  /** Returns the cell's coordinates given player's position
   * First int is column index, the second is the row
   *
   * @param position
   *  player's position
   * @return
   */
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
        (CELLS_IN_SIDE * 8 + 3, 5)
      case _ if position < CELLS_IN_SIDE * 8 + 3 =>
        (2, (CELLS_IN_SIDE * 9 - 1) - position)
      case _ if position < CELLS_IN_SIDE * 9 - 2 =>
        (position - (CELLS_IN_SIDE * 8 + 1), 3)
      case _ if position < CELLS_IN_SIDE * 9 =>
        (4, position - (CELLS_IN_SIDE * 8 + 2))
      case _ => (3, 4)


  /** Return the coordinate of the nth cell in a grid of gridSize dimensions,
   * starting from startingCell.
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
    getNthCellInGrid(n + (startingCell._1 * gridSize._2 + startingCell._2 * gridSize._1),
      gridSize
    )

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
      case _                         => (gridSize._1 - 1, n / gridSize._1 - 1)