package net

import shapeless._
import shapeless.nat._
import shapeless.ops.hlist.Length
import shapeless.ops.nat.{Mod, Prod, Sum}

// Given an HList of size 3, provide evidence of the sum of HList
// multiplied by _3 :: _2 :: _1 :: HNil
// Example: input: _1 :: _2 :: _2 -> output: _3 + _4 + _2 :: HNil
trait HListProductZipped[L <: HList] {
  type Out <: HList
}
object HListProductZipped {

  type Aux[L <: HList, Out1 <: HList] = HListProductZipped[L] { type Out = Out1 }

  def apply[L <: HList](implicit ev: HListProductZipped[L]): Aux[L, ev.Out] = ev

  implicit def induct[H <: Nat, T <: HList, L <: Nat](
    implicit ev: Length.Aux[H :: T, L],
             prod: Prod[H, L],
             rest: HListProductZipped[T]
  ): HListProductZipped.Aux[H :: T, prod.Out :: rest.Out] = new HListProductZipped[H :: T] {
    type Out = prod.Out :: rest.Out
  }

  implicit val hnilHListProductZipped: HListProductZipped.Aux[HNil, HNil] = new HListProductZipped[HNil] {
    type Out = HNil
  }

}

trait HListSum[L <: HList] {
  type Out <: Nat
}
object HListSum {

  type Aux[L <: HList, O <: Nat] = HListSum[L] { type Out = O }

  def apply[L <: HList](implicit ev: HListSum[L]): Aux[L, ev.Out] = ev

  implicit def hListSumInductive[H <: Nat, L <: HList, S <: Nat, TS <: Nat](
    implicit rest: HListSum.Aux[L, S],
             sum: Sum.Aux[H, S, TS]): HListSum.Aux[H :: L, TS] = new HListSum[H :: L] {
    type Out = sum.Out
  }

  implicit val hlistSumHNil: HListSum.Aux[HNil, _0] = new HListSum[HNil] {
    type Out = _0
  }
}


//  account number:  3  4  5  8  8  2  8  6  5
//  position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1
//
//  checksum calculation:
//    (d1+2*d2+3*d3 +..+9*d9) mod 11 = 0
trait IsValidInductive[L <: HList]
object IsValidInductive {
  def apply[L <: HList](implicit ev: IsValidInductive[L]) = ev

  implicit def wholeIsValid[H <: Nat, T <: HList, L <: HList, S <: Nat](
   implicit length: Length.Aux[H :: T, _9],
            ev: HListProductZipped.Aux[H :: T, L],
            sum: HListSum.Aux[L, S],
            mod: Mod.Aux[S, _11, _0]
            ): IsValidInductive[H :: T] = new IsValidInductive[H :: T] {}

}