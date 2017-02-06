package net

import shapeless.{HList, HNil, Nat}
import shapeless.nat._5
import shapeless.ops.hlist.IsHCons
import shapeless.ops.nat.{Sum}

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