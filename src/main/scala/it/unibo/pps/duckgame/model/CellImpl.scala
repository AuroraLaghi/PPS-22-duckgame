package it.unibo.pps.duckgame.model

class CellImpl(_number: Int) extends Cell:
  def number: Int = _number
/** Implementation class of Cell
 *
 */
object CellImpl:
  /** Creates a cell with the given name
   *
   * @param number
   *  number of the cell
   * @return
   *  the created cell
   */

  def apply(number: Int): CellImpl = new CellImpl(number)