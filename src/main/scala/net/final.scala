package net

import shapeless._, nat._
import shapeless.ops.hlist.Length
import shapeless.ops.nat.{Mod, Prod, Sum}

trait SumOfMods[L <: HList] {
	type C <: Nat
}
object SumOfMods {

	type Aux[L <: HList, Out1 <: Nat] = SumOfMods[L] { type C = Out1 }

	def apply[L <: HList](implicit ev: SumOfMods[L]): Aux[L, ev.C] = ev

    implicit def hlistCheckSum[H <: Nat, T <: HList, Len <: Nat, Cs <: Nat, P <: Nat, M <: Nat](
	  implicit rest:     SumOfMods.Aux[T, Cs],
	           length:   Length.Aux[H :: T, Len],
	           prod:     Prod.Aux[H, Len, P],
	           mod:      Mod.Aux[P, _11, M],
	           sum:      Sum[M, Cs]
	): Aux[H :: T, sum.Out] = new SumOfMods[H :: T] {
    	type C = sum.Out
	}

	implicit val hNilCheckSum: Aux[HNil, _0] = new SumOfMods[HNil] {
		type C = _0
	}
}

trait HasCheckSum[L <: HList]
object HasCheckSum {

	def apply[L <: HList](implicit ev: HasCheckSum[L]): HasCheckSum[L] = ev

	implicit def hasCheckSum[L <: HList, S <: Nat](
		implicit ev:      SumOfMods.Aux[L, S],
		         divBy11: Mod.Aux[S, _11, _0]
	): HasCheckSum[L] = new HasCheckSum[L] {}
}

trait FinalValid[L <: HList]
object FinalValid {
  def apply[L <: HList](implicit ev: SumOfMods[L]) = ev

  implicit def finalValidEv[L <: HList](
  	implicit nine:     Length.Aux[L, _9], 
  	         checkSum: HasCheckSum[L]
  ): FinalValid[L] = new FinalValid[L] {}
}