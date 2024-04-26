package it.unibo.pps.duckgame.model

import org.scalatest.*
import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class PlayerTest extends AnyFlatSpec with should.Matchers:

  val player: Player = Player("Mario")

  "A new player" should "have initial position equal to 0" in {
    player.actualPosition shouldBe 0
  }

  "A new player" should "not result as blocked for one turn" in {
    player.isLocked shouldBe false
  }






