package it.unibo.pps.duckgame.utils.resources

/** Enumeration of Prolog resources used in the application */
enum PrologResources(_path: String):
  case GAMEUTILS_PROLOG extends PrologResources("prolog/GameUtils.pl")
  case ROLLDICE_PROLOG extends PrologResources("prolog/RollDice.pl")

  /** @retyrn
    *   the path of Prolog's resource
    */
  val path: String = _path
