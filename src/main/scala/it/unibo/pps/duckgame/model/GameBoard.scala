package it.unibo.pps.duckgame.model

/** Represents the game board
 * 
 * @param cells
 *  list of cells
 */
class GameBoard(cells: List[Cell]):
  
  private val _gameBoardMap: Map[Int, Cell] = {
    val numbers = (0 to 63).map(key => key -> CellImpl(key)).toMap
    numbers
  }

  /** Returns the game board map.
   *
   * @return
   *   The game board map as a list of cells.
   */
  def gameBoardMap: List[Cell] = cells

  /** Returns the size of the game board.
   *
   * @return
   *   The integer size of the game board.
   */
  def size: Int = cells.length

object GameBoard:
  def apply(): GameBoard = 
    val numbers = (0 to 63).map(n => CellImpl(n)).toList
    new GameBoard(numbers)
  
  def apply(cells: List[Cell]): GameBoard =
    new GameBoard(cells)
  