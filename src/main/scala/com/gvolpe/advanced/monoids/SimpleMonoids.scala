package com.gvolpe.advanced.monoids

object SimpleMonoids {

  trait Monoid[A] {
    def append(a: A, b: => A): A
    def zero: A
  }

  implicit val andMonoid = new Monoid[Boolean] {
    override def append(a: Boolean, b: => Boolean): Boolean = a && b
    override def zero: Boolean = true
  }

  implicit val orMonoid = new Monoid[Boolean] {
    override def append(a: Boolean, b: => Boolean): Boolean = a || b
    override def zero: Boolean = false
  }

  implicit val xorMonoid = new Monoid[Boolean] {
    override def append(a: Boolean, b: => Boolean): Boolean = (a && !b) || (!a && b)
    override def zero: Boolean = false
  }

}