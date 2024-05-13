package it.unibo.pps.duckgame.utils.resources

/** Enumeration representing FXML resources used in the Duck Game application.
  *
  * This enum is likely used for managing paths to FXML files within the Duck Game application. FXML (FX Markup
  * Language) is used to define user interface (UI) layouts in JavaFX applications. Each member of this enum represents
  * an FXML file that defines the layout of a specific screen or component in the game's UI.
  *
  * @param _path
  *   Indicates the path
  */
enum FxmlResources(_path: String):
  case START_MENU extends FxmlResources("/fxml/StartMenuFXML.fxml")
  case GAME_VIEW extends FxmlResources("/fxml/GameFXML.fxml")
  case PLAYER_MENU extends FxmlResources("/fxml/PlayersMenuFXML.fxml")

  /** @return
    *   the path of Fxml's resource
    */
  val path: String = _path
