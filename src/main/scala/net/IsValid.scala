package net

import shapeless._
import nat._
import ops.hlist.Length, ops.nat.{ Mod, Prod, Sum }



trait IsValid[A]
object IsValid {

  // sum $ multByPosition $ zipAccountNumberWithPosition numbers) `modNat` 5
  // simplified: given 2 digits, it's valid if (A * 0 + b * 1) mod 5 == 0
  def isValid[L <: HList](implicit ev: IsValid[L]) = ???

  implicit def lengthTwo[L <: HList](implicit length: Length.Aux[L, _2]): IsValid[L] =
    new IsValid[L] {}

  implicit def foo[A <: Nat, B <: Nat, P1 <: Nat, P2 <: Nat, S <: Nat, M <: Nat, L <: A :: B :: HList](
    implicit prod1: Prod.Aux[A, _0, P1],
             prod2: Prod.Aux[B, _1, P2],
             sum: Sum.Aux[P1, P2, S],
             mod: Mod.Aux[S, _5, _0]) = ???

  def twoFields[A, B, C](implicit ev: Generic.Aux[A, B :: C :: HNil]) = ???
}