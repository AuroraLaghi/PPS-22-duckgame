package it.unibo.pps.duckgame.utils.resources

/** Enumeration representing CSS resource used in the Duck Game application.
  *
  * This enum is likely used for managing CSS resource paths within the Duck Game application. It defines different CSS
  * stylesheets used for styling the user interface (UI) elements.
  *
  * @param _path
  *   Indicates the path
  */
enum CssResources(_path: String):
  case GAME_STYLE extends CssResources("/css/UIStyle.css")

  /** @return
    *   the path of Css's resource
    */
  val path: String = _path
