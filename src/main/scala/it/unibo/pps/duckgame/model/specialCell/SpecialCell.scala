package it.unibo.pps.duckgame.model.specialCell

import it.unibo.pps.duckgame.model.{Cell, CellStatus, Player}

class SpecialCell(
    override val number: Int,
    val specialCellType: SpecialCellType,
    val message: String,
    val action: Int => Unit
) extends Cell:

  object SpecialCell:
    def apply(number: Int, specialCellType: SpecialCellType, message: String, action: Int => Unit): SpecialCell =
      new SpecialCell(number, specialCellType, message, action)

