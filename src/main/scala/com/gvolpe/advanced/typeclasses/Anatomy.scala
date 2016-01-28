package com.gvolpe.advanced.typeclasses

import PrintDefaults._
import CatDefaults._
import PrintSyntax._

object Anatomy extends App {

  val name = "Gabi"
  val age = 28

  Print.print(name)
  Print.print(age)

  val cat = Cat("Felix", 7, "Grey")

  //  Print.print(cat)

  cat.print
}


trait Printable[A] {
  def format(a: A): String
}

object Print {
  def format[A](a: A)(implicit printable: Printable[A]): String = {
    printable.format(a)
  }
  def print[A](a: A)(implicit printable: Printable[A]): Unit = {
    println(format(a))
  }
}