package it.unibo.pps.duckgame.utils.resources

/** Enumeration representing PROLOG resources used in the Duck Game application.
  *
  * @param _path
  *   Indicates the path
  */
enum PrologResources(_path: String):
  case GAMEUTILS_PROLOG extends PrologResources("prolog/GameUtils.pl")
  case ROLLDICE_PROLOG extends PrologResources("prolog/RollDice.pl")

  /** @return
    *   the path of Prolog's resource
    */
  val path: String = _path
