package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.model.specialCell.SpecialCellType.DUCK
import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellBuilder, SpecialCellType}
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
    var duckNumbers = List(5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59)
    val normalCells = List(
      0 to 4,
      7 to 8,
      10 to 13,
      15 to 17,
      20 to 22,
      24 to 26,
      28 to 30,
      33 to 35,
      37 to 40,
      43 to 44,
      46 to 49,
      51,
      53,
      55 to 57,
      60 to 62
    )
    var listNormal = normalCells.flatMap {
      case range: Range => range.toList
      case number: Int => List(number)
    }
    var specialCells = List(
      SpecialCellBuilder(6, SpecialCellType.BRIDGE, "").build(),
      SpecialCellBuilder(31, SpecialCellType.WELL, "").build(),
      SpecialCellBuilder(42, SpecialCellType.LABYRINTH, "").build(),
      SpecialCellBuilder(58, SpecialCellType.SKELETON, "").build(),
      SpecialCellBuilder(52, SpecialCellType.JAIL, "").build(),
      SpecialCellBuilder(19, SpecialCellType.HOUSE, "").build(),
      SpecialCellBuilder(63, SpecialCellType.FINAL, "").build()
    )
    val duckList = duckNumbers.map(n => SpecialCellBuilder(n, SpecialCellType.DUCK, "").build())
    specialCells = specialCells++duckList
    val numbers: List[Cell] = listNormal.map(n => CellImpl(n))++specialCells
    new GameBoard(numbers, specialCells)

  def apply(cells: List[Cell], specialCell: List[SpecialCell]): GameBoard =
    new GameBoard(cells, specialCell)
