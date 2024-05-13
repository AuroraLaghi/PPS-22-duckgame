package it.unibo.pps.duckgame.model

/** Represents the cell class. This class extends the `Cell` class and likely provides concrete functionality for a
  * standard cell on the game board.
  *
  * @param _number
  *   The unique identifier (integer) assigned to this cell.
  */
class CellImpl(_number: Int) extends Cell:

  /** Gets the cell's unique identifier.
    *
    * @return
    *   The integer representing the cell's unique identifier.
    */
  def number: Int = _number
object CellImpl:
  def apply(number: Int): CellImpl = new CellImpl(number)
