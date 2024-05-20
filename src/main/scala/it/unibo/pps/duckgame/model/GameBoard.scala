package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.model.cell.specialCell.{SpecialCell, SpecialCellType}
import it.unibo.pps.duckgame.model.cell.{Cell, CellImpl}
import it.unibo.pps.duckgame.utils.config.Parser.SpecialCellsParser
import it.unibo.pps.duckgame.utils.config.SetupFromFile
import it.unibo.pps.duckgame.utils.resources.TxtResources

/** Represents the game board
  *
  * @param cells
  *   A list of cells
  * @param _specialCells
  *   A list of SpecialCell objects representing special cells with unique effects on gameplay. These cells might
  *   provide bonuses, penalties, or trigger events when a player lands on them.
  */
class GameBoard(
    cells: List[Cell],
    _specialCells: List[SpecialCell]
):

  /** Gets a reference to the list of special cells on the game board.
    *
    * @return
    *   An immutable list of `SpecialCell` objects on the game board. Modifying this list will not affect the internal
    *   game state.
    */
  def specialCells: List[SpecialCell] = _specialCells

  /** Returns the game board map.
    *
    * @return
    *   The game board map as a list of cells.
    */
  def gameBoardList: List[Cell] = cells

  /** Returns the size of the game board.
    *
    * @return
    *   The integer size of the game board.
    */
  def size: Int = cells.length

object GameBoard:
  def apply(): GameBoard =
    new GameBoard(
      (0 to 63).map(n => CellImpl(n)).toList,
      SetupFromFile.setup[SpecialCell](TxtResources.SPECIAL_CELL_TXT.path, SpecialCellsParser)
    )

  def apply(cells: List[Cell], specialCell: List[SpecialCell]): GameBoard =
    new GameBoard(cells, specialCell)
