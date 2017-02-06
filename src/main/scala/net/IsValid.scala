package net

import shapeless._, ops.hlist.IsHCons
import nat._
import ops.nat.{ Mod, Prod, Sum }

trait IsValid[A]
object IsValid {

  // sum $ multByPosition $ zipAccountNumberWithPosition numbers) `modNat` 5
  // simplified: given 2 digits, it's valid if (A * 1 + b * 0) mod 5 == 0
  def apply[L <: HList](implicit ev: IsValid[L]) = ev

  implicit def validNumber[L <: HList, H <: HList, A <: Nat, B <: Nat, P1 <: Nat, P2 <: Nat, S <: Nat](
     implicit evA: IsHCons.Aux[L, A, H],
     evB: IsHCons.Aux[H, B, HNil],
     prod1: Prod.Aux[_1, A, P1],
     prod2: Prod.Aux[_0, B, P2],
     sum: Sum.Aux[P1, P2, S],
     mod: Mod.Aux[S, _5, _0]
   ): IsValid[L] = new IsValid[L] {}

}