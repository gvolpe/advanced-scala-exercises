package com.gvolpe.advanced.functors

import scalaz.std.anyVal._
import scalaz.std.string._
import scalaz.std.list._
import FoldMappableSyntax._

object HigherKinds extends App {

  val list = List(1,3,5)
  println(list.foldMap(_.toString))

}



