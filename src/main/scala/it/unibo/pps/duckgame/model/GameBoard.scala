package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.model.specialCell.SpecialCellType.DUCK
import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellBuilder, SpecialCellType}
import it.unibo.pps.duckgame.utils.JsonUtils
import it.unibo.pps.duckgame.utils.deserialization.SpecialCellDeserializer
import it.unibo.pps.duckgame.utils.resources.JsonResources
import scalafx.scene.control

/** Represents the game board
  *
  * @param cells
  *   list of cells
  */
class GameBoard(
    cells: List[Cell],
    _specialCells: List[SpecialCell]
):

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
    new GameBoard((0 to 63).map(n => CellImpl(n)).toList, JsonUtils.readCellsType[SpecialCell](JsonResources.SPECIAL_CELLS, SpecialCellDeserializer ))

  def apply(cells: List[Cell], specialCell: List[SpecialCell]): GameBoard =
    new GameBoard(cells, specialCell)
