package it.unibo.pps.duckgame.utils.resources

/** Enumeration of the TXT resource used in the game. */
enum TxtResources(_path: String):
  case SPECIAL_CELL_TXT extends TxtResources("/config/specialCells.txt")

  /** @return
    *   the path of the TXT resource.
    */
  val path: String = _path
