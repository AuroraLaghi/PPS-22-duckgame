package it.unibo.pps.duckgame.utils.config

import it.unibo.pps.duckgame.utils.config.Parser.Parser

import scala.collection.mutable.ListBuffer
import scala.io.{Codec, Source}

/** Object for setting up data from a file */
object SetupFromFile:

  /** Reads data from a file, parses each line using the provided parser, and
    * returns a list of parsed objects.
    *
    * @param filePath
    *   The path to the file containing the data.
    * @param parser
    *   A parsing function that takes a string line and returns a T object.
    * @return
    *   A list of T objects parsed from the file.
    */
  def setup[T](filePath: String)(parser: Parser[T]): List[T] =
    val cells = new ListBuffer[T]
    readLinesFromFile(filePath).flatMap(parser.parse).foreach(c => cells += c)
    cells.toList

  /** Reads lines from a file as a sequence of strings.
    *
    * @param filePath
    *   The path to the file.
    * @return
    *   A sequence of strings representing the lines in the file.
    */
  private def readLinesFromFile(filePath: String): Seq[String] =
    Source
      .fromInputStream(getClass.getResourceAsStream(filePath))(Codec.UTF8)
      .getLines()
      .toList
