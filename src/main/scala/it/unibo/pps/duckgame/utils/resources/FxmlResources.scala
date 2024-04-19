package it.unibo.pps.duckgame.utils.resources

enum FxmlResources(_path: String):
  case START_MENU extends FxmlResources("/fxml/StartMenuFXML.fxml")
  case GAME_VIEW extends FxmlResources("/fxml/GameFXML.fxml")

  case PLAYER_MENU extends FxmlResources("/fxml/PlayersMenuFXML.fxml")

  val path: String = _path