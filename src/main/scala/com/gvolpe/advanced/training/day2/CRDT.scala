package com.gvolpe.advanced.training.day2

import scala.collection.immutable.{Map => SMap}
import scalaz.Monoid
import scalaz.std.iterable._
import scalaz.std.map._
import scalaz.syntax.monoid._
import scalaz.syntax.traverse._

trait BoundedSemiLattice[A] extends Monoid[A]
object BoundedSemiLattice {
  implicit object maxMonoid extends BoundedSemiLattice[Int] {
    override def zero: Int = Int.MinValue
    override def append(f1: Int, f2: => Int): Int = f1 max f2
  }
}

object CRDT {

  type State[+A] = SMap[String, A]

  final case class GCounter[V](state: State[V]) {
    def inc(id: String, amount: V)(implicit ev: Monoid[V]): GCounter[V] = {
      GCounter[V](
        state + (id -> (state.getOrElse(id, mzero[V]) |+| amount))
      )
    }

    def total(implicit ev: Monoid[V]): V = state.values.foldMap[V]()
    // Manual implementation
    //state.values.foldLeft(mzero[V])((acc, v) => acc |+| v)

    def merge(that: GCounter[V])(implicit ev: BoundedSemiLattice[V]): GCounter[V] = {
      GCounter[V](this.state |+| that.state)
    }
  }

}