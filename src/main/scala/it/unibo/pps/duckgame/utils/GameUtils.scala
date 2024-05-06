package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.controller.{GameReader, LogicController}
import it.unibo.pps.duckgame.model.specialCell.SpecialCell
import it.unibo.pps.duckgame.model.{Cell, GameBoard, Player}
import it.unibo.pps.duckgame.utils.PrologGameUtils.PrologEngine.PrologEngine
import it.unibo.pps.duckgame.utils.resources.PrologResources
import alice.tuprolog.SolveInfo
import PrologGameUtils.PrologTheory.given
import PrologGameUtils.{PrologSolution, PrologTheory, ConversionTerm}


import scala.util.Random

object GameUtils:

  val CELLS_IN_SIDE = 7
  private val pathTheory = PrologResources.GAMEUTILS_PROLOG.path
  private val prolog: PrologEngine = PrologEngine(pathTheory)

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
  def getCoordinateFromPosition(position: Int): (Int, Int) =
    prolog.getFreeSlotInCell(position, CELLS_IN_SIDE)

  /** Return the coordinate of the free slot inside a grid of gridSize dimensions
    * @param n
    *   The number of the slot in grid which coordinates are to be returned.
    * @param gridSize
    *   The dimensions of the grid (default 3 x 2)
    * @return
    *   The coordinates of the nth slot.
    */
  def getNthSlotFromCell(n: Int, gridSize: (Int, Int)): (Int, Int) =
    prolog.getCellInGrid(n, gridSize._1, gridSize._2)

  def getSpecialCellFromPlayerPosition: Option[SpecialCell] =
    GameReader.gameBoard.specialCells.find(
      _.number == GameReader.currentPlayer.actualPosition
    )
