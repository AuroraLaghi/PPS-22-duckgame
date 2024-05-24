package it.unibo.pps.duckgame.model

/** Represents the status of a cell on the Duck Game board.
 *
 * This enum defines two possible cell statuses:
 *  - `STANDARD_CELL`: Indicates a regular cell with no special effects.
 *  - `SPECIAL_CELL`: Indicates a cell with unique gameplay effects triggered when a player lands on it.
 */
enum CellStatus:
  case STANDARD_CELL
  case SPECIAL_CELL


