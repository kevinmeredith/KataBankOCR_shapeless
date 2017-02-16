package net

import shapeless._
import shapeless.nat._
import shapeless.ops.nat.{Mod, Prod, Sum}

// Given 2 HList's, consisting of Nat's entirely, add them together.
trait SumHList[L, M] {
  type S
}
object SumHList {

  type Aux[L, M, O] = SumHList[L, M] {
    type S = O
  }

  def apply[L <: HList, M <: HList](implicit ev: SumHList[L, M]): ev.type = ev

  // credit to http://stackoverflow.com/questions/42115144/sum-of-two-identically-sized-hlists-at-compile-time/42115847#42115847
  implicit def sumZippedInductiveEq5[LH <: Nat, L <: HList, MH <: Nat, M <: HList, RS <: Nat, CS <: Nat, E <: Nat](
    implicit ev: SumHList.Aux[L, M, RS],
    curr: Sum.Aux[LH, MH, CS],
    total: Sum.Aux[CS, RS, E]
  ): SumHList.Aux[LH :: L, MH :: M, E] = new SumHList[LH :: L, MH :: M] {
    type S = E
  }

  implicit val hnils: SumHList.Aux[HNil, HNil, _0] = new SumHList[HNil, HNil] {
    type S = _0
  }
}

// Given an HList of size 3, provide evidence of the sum of HList
// multiplied by _3 :: _2 :: _1 :: HNil
// Example: input: _1 :: _2 :: _2 -> output: _3 + _4 + _2 :: HNil
trait HListProductZipped[L] {
  type Out
}
object HListProductZipped {

  type Aux[L, O] = HListProductZipped[L] { type Out = O }

  def apply[L <: HList](implicit ev: HListProductZipped[L]): ev.type = ev

  implicit def sumTimesLength3[A <: Nat, B <: Nat, C <: Nat, X <: Nat, Y <: Nat, Z <: Nat, O <: HList](
    implicit prodA: Prod.Aux[A, _3, X],
             prodB: Prod.Aux[B, _2, Y],
             prodC: Prod.Aux[C, _1, Z]
  ) =
    new HListProductZipped[A :: B :: C :: HNil] {
      type Out = X :: Y :: Z :: HNil
    }
}

trait HListSum[L] {
  type Out
}
object HListSum {

  type Aux[L, O] = HListSum[L] { type Out = O }

  def apply[L <: HList](implicit ev: HListSum[L]): ev.type = ev

  implicit def hListSumInductive[H <: Nat, L <: HList, S <: Nat, T <: Nat](
    implicit rest: HListSum.Aux[L, T],
             all: Sum.Aux[H, T, S]): HListSum.Aux[H :: L, S] = new HListSum[H :: L] {
    type Out = S
  }

  implicit val hlistSumHNil: HListSum.Aux[HNil, _0] = new HListSum[HNil] {
    type Out = _0
  }
}

trait IsValidInductive[L]
object IsValidInductive {

  // valid criterion:
  // 3*d3 + 2*d2 + 1*d1 mod 11 == 0
  def apply[L <: HList](implicit ev: IsValidInductive[L]) = ev

  implicit def wholeIsValid[LH <: Nat, L <: HList, MH <: Nat, M <: HList, P <: Nat](
   implicit ev: SumZippedProduct.Aux[H, _3, P]
            ): IsValidInductive[H :: L] = new IsValidInductive[H :: L] {}

}