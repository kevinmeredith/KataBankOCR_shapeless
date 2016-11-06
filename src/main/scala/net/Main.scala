package net

import shapeless._
import nat._
import ops.hlist.Length, ops.nat.{ Mod, Prod, Sum }

trait HasChecksum[L <: HList, S <: Nat]
object HasChecksum {
 // implicit val hasCheckSum
}

object Main {

  //def nineNatsLtEq9[N <: Nat, L <: HList](implicit ev: Sized[L, _9], ev2: LTEq[N, _9]) = ???

  def isValid[L <: HList](implicit ev: Sized[L, _9]) = ???

  private def 

//  def natNOfSize9[N <: Nat](n: N): Sized[List[N], _9] = Sized[List](n, n, n)

  // checksum calculation:
  //(d1+2*d2+3*d3 +..+9*d9) mod 11 = 0

}