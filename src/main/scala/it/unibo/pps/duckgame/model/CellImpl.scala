package it.unibo.pps.duckgame.model

/** Represents the cell class
 *
 * @param _number
 * number
 */
class CellImpl(_number: Int) extends Cell:
  def number: Int = _number
object CellImpl:

  def apply(number: Int): CellImpl = new CellImpl(number)