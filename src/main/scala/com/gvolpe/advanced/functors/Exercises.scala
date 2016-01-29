package com.gvolpe.advanced.functors

import scalaz.std.string._
import FoldMappableSyntax._
import scalaz.syntax.functor._

object Exercises extends App {

  // HigherKinds exercise

  val list = List(1,3,5)
  println(list.foldMap(_.toString))

  // Last exercise
  val result1 = success(100)
  val mapResult1 = result1 map (_ * 2)

  val result2 = warning(100, "Don't care about it")
  val mapResult2 = result2 map (_ * 2)

  val result3 = failure("You should care about this error...")
  val mapResult3 = result3 map ((a: String) => a.toUpperCase)

  println(mapResult1)
  println(mapResult2)
  println(mapResult3)

}





