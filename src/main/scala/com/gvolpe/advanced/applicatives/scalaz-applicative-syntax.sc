import scalaz.Applicative
import scalaz.syntax.applicative._
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

implicit val keepAllAp = new Applicative[Result] {
  def point[A](a: => A): Result[A] = Pass(a)
  def ap[A, B](fa: => Result[A])(f: => Result[(A) => B]): Result[B] = (fa, f) match {
    case (Pass(a), Pass(b)) => Pass(b(a))
    case (Pass(a), Fail(e)) => Fail(e)
    case (Fail(e), Pass(b)) => Fail(e)
    case (Fail(e1), Fail(e2)) => Fail(e1 ++ e2)
  }
}

(readInt("123") |@| readInt("bar") |@| readInt("baz"))(sum3)

val res = readInt("123") |@| readInt("456") |@| readInt("789")
res.apply(sum3)