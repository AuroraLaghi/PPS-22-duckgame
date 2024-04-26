package it.unibo.pps.duckgame.model

import it.unibo.pps.duckgame.utils.resources.ImgResources

enum Token(_img: ImgResources):
  case BLUE extends Token(ImgResources.TOKEN_BLUE)
  case RED extends Token(ImgResources.TOKEN_RED)
  case YELLOW extends Token(ImgResources.TOKEN_YELLOW)
  case GREEN extends Token(ImgResources.TOKEN_GREEN)
  case BLACK extends Token(ImgResources.TOKEN_BLACK)
  case WHITE extends Token(ImgResources.TOKEN_WHITE)
  
  val img: ImgResources = _img
