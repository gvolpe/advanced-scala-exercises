package com.gvolpe.advanced.typeclasses

import PrintDefaults._

object Anatomy extends App {

  val name = "Gabi"
  val age = 28

  Print.accepts(name)
  Print.accepts(age)

}

trait Printable[A] {
  def format(a: A): String
}

object Print {
  def format[A](a: A)(implicit printable: Printable[A]): String = {
    printable.format(a)
  }
  def accepts[A](a: A)(implicit printable: Printable[A]): Unit = {
    println(printable.format(a))
  }
}