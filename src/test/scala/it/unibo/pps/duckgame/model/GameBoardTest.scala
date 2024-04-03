package it.unibo.pps.duckgame.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class GameBoardTest extends AnyFlatSpec with should.Matchers:
  val GAMEBOARD_SIZE = 64
  
  "Gameboard" should "have fixed size" in {
    GameBoard().size shouldBe GAMEBOARD_SIZE
  }
  
  "A new gameboard created with an empty list" should "be empty" in {
    GameBoard(List.empty).size shouldBe 0
  }
