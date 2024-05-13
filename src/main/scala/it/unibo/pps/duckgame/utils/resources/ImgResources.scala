package it.unibo.pps.duckgame.utils.resources

/** Enumeration representing IMG resources used in the Duck Game application.
  *
  * @param _path
  *   Indicates the path
  */
enum ImgResources(_path: String):
  case GAMEBOARD extends ImgResources("/img/gameboard.png")
  case LOGO extends ImgResources("/img/logo.png")
  case TOKEN_BLUE extends ImgResources("/img/token/blue_token.png")
  case TOKEN_RED extends ImgResources("/img/token/red_token.png")
  case TOKEN_YELLOW extends ImgResources("/img/token/yellow_token.png")
  case TOKEN_GREEN extends ImgResources("/img/token/green_token.png")
  case TOKEN_WHITE extends ImgResources("/img/token/white_token.png")
  case TOKEN_BLACK extends ImgResources("/img/token/black_token.png")
  case DICE_1 extends ImgResources("/img/dice/1.png")
  case DICE_2 extends ImgResources("/img/dice/2.png")
  case DICE_3 extends ImgResources("/img/dice/3.png")
  case DICE_4 extends ImgResources("/img/dice/4.png")
  case DICE_5 extends ImgResources("/img/dice/5.png")
  case DICE_6 extends ImgResources("/img/dice/6.png")

  /** @return
    *   the path of Img's resource
    */
  val path: String = _path
