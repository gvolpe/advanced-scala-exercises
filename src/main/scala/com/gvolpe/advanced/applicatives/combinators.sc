import scalaz.Monad
import scalaz.syntax.std.string._
import scalaz.syntax.monad._

sealed trait Result[+A]
final case class Pass[+A](value: A) extends Result[A]
final case class Fail(errors: List[String]) extends Result[Nothing]

implicit val resultMonad = new Monad[Result] {
  def point[A](a: => A): Result[A] = Pass(a)
  def bind[A, B](fa: Result[A])(f: (A) => Result[B]): Result[B] = fa match {
    case Pass(value) => f(value)
    case fail @ Fail(errors) => fail
  }
}

// Values within a context
def readInt(str: String): Result[Int] = {
  println("Reading " + str)
  str.parseInt.disjunction.fold(
    exn => Fail(List(s"Error reading $str")),
    num => Pass(num)
  )
}

// Combining values outside the context
def sum2(a: Int, b: Int): Int = a + b

// Combinator
trait Combinator {
  def apply2[A, B, C](a: => Result[A],
                      b: => Result[B],
                      f: (A, B) => C): Result[C]
}

object MonadicCombinator extends Combinator {
  def apply2[A, B, C](a: => Result[A], b: => Result[B], f: (A, B) => C): Result[C] =
    a.flatMap(a => b.map(b => f(a, b)))
}

object KeepLeftCombinator extends Combinator {
  def apply2[A, B, C](a: => Result[A], b: => Result[B], f: (A, B) => C): Result[C] = (a, b) match {
    case (Pass(a), Pass(b)) => Pass(f(a, b))
    case (Pass(a), Fail(f)) => Fail(f)
    case (Fail(e), Pass(b)) => Fail(e)
    case (Fail(e), Fail(f)) => Fail(e)
  }
}

object KeepAllCombinator extends Combinator {
  def apply2[A, B, C](a: => Result[A], b: => Result[B], f: (A, B) => C): Result[C] = (a, b) match {
    case (Pass(a), Pass(b)) => Pass(f(a, b))
    case (Pass(a), Fail(f)) => Fail(f)
    case (Fail(e), Pass(b)) => Fail(e)
    case (Fail(e), Fail(f)) => Fail(e ++ f)
  }

  def apply2_curried[A, B, C](a: => Result[A], b: => Result[B], f: (A, B) => C): Result[C] = {
    ap(b)(ap(a)(Pass(f.curried)))
  }

  def ap[A, B](a: => Result[A])(b: Result[A => B]): Result[B] = (a, b) match {
    case (Pass(a), Pass(b)) => Pass(b(a))
    case (Pass(a), Fail(f)) => Fail(f)
    case (Fail(e), Pass(b)) => Fail(e)
    case (Fail(e), Fail(f)) => Fail(e ++ f)
  }
}

MonadicCombinator.apply2(readInt("abc"), readInt("def"), sum2)
MonadicCombinator.apply2(readInt("123"), readInt("def"), sum2)
MonadicCombinator.apply2(readInt("12"), readInt("8"), sum2)

KeepLeftCombinator.apply2(readInt("abc"), readInt("def"), sum2)

KeepAllCombinator.apply2(readInt("abc"), readInt("def"), sum2)
KeepAllCombinator.apply2_curried(readInt("21"), readInt("def"), sum2)