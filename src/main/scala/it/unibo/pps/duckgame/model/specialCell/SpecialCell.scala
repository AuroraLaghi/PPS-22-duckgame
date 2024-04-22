package it.unibo.pps.duckgame.model.specialCell

import it.unibo.pps.duckgame.model.{Cell, CellStatus, Player}

class SpecialCell(
    override val number: Int,
    val specialCellType: SpecialCellType,
    val action: Int => Unit
) extends Cell:

  object SpecialCell:
    def apply(number: Int, specialCellType: SpecialCellType, action: Int => Unit): SpecialCell =
      new SpecialCell(number, specialCellType, action)
