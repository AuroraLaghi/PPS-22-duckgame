package it.unibo.pps.duckgame.model

object SpaceName:
  sealed trait SpaceName
  case class Cell(value: Int) extends SpaceName
  case class SpecialCell(value: String) extends SpaceName


