import scalaz.{Monad, Applicative}
import scalaz.syntax.std.string._

sealed trait Result[+A]
final case class Pass[+A](value: A) extends Result[A]
final case class Fail(errors: List[String]) extends Result[Nothing]

def readInt(str: String): Result[Int] = {
  println("Reading " + str)
  str.parseInt.disjunction.fold(
    exn => Fail(List(s"Error reading $str")),
    num => Pass(num)
  )
}

val sum3 = (a: Int, b: Int, c: Int) => a + b + c

val keepLeftAp = new Applicative[Result] {
  def point[A](a: => A): Result[A] = Pass(a)
  def ap[A, B](fa: => Result[A])(f: => Result[(A) => B]): Result[B] = (fa, f) match {
    case (Pass(a), Pass(b)) => Pass(b(a))
    case (Pass(a), Fail(e)) => Fail(e)
    case (Fail(e), Pass(b)) => Fail(e)
    case (Fail(e1), Fail(e2)) => Fail(e1)
  }
}

val keepAllAp = new Applicative[Result] {
  def point[A](a: => A): Result[A] = Pass(a)
  def ap[A, B](fa: => Result[A])(f: => Result[(A) => B]): Result[B] = (fa, f) match {
    case (Pass(a), Pass(b)) => Pass(b(a))
    case (Pass(a), Fail(e)) => Fail(e)
    case (Fail(e), Pass(b)) => Fail(e)
    case (Fail(e1), Fail(e2)) => Fail(e1 ++ e2)
  }
}

keepLeftAp.apply3(readInt("3"), readInt("5"), readInt("4"))(sum3)
keepLeftAp.apply3(readInt("3"), readInt("err"), readInt("4"))(sum3)
keepLeftAp.apply3(readInt("3"), readInt("asd"), readInt("def"))(sum3)

keepAllAp.apply3(readInt("3"), readInt("5"), readInt("4"))(sum3)
keepAllAp.apply3(readInt("3"), readInt("err"), readInt("4"))(sum3)
keepAllAp.apply3(readInt("3"), readInt("asd"), readInt("def"))(sum3)

// Monadic applicative (fail fast strategy)
implicit val failFast = new Monad[Result] {
  def point[A](a: => A): Result[A] = Pass(a)
  def bind[A, B](fa: Result[A])(f: (A) => Result[B]): Result[B] = fa match {
    case Pass(value) => f(value)
    case fail @ Fail(errors) => fail
  }
}

Applicative[Result].apply3(readInt("3"), readInt("5"), readInt("4"))(sum3)
Applicative[Result].apply3(readInt("3"), readInt("asd"), readInt("4"))(sum3)
Applicative[Result].apply3(readInt("def"), readInt("5"), readInt("asd"))(sum3)