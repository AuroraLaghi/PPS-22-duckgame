package it.unibo.pps.duckgame.model.cell

/** Represents a cell on the Duck Game board.
  *
  * This trait defines the basic properties and functionality of a cell within the game board. A cell can be occupied by
  * one or more players.
  */
trait Cell:
  /** Gets the unique identifier assigned to this cell.
    *
    * @return
    *   The integer representing the cell's unique identifier.
    */
  def number: Int

  /** Defines custom logic for object equality comparison.
    *
    * @param obj
    *   The object to compare with this cell.
    * @return
    *   `true` if the object is a `Cell` with the same `number`, `false` otherwise.
    */
  override def equals(obj: Any): Boolean = obj match
    case cell: Cell => cell.number == this.number
    case _ => false
