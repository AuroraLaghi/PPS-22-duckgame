package it.unibo.pps.duckgame.utils

import alice.tuprolog.{Prolog, SolveInfo, Term, Theory}
import it.unibo.pps.duckgame.model.Dice

import java.util.Scanner
import scala.io.{Codec, Source}
import scala.language.implicitConversions
import it.unibo.pps.duckgame.utils.PrologGameUtils.ConversionTerm.given
import it.unibo.pps.duckgame.utils.PrologGameUtils.PrologSolution.given

/** Returns pair of (Int, Int) for two different methods in [[it.unibo.pps.duckgame.utils.GameUtils]] using TuProlog
  * [[alice.tuprolog.Engine]].
  */
object PrologGameUtils:

  /** Contains constructs for building and launching a Prolog engine. */
  object PrologEngine:

    /** Defines an Engine, designed for our purposes. */
    trait Engine:
      /** Solve a [[Term]]
        *
        * @return
        *   solution
        */
      def solve: Term => SolveInfo

      /** Returns coordinate from player's position
        * @param cell
        *   player's position (nth cell in grid)
        * @param cols
        *   columns size of the grid
        * @param rows
        *   rows size of the grid
        * @return
        *   coordinates of player's position in form of (column, row)
        */
      def getCellInGrid(cell: Int, cols: Int, rows: Int): (Int, Int)

      /** Return the coordinate of the nth free slot in a grid of gridSize dimensions
        * @param position
        *   The number of the slot which coordinates are to be returned.
        * @param cis
        *   The dimensions of the grid (default 3 x 2)
        *
        * @return
        *   The coordinates of the nth slot.
        */
      def getFreeSlotInCell(position: Int, cis: Int): (Int, Int)

      /** Return two random values simulating roll of dices
        * @param maxValue
        *   Max Value for random (default 6)
        * @return
        *   A pair of int.
        */
      def randomDice(maxValue: Int): (Int, Int)

    /** Implementations of [[Engine]].
      *
      * @param theory
      *   the theory that the [[Engine]] uses to solve the input query.
      */
    case class PrologEngine(theory: Theory) extends Engine:
      private val engine: Prolog = new Prolog
      engine.setTheory(theory)

      override def getCellInGrid(cell: Int, cols: Int, rows: Int): (Int, Int) =
        val query: String = "getCellInGrid(" + cell + ", " + cols + ", " + rows + ", X, Y)"
        solve(query)

      override def getFreeSlotInCell(position: Int, cis: Int): (Int, Int) =
        val query: String = "getCoordinateFromPosition(" + position + ", " + cis + ", X, Y)"
        solve(query)

      override def randomDice(maxValue: Int): (Int, Int) =
        val query: String = "rollDice(" + maxValue + ", X)"
        (solve(query), solve(query))

      override def solve: Term => SolveInfo = term => engine solve term

  /** Contains useful operator for building [[Theory]]. */
  object PrologTheory:
    given Conversion[String, Theory] = e => Theory.parseWithStandardOperators(getStringTheory(e))

    private def getStringTheory(resourcePath: String): String = Source.fromResource(resourcePath)(Codec.UTF8).mkString

  /** Contains useful operator for building [[Term]]. */
  object ConversionTerm:
    given Conversion[String, Term] = Term.createTerm(_)

  /** Contains useful method for deserializing TuProlog solutions. */
  object PrologSolution:
    given Conversion[SolveInfo, (Int, Int)] = e => colRowFromTerm(e.getTerm("X"))(e.getTerm("Y"))
    given Conversion[SolveInfo, Int] = e => intFromTerm(e.getTerm("X"))

    private def colRowFromTerm(termX: Term)(termY: Term): (Int, Int) =
      (termX.toString.toDouble.toInt, termY.toString.toDouble.toInt)

    private def intFromTerm(term: Term): Int =
      term.toString.toInt
