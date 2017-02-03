package net

import shapeless._
import nat._
import ops.hlist.Length, ops.nat.{ Mod, Prod, Sum }

trait IsValid[A]
object IsValid {

  def isValid[L <: HList](implicit ev: IsValid[L]) = ???

  implicit def lengthTwo[L <: HList](implicit length: Length.Aux[L, _2]): IsValid[L] =
    new IsValid[L] {}
}