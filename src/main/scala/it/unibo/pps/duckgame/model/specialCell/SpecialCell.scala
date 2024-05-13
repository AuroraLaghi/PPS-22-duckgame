package it.unibo.pps.duckgame.model.specialCell

import it.unibo.pps.duckgame.model.{Cell, CellStatus, Player}

/** Represents a special cell with unique gameplay effects on the Duck Game board.
  *
  * This class extends the `Cell` trait and defines the properties and behavior of a special cell. Special cells trigger
  * events or effects when a player lands on them.
  */
class SpecialCell(
    override val number: Int,
    val specialCellType: SpecialCellType,
    val message: String,
    val action: Int => Unit
) extends Cell:

  object SpecialCell:
    def apply(number: Int, specialCellType: SpecialCellType, message: String, action: Int => Unit): SpecialCell =
      new SpecialCell(number, specialCellType, message, action)
