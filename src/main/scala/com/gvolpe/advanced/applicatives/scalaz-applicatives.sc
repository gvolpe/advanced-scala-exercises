import scalaz.Applicative
import scalaz.std.option._
import scalaz.std.list._

val inc = (x: Int) => x + 2
Applicative[Option].ap(Some(1))(Some(inc))

val sum3 = (a: Int, b: Int, c: Int) => a + b + c
Applicative[Option].ap3(Some(1), Some(2), Some(3))(Some(sum3))

Applicative[Option].apply3(Some(1), Some(2), Some(3))(sum3)

val optSum3 = Applicative[Option].lift3(sum3)
optSum3(Some(1), Some(3), Some(5))

Applicative[Option].sequence(List(some(1), some(2), some(3)))

// Custom applicatives
/*
val myApplicative = new Applicative[MyType] {
  def ap[A, B](value: => MyType[A])(func: => MyType[A => B]): MyType[B] = ???
  def point[A](value: => A): MyType[A] = ???
}
*/