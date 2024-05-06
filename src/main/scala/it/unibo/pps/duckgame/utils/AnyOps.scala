package it.unibo.pps.duckgame.utils

import scala.annotation.targetName

@SuppressWarnings(Array("org.wartremover.warts.Equals"))
implicit final class AnyOps[A](self: A):
  @targetName("Equal")
  def ===(other: A): Boolean = self == other

  @targetName("NotEqual")
  def =/=(other: A): Boolean = self != other
