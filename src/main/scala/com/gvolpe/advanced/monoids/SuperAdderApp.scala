package com.gvolpe.advanced.monoids

import scalaz.Monoid
import scalaz.std.anyVal._
import scalaz.syntax.monoid._

object SuperAdderApp extends App {

  val items = List(1,3,5,7)
  println(SuperAdder.add(items))
  println(SuperAdderSyntax.add(items))

}

object SuperAdder {
  def add(items: List[Int]): Int = {
    val monoid = Monoid.instance[Int](_ + _, 0)
    items.foldLeft(monoid.zero){ (acc, n) => monoid.append(acc, n) }
  }
}

object SuperAdderSyntax {
  def add(items: List[Int]): Int = {
    items.foldLeft(mzero[Int]){ (acc, n) => acc |+| n }
  }
}