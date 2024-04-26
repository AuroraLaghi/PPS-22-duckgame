package it.unibo.pps.duckgame.utils.resources

enum ImgResources(_path: String):
  case GAMEBOARD extends ImgResources("/img/gameboard.png")
  case LOGO extends ImgResources("/img/logo.png")
  case TOKEN_BLUE extends ImgResources("/img/token/blue_token.png")
  case TOKEN_RED extends ImgResources("/img/token/red_token.png")
  case TOKEN_YELLOW extends ImgResources("/img/token/yellow_token.png")
  case TOKEN_GREEN extends ImgResources("/img/token/green_token.png")
  case TOKEN_WHITE extends ImgResources("/img/token/white_token.png")
  case TOKEN_BLACK extends ImgResources("/img/token/black_token.png")
  
  val path: String = _path