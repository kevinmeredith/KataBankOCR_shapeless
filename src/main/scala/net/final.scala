package net

import shapeless._, nat._
import shapeless.ops.hlist.Length
import shapeless.ops.nat.{Mod, Prod, Sum}

trait HasCheckSum[L <: HList] {
	type C <: Nat
}
object HasCheckSum {

	type Aux[L <: HList, Out1 <: Nat] = HasCheckSum[L] { type C = Out1 }

	def apply[L <: HList](implicit ev: HasCheckSum[L]): Aux[L, ev.C] = ev

    implicit def hlistCheckSum[H <: Nat, T <: HList, Len <: Nat, Cs <: Nat, P <: Nat, M <: Nat, S <: Nat, FMod <: Nat](
	  implicit rest:     HasCheckSum.Aux[T, Cs],
	           length:   Length.Aux[H :: T, Len],
	           prod:     Prod.Aux[H, Len, P],
	           mod:      Mod.Aux[P, _11, M],
	           sumMod:   Sum.Aux[M, Cs, S],
	           finalMod: Mod.Aux[S, _11, FMod]
	): Aux[H :: T, FMod] = new HasCheckSum[H :: T] {
    	type C = FMod
	}

	implicit val hNilCheckSum: Aux[HNil, _0] = new HasCheckSum[HNil] {
		type C = _0
	}

}

trait FinalValid[L <: HList]
object FinalValid {
  def apply[L <: HList](implicit ev: IsValidInductive[L]) = ev

  implicit def finalValidEv[L <: HList](
  	implicit nine:     Length.Aux[L, _9], 
  	         checkSum: HasCheckSum.Aux[L, _0]
  ): FinalValid[L] = new FinalValid[L] {}
}