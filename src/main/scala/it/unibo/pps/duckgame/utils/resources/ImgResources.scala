package it.unibo.pps.duckgame.utils.resources

enum ImgResources(_path: String):
  case GAMEBOARD extends ImgResources("/img/gameboard.png")
  
  val path: String = _path