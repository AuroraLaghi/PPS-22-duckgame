package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.BaseTest
import org.junit.jupiter.api.BeforeEach

class TestGameController extends BaseTest:
  val gameController: GameController = new GameController
  
  @BeforeEach
  override def setUp(): Unit =
    super.setUp()
    gameController.newGame()

