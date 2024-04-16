package it.unibo.pps.duckgame.model

import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class PlayerTest extends AnyFlatSpec with should.Matchers:

  val player: Player = Player()

  "A new player" should "have initial position equal to 0" in {
    player.actualPosition shouldBe 0
  }




