package it.unibo.pps.duckgame.model

/**A trait that represents a cell in the board. A cell is a component of the board that can be
 * occupied by one or more players
 *
 */
trait Cell:
  /** The number assigned to the cell */
  def number: Int

  override def equals(obj: Any): Boolean = obj match
    case cell: Cell => cell.number == this.number
    case _ => false



