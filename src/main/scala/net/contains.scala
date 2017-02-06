package net

import shapeless.{HList, HNil}
import shapeless.ops.hlist.IsHCons

trait Contains[A] {
  type Out
}
object Contains {

  type Aux[A, O] = Contains[A] { type Out = O }

  def apply[L <: HList, A](implicit ev: Contains.Aux[L, A]) = ev

  implicit def containsHead[L <: HList, L2 <: HList, A]
    (implicit ev: IsHCons.Aux[L, A, L2]): Contains.Aux[L, A] = {
      new Contains[L] {
        type Out = A
      }
    }

  implicit def containsRest[H, L <: HList, L2 <: HList, A]
    (implicit ev: IsHCons.Aux[L, H, L2],
              contains: Aux[L2, A]): Contains.Aux[L, A] = {
      new Contains[L] { type Out = A }
    }
}
