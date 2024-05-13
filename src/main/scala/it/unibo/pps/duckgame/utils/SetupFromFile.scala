package it.unibo.pps.duckgame.utils

import it.unibo.pps.duckgame.model.specialCell.SpecialCell
import it.unibo.pps.duckgame.utils.config.Parser
import it.unibo.pps.duckgame.utils.config.Parser.Parser
import it.unibo.pps.duckgame.utils.resources.TxtResources

import java.nio.charset.StandardCharsets
import scala.collection.mutable.ListBuffer
import scala.io.{Codec, Source}

object SetupFromFile:
  def setup[T](filePath: String, parser: Parser[T]): List[T] =
    val cells = new ListBuffer[T]
    readLinesFromFile(filePath).flatMap(parser.parse).foreach(c => cells += c)
    cells.toList

  private def readLinesFromFile(filePath: String): Seq[String] =
    Source.fromInputStream(getClass.getResourceAsStream(filePath))(Codec.UTF8).getLines().toList
