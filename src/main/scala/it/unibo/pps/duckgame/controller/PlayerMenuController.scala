package it.unibo.pps.duckgame.controller

import it.unibo.pps.duckgame.model.{Player, Token}
import it.unibo.pps.duckgame.utils.FxmlUtils
import it.unibo.pps.duckgame.utils.resources.FxmlResources

/** Controller for the [[it.unibo.pps.duckgame.view.PlayersMenuView]] */
object PlayerMenuController:

  /** Called when user want to add a new player to the game
    * @param player
    *   The player to add
    */
  def addPlayer(player: Player): Unit =
    LogicController addPlayer player

  /** Called when user wants to delete one player from the game
    *
    * @param player
    *   the player to delete
    */
  def removePlayer(player: Player): Unit =
    LogicController removePlayer player

  /** Called before adding a new player to the game
    *
    * @return
    *   true if there's space for another player, false otherwise
    */
  def canAddPlayer: Boolean =
    GameReader.canAddPlayer

  /** Called after user presses PLAY button
    *
    * @return
    *   if there are at least two players, false otherwise
    */
  def canStartGame: Boolean =
    GameReader.canStartGame

  /** Called after the canStartGame check returned positive Starts the game and changes scene
    */
  def playGame(): Unit =
    LogicController.startGame()
    FxmlUtils.changeScene(FxmlResources.GAME_VIEW.path)

  /** Called if user wants to close game */
  def exitGame(): Unit =
    LogicController.exitGame()

  def availableToken(): List[Token] =
    GameReader.availableTokens()
    