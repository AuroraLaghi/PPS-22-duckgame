package it.unibo.pps.duckgame.utils.config

import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellBuilder, SpecialCellType}

object Parser:
  trait Parser[T]:
    def parse(line: String): Option[T]

  object SpecialCellsParser extends Parser[SpecialCell]:
    
    override def parse(line: String): Option[SpecialCell] =
      line.split(";").toList match
        case number :: cellType :: message :: Nil =>
          cellType match
            case "duck" => Some(SpecialCellBuilder(number.toInt, SpecialCellType.DUCK, message).build())
            case "jail" => Some(SpecialCellBuilder(number.toInt, SpecialCellType.JAIL, message).build())
            case "bridge" => Some(SpecialCellBuilder(number.toInt, SpecialCellType.BRIDGE, message).build())
            case "house" => Some(SpecialCellBuilder(number.toInt, SpecialCellType.HOUSE, message).build())
            case "well" => Some(SpecialCellBuilder(number.toInt, SpecialCellType.WELL, message).build())
            case "labyrinth" => Some(SpecialCellBuilder(number.toInt, SpecialCellType.LABYRINTH, message).build())
            case "skeleton" => Some(SpecialCellBuilder(number.toInt, SpecialCellType.SKELETON, message).build())
            case "final" => Some(SpecialCellBuilder(number.toInt, SpecialCellType.FINAL, message).build())
            case _ => Some(SpecialCellBuilder(number.toInt, SpecialCellType.BLANK, message).build())
        case _ => None
