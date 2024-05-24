package it.unibo.pps.duckgame.utils.resources

/** Enumeration representing TXT resource used in the Duck Game application.
  *
  * @param _path
  *   Indicates the path
  */
enum TxtResources(_path: String):
  case SPECIAL_CELL_TXT extends TxtResources("/config/specialCells.txt")

  /** @return
    *   the path of the TXT resource.
    */
  val path: String = _path
