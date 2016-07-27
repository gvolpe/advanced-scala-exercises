package com.gvolpe.advanced.training.day2

import com.gvolpe.advanced.training.day2.CRDT.GCounter

/***
  * Associativity:  (a + b) + c = a + (b + c)
  * Commutativity:  (a + b) = (b + a)
  * Idempotency:    a + a = a
  * Identity:       a + 0 = 0 + a = a
  *
  * Number    > Max
  * Number(+) > Set(intersection)
  * Number(*) > Set(union)
  * Tuple     > Hyperloglog
  *
  * Idempotent Commutative Monoid
  *
  * G-Counter can only GROW
  * PN-Counter can GROW and SHRINK (PN stands for Positive Negative)
  */
object CRDTDemo extends App {

  import scala.collection.immutable.{ Map => SMap }
  import scalaz.std.anyVal._

  val c1 = GCounter[Int](SMap("A" -> 7, "B" -> 3))
  val c2 = GCounter[Int](SMap("A" -> 2, "B" -> 5))

  val c3 = c1.inc("A", 10)

  println(c3.merge(c2))
  println(c3.merge(c1))

  assert(c3.merge(c2) == c2.merge(c3))
}
