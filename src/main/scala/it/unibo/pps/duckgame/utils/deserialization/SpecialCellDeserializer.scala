package it.unibo.pps.duckgame.utils.deserialization

import com.google.gson.stream.JsonReader
import it.unibo.pps.duckgame.model.specialCell.{SpecialCell, SpecialCellBuilder, SpecialCellType}

import scala.annotation.tailrec

object SpecialCellDeserializer extends Deserializer[SpecialCell]:
  override def read(reader: JsonReader): SpecialCell =
    var number = -1
    var cellType = ""
    var message = ""

    @tailrec
    def readJson(reader: JsonReader): Unit =
      if (reader.hasNext)
        val currentName = reader.nextName()
        currentName match
          case "number" => number = reader.nextInt()
          case "cellType" => cellType = reader.nextString()
          case "message" => message = reader.nextString()
          case _ => reader.skipValue()
        readJson(reader)

    reader.beginObject()
    readJson(reader)
    reader.endObject()

    cellType match
      case "duck" => SpecialCellBuilder(number, SpecialCellType.DUCK, message).build()
      case "jail" => SpecialCellBuilder(number, SpecialCellType.JAIL, message).build()
      case "bridge" => SpecialCellBuilder(number, SpecialCellType.BRIDGE, message).build()
      case "house" => SpecialCellBuilder(number, SpecialCellType.HOUSE, message).build()
      case "well" => SpecialCellBuilder(number, SpecialCellType.WELL, message).build()
      case "labyrinth" => SpecialCellBuilder(number, SpecialCellType.LABYRINTH, message).build()
      case "skeleton" => SpecialCellBuilder(number, SpecialCellType.SKELETON, message).build()
      case "final" => SpecialCellBuilder(number, SpecialCellType.FINAL, message).build()
      case _ => SpecialCellBuilder(number, SpecialCellType.BLANK, message).build()