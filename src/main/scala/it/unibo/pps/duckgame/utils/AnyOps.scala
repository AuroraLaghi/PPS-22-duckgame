package it.unibo.pps.duckgame.utils

import scala.annotation.targetName

/** Implicit conversion class that adds comparison methods to any type.
  *
  * @param self
  *   The value of any type to which the extension methods are applied.
  */
@SuppressWarnings(Array("org.wartremover.warts.Equals"))
implicit final class AnyOps[A](self: A):

  /** Compares the current value for equality with another value.
    *
    * This method is a syntactic sugar for the standard Scala `==` operator.
    *
    * @param other
    *   The value to compare with.
    * @return
    *   `true` if the values are equal, `false` otherwise.
    */
  @targetName("Equal")
  def ===(other: A): Boolean = self == other

  /** Compares the current value for inequality with another value.
    *
    * This method is a syntactic sugar for the standard Scala `!=` operator.
    *
    * @param other
    *   The value to compare with.
    * @return
    *   `true` if the values are not equal, `false` otherwise.
    */
  @targetName("NotEqual")
  def =/=(other: A): Boolean = self != other
