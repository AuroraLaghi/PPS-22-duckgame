package it.unibo.pps.duckgame.utils.resources

enum CssResources(_path: String):
  case GAME_STYLE extends CssResources("/css/UIStyle.css")
  
  val path: String = _path