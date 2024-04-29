package it.unibo.pps.duckgame.utils.resources

/** Enumeration of the JSON resources used in the game. */
enum JsonResources(_path: String):
  case SPECIAL_CELLS extends JsonResources("/json/specialCells.json")

  /** @return
    *   the path of the JSON resource.
    */
  val path: String = _path
