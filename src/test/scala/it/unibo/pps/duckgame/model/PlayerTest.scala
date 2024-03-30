package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.BaseTest
import org.junit.jupiter.api.{BeforeEach, Test}
import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

@Test
class PlayerTest extends AnyFlatSpec with should.Matchers:

  val player: Player = Player()

  "A new player" should "have initial position equal to 0" in {
    player.actualPosition shouldBe 0
  }




