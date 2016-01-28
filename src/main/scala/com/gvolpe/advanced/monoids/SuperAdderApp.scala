package com.gvolpe.advanced.monoids

import scalaz.Monoid
import scalaz.std.anyVal._
import scalaz.std.option._
import scalaz.std.string._
import scalaz.syntax.monoid._
import ListMonoid._
import OrderOps._

object SuperAdderApp extends App {

  val items = List(1,3,5,7)
//  println(SuperAdder.add(items))
//  println(SuperAdderSyntax.add(items))

  val optItems = List(Some(2), None, Some(5))
  //println(SuperAdderSyntax.add(optItems))

  case class Order(totalCost: Double, quantity: Int)

  val order = List(Order(248.0, 5), Order(1000.0, 12))
  //println(SuperAdderSyntax.add(order))

  println(optItems.foldMap())
  println(order.foldMap())

  val stringItems: String = items.foldMap(_.toString)

}

object SuperAdder {
  def add(items: List[Int]): Int = {
    val monoid = Monoid.instance[Int](_ + _, 0)
    items.foldLeft(monoid.zero){ (acc, n) => monoid.append(acc, n) }
  }
}

object SuperAdderSyntax {
  def intAdd(items: List[Int]): Int = {
    items.foldLeft(mzero[Int]){ _ |+| _ }
  }
  def optionAdd(items: List[Option[Int]]): Int = {
    items.foldLeft(mzero[Int]){ (acc, n) => acc + n.getOrElse(mzero[Int]) }
  }
  def add[A](items: List[A])(implicit monoid: Monoid[A]): A = {
    items.foldLeft(mzero[A]){ _ |+| _ }
  }
}