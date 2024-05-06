package it.unibo.pps.duckgame.utils

import com.google.gson.stream.JsonReader
import it.unibo.pps.duckgame.utils.deserialization.Deserializer
import it.unibo.pps.duckgame.utils.resources.JsonResources

import java.io.{Reader, StringReader}
import scala.collection.mutable.ListBuffer
import scala.io.Source

/** An object that provides utilities for reading JSON files. */
object JsonUtils:

  /** Reads a list of cells from a JSON file.
    * @param jsonResources
    *   the JSON file
    * @param deserializer
    *   the reader of the JSON file
    * @tparam T
    *   the type of the cells
    * @return
    *   the list of cells
    */
  def readCellsType[T](jsonResources: JsonResources, deserializer: Deserializer[T]): List[T] =
    val js = Source.fromInputStream(getClass.getResourceAsStream(jsonResources.path))
    val cells: List[T] = readCells[T](new StringReader(js.mkString), deserializer)
    js.close()
    cells

  private def readCells[T](in: Reader, deserializer: Deserializer[T]): List[T] =
    val reader = new JsonReader(in)
    val cells = new ListBuffer[T]
    reader.beginArray()
    while (reader.hasNext)
      cells += deserializer.read(reader)
    reader.endArray()
    cells.toList
