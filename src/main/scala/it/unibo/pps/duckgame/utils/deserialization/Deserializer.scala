package it.unibo.pps.duckgame.utils.deserialization

import com.google.gson.stream.JsonReader

/** Trait that defines a general Json deserializer
  *
  * @tparam T
  *   the type of object to be read
  */
trait Deserializer[T]:

  /** Reads a Json Reader
    *
    * @param in
    *   the Json Reader to be read
    * @return
    *   the object read
    */
  def read(in: JsonReader): T
