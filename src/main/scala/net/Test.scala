package net

import shapeless._, nat._

class Test {

  def main(args: Array[String]) = {
    // after 12 hours on the Typelevel 2.12.1, it had not finished compiling
    //    IsValid[_3 :: _4 :: _5 :: _8 :: _8 :: _2 :: _8 :: _6 :: _5 :: HNil]
    //    ()
  }

}
