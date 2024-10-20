package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.controller.GameReader
import it.unibo.pps.duckgame.model.Player
import it.unibo.pps.duckgame.model.cell.specialCell.SpecialCell
import it.unibo.pps.duckgame.utils.PrologGameUtils.PrologEngine.PrologEngine
import it.unibo.pps.duckgame.utils.PrologGameUtils.PrologTheory
import it.unibo.pps.duckgame.utils.PrologGameUtils.PrologTheory.given
import it.unibo.pps.duckgame.utils.resources.PrologResources

import scala.language.implicitConversions
import scala.util.Random

object GameUtils:

  val CELLS_IN_SIDE = 7
  private val pathTheory = PrologResources.GAMEUTILS_PROLOG.path
  private val prolog: PrologEngine = PrologEngine(pathTheory)

  /** Method that returns the new position of current player If the position is
    * bigger than the gameboard's size, it will return back from the last cell
    *
    * @param sum
    *   result of the dices
    * @param position
    *   player's position to be updated
    * @return
    *   new player's position
    */
  def addSumToPosition(sum: Int)(position: Int): Int = sum + position match
    case result if result >= GameReader.gameBoard.size =>
      val updatedPosition = 63 - Math.abs(63 - result)
      updatedPosition
    case result => result

  /** Shuffles the order of the players in the list
    *
    * @param players
    *   A list og Players objects
    * @return
    *   A new list of Players objects with order randomized
    */
  def mixPlayers(players: List[Player]): List[Player] =
    Random shuffle players

  /** Returns the cell's coordinates given player's position First int is column
    * index, the second is the row
    *
    * @param position
    *   player's position
    * @return
    *   coordinates in (col, row) of the cell representing player's position
    */
  def getCoordinateFromPosition(position: Int): (Int, Int) =
    prolog.getFreeSlotInCell(position)(CELLS_IN_SIDE)

  /** Returns the coordinate of the free slot inside a grid of gridSize
    * dimensions
    * @param n
    *   The number of the slot in grid which coordinates are to be returned.
    * @param gridSize
    *   The dimensions of the grid (default 3 x 2)
    * @return
    *   The coordinates of the nth slot.
    */
  def getNthSlotFromCell(n: Int)(gridSize: (Int, Int)): (Int, Int) =
    prolog.getCellInGrid(n)(gridSize._1)(gridSize._2)

  /** Finds a SpecialCell object from the game board that corresponds to the
    * current player's position.
    *
    * @return
    *   An Option[SpecialCell] containing the SpecialCell if found, or None if
    *   not found.
    */
  def getSpecialCellFromPlayerPosition: Option[SpecialCell] =
    GameReader.gameBoard.specialCells.find(
      _.number == GameReader.currentPlayer.actualPosition
    )
