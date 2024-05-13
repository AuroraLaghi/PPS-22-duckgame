package it.unibo.pps.duckgame.utils.config

import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellBuilder, SpecialCellType}

/** The `Parser` object likely provides functionalities related to parsing textual information in the Duck Game model */
object Parser:
  /** Trait representing a parser for a generic type `T`.
    *
    * This trait defines the `parse` method that takes a string (`line`) as input and attempts to parse it into an
    * object of type `T`. It returns an `Option[T]`, indicating a successful parse result with `Some(T)` or a failure
    * with `None`.
    */
  trait Parser[T]:
    /** Attempts to parse a string into an object of type `T`. This method is a generic parser, meaning it can be used
      * with different data types depending on the implementation. It's recommended to document the specific parsing
      * logic and the expected format of the `line` for a particular usage scenario.
      *
      * @param line
      *   The string to be parsed. The format of the `line` depends on the specific implementation and context.
      * @return
      *   An `Option[T]`. If parsing is successful, it returns `Some(T)` containing the parsed object. If parsing fails,
      *   it returns `None`.
      */
    def parse(line: String): Option[T]

  /** Parser specifically designed to handle lines describing special cells in the Duck Game model.
    *
    * This object, `SpecialCellsParser`, extends the `Parser[SpecialCell]` trait. It provides a concrete implementation
    * of the `parse` method to parse lines from a configuration file or other source that define special cell
    * information.
    *
    * This `SpecialCellsParser` is likely used during the game initialization or level loading to parse information
    * about special cells defined in a configuration file or other data source.
    */
  object SpecialCellsParser extends Parser[SpecialCell]:

    /** @param line
      *   The string to be parsed. The format of the `line` depends on the specific implementation and context.
      * @return
      *   An `Option[T]`. If parsing is successful, it returns `Some(T)` containing the parsed object. If parsing fails,
      *   it returns `None`.
      */
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
