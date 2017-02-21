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
trait HListProductZipped[L <: HList] {
  type Out <: HList
}
object HListProductZipped {

  type Aux[L <: HList, O <: HList] = HListProductZipped[L] { type Out = O }

  def apply[L <: HList](implicit ev: HListProductZipped[L]): ev.type = ev

  implicit def hlistProductZippedInductive[A <: Nat, B <: Nat, C <: Nat, D <: Nat, E <: Nat, F <: Nat, G <: Nat, H <: Nat, I <: Nat](
    implicit prod9: Prod[A, _9],
             prod8: Prod[B, _8],
             prod7: Prod[C, _7],
             prod6: Prod[D, _6],
             prod5: Prod[E, _5],
             prod4: Prod[F, _4],
             prod3: Prod[G, _3],
             prod2: Prod[H, _2],
             prod1: Prod[I, _1]
  ) =
    new HListProductZipped[A :: B :: C :: D :: E :: F :: G :: H :: I :: HNil] {
      type Out = prod9.Out :: prod8.Out :: prod7.Out :: prod6.Out :: prod5.Out :: prod4.Out :: prod3.Out :: prod2.Out :: prod1.Out :: HNil
    }

//
//  implicit def hlistProduceZippedInductive[A <: Nat, B <: Nat, C <: Nat, D <: Nat, E <: Nat, F <: Nat, G <: Nat, H <: Nat, I <: Nat](
//    implicit prod9: Prod[A, _9],
//             ev: HListProductZipped[B :: C :: D :: E :: F :: G :: H :: I :: HNil]
//  ) =
//    new HListProductZipped[A :: B :: C :: D :: E :: F :: G :: H :: I :: HNil] {
//      type Out = prod9.Out :: ev.Out
//    }


}

trait HListSum[L <: HList] {
  type Out <: Nat
}
object HListSum {

  type Aux[L <: HList, O <: Nat] = HListSum[L] { type Out = O }

  def apply[L <: HList](implicit ev: HListSum[L]): Aux[L, ev.Out] = ev

//  implicit def hListSumInductive[H <: Nat, L <: HList, T <: Nat](
//    implicit rest: HListSum.Aux[L, T],
//             all: Sum[H, T]): HListSum.Aux[H :: L, all.Out] = new HListSum[H :: L] {
//    type Out = all.Out
//  }

  implicit def hListSumInductive[H <: Nat, L <: HList, T <: Nat](
    implicit rest: HListSum.Aux[L, T],
    all: Sum[H, T]): HListSum.Aux[H :: L, all.Out] = new HListSum[H :: L] {
    type Out = all.Out
  }

  implicit val hlistSumHNil: HListSum.Aux[HNil, _0] = new HListSum[HNil] {
    type Out = _0
  }
}


trait IsValidInductive[L <: HList]
object IsValidInductive {

  //  account number:  3  4  5  8  8  2  8  6  5
  //  position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1
  //
  //  checksum calculation:
  //    (d1+2*d2+3*d3 +..+9*d9) mod 11 = 0
  def apply[L <: HList](implicit ev: IsValidInductive[L]) = ev

  implicit def wholeIsValid[H <: Nat, T <: HList, L <: HList, S <: Nat](
   implicit ev: HListProductZipped.Aux[H :: T, L],
            sum: HListSum.Aux[L, S],
            mod: Mod.Aux[S, _11, _0]
            ): IsValidInductive[H :: T] = new IsValidInductive[H :: T] {}


}