import scalaz.{Validation, Applicative, Success, Failure, \/}
import scalaz.std.list._
import scalaz.std.string._
import scalaz.std.vector._
import scalaz.syntax.either._
import scalaz.syntax.std.string._
import scalaz.syntax.validation._

// Fail fast approach

type ErrorFailFast[A] = List[String] \/ A

def fail1: ErrorFailFast[Int] = {
  println("Calling fail1")
  List("Fail1").left
}

def fail2: ErrorFailFast[Int] = {
  println("Calling fail2")
  List("Fail2").left
}

Applicative[ErrorFailFast].apply2(fail1, fail2)(_ + _)
// Accumulation of errors

type ErrorOr[A] = Validation[List[String], A]
def fail1_2: ErrorOr[String] = {
  println("Calling fail1")
  List("Fail1").failure
}

def fail2_2: ErrorOr[Int] = {
  println("Calling fail2")
  List("Fail2").failure
}

Applicative[ErrorOr].apply2(fail1_2, fail2_2)(_ + _)

// Types of Validation:
type StringOr[A] = Validation[String, A]
type ListOr[A] = Validation[List[String], A]
type VectorOr[A] = Validation[Vector[Int], A]

Applicative[StringOr].apply2("Hello".failure[Int], "world".failure[Int])(_ * _)
Applicative[ListOr].apply2(List("Hello").failure[Int], List("world").failure[Int])(_ * _)
Applicative[VectorOr].apply2(Vector(404).failure[Int], Vector(500).failure[Int])(_ * _)

val s: Validation[String, Int] = Success(123)
val f: Validation[String, Int] = Failure("message")

456.success[String]
"error".failure[Int]

"123".parseInt
"123".parseInt.disjunction
"123".parseInt.disjunction.validation

// Transformations
123.success.map(_ * 100)
456.failure.leftMap(_.toString)
123.success[String].bimap(_ + "!", _ * 100)
"fail".failure[Int].bimap(_ + "!", _ * 100)

"fail".failure[Int].getOrElse(0)
"fail".failure[Int].fold(_ + "!!!", _.toString)