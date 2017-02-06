package net

import shapeless._, ops.hlist.IsHCons
import nat._
import ops.hlist.Length, ops.nat.{ Mod, Prod, Sum }

trait IsValid[A]
object IsValid {

  // sum $ multByPosition $ zipAccountNumberWithPosition numbers) `modNat` 5
  // simplified: given 2 digits, it's valid if (A * 0 + b * 1) mod 5 == 0
  def isValid[L <: HList](implicit ev: IsValid[L]) = ???

//  implicit def lengthTwo[L <: HList](implicit length: Length.Aux[L, _2]): IsValid[L] =
//    new IsValid[L] {}

  implicit def kataEv[A <: Nat, B <: Nat, P1 <: Nat, P2 <: Nat, S <: Nat, M <: Nat, L <: A :: B :: HNil](
    implicit prod1: Prod.Aux[A, _0, P1],
             prod2: Prod.Aux[B, _1, P2],
             sum: Sum.Aux[P1, P2, S],
             mod: Mod.Aux[S, _5, _0]): IsValid[L] = new IsValid[L] {}

}

// credit to Dale W. (http://stackoverflow.com/a/42054407/409976)
trait SumEq5[L]
object SumEq5 {

  def apply[L <: HList](implicit ev: SumEq5[L]): SumEq5[L] = ev


  implicit def sumEq5Ev[L <: HList, H <: HList, A <: Nat, B <: Nat](
    implicit evA: IsHCons.Aux[L, A, H],
             evB: IsHCons.Aux[H, B, HNil],
             ev: Sum.Aux[A, B, _5]
  ): SumEq5[L] = new SumEq5[L] {}

}