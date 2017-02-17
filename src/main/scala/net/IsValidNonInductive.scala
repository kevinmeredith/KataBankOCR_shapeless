package net

import shapeless.{::, HNil, _}
import ops.hlist.IsHCons
import nat._
import ops.nat.{Mod, Prod, Sum}

trait IsValidNonInductive[A]
object IsValidNonInductive {

  def apply[L <: HList](implicit ev: IsValidNonInductive[L]) = ev

  //  account number:  3  4  5  8  8  2  8  6  5
  //  position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1
  //
  //  checksum calculation:
  //    (d1+2*d2+3*d3 +..+9*d9) mod 11 = 0
  implicit def validNumber[D9 <: Nat, D8 <: Nat, D7 <: Nat, D6 <: Nat, D5 <: Nat, D4 <: Nat, D3 <: Nat, D2 <: Nat, D1 <: Nat,
                           P9 <: Nat, P8 <: Nat, P7 <: Nat, P6 <: Nat, P5 <: Nat, P4 <: Nat, P3 <: Nat, P2 <: Nat, P1 <: Nat,
                           S1 <: Nat, S2 <: Nat, S3 <: Nat, S4 <: Nat, S5 <: Nat, S6 <: Nat, S7 <: Nat, S8 <: Nat](
    implicit p9: Prod.Aux[D9, _9, P9],
    p8 : Prod.Aux[D8, _8, P8],
    p7 : Prod.Aux[D7, _7, P7],
    p6 : Prod.Aux[D6, _6, P6],
    p5 : Prod.Aux[D5, _5, P5],
    p4 : Prod.Aux[D4, _4, P4],
    p3 : Prod.Aux[D3, _3, P3],
    p2 : Prod.Aux[D2, _2, P2],
    p1 : Prod.Aux[D1, _1, P1],
    s1: Sum.Aux[P9, P8, S1],
    s2: Sum.Aux[P7, S1, S2],
    s3: Sum.Aux[P6, S2, S3],
    s4: Sum.Aux[P5, S3, S4],
    s5: Sum.Aux[P4, S4, S5],
    s6: Sum.Aux[P3, S5, S6],
    s7: Sum.Aux[P2, S6, S7],
    s8: Sum.Aux[P1, S7, S8],
    mod: Mod.Aux[S8, _11, _0]
  ): IsValidNonInductive[D9 :: D8 :: D7 :: D6 :: D5 :: D4 :: D3 :: D2 :: D1 :: HNil] = {
    new IsValidNonInductive[D9 :: D8 :: D7 :: D6 :: D5 :: D4 :: D3 :: D2 :: D1 :: HNil] {}
  }


}