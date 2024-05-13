package it.unibo.pps.duckgame.utils.resources

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.io.{Codec, Source}

class PrologResourcesTest extends AnyFlatSpec with should.Matchers:
  private val NUM_PROLOG_RESOURCES = 2
  private val UTILS_PROLOG_POSITION = 0
  private val DICES_PROLOG_POSITION = 1

  "Resource path for prolog gameUtils file" should "be correct" in {
    PrologResources.GAMEUTILS_PROLOG.path shouldBe "prolog/GameUtils.pl"
  }

  "Resource path for prolog rollDices file" should "be correct" in {
    PrologResources.ROLLDICE_PROLOG.path shouldBe "prolog/RollDice.pl"

  }

  "PrologResources class" should "return the right path at given position" in {
    PrologResources.fromOrdinal(UTILS_PROLOG_POSITION) shouldBe PrologResources.GAMEUTILS_PROLOG
    PrologResources.fromOrdinal(DICES_PROLOG_POSITION) shouldBe PrologResources.ROLLDICE_PROLOG
  }

  "If asked an element in a non-existent position it" should "throw an exception" in {
    an[NoSuchElementException] should be thrownBy {
      TxtResources.fromOrdinal(PrologResources.values.length + 1)
    }
  }

  "Resources obtained" should "be non-null" in {
    Source.fromResource(PrologResources.GAMEUTILS_PROLOG.path)(Codec.UTF8).mkString should not be null
  }

  "Number of resources" should "have length of resources.values" in {
    PrologResources.values.length shouldBe NUM_PROLOG_RESOURCES
  }
