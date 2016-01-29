package com.gvolpe.advanced.functors

import scala.language.higherKinds
import scalaz.{Monad, Functor}
import scalaz.std.anyVal._
import scalaz.std.list._
import scalaz.std.option._
import scalaz.syntax.functor._

object ScalazFunctors extends App {

  val list: List[Int] = Functor[List].map(List(1, 2, 3))(_ * 2)
  val option: Option[String] = Functor[Option].map(Some(123))(_.toString)

  println(list)
  println(option)

  def lifted: (Option[Int]) => Option[Int] = Functor[Option].lift((x: Int) => x + 1)

  println(lifted(Some(54)))

  // Using functor syntax

  // FIXME: This doesn't work like the book says. ERROR: value map is not a member of Int => Int
//  val f = ((a: Int) => a + 1) map ((a: Int) => a * 2)
//  println(f(123))

  val func = ((x: Int) => x + 1) lift Monad[Option]
  println(func(Some(87)))
}
