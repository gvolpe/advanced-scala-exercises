import scala.language.higherKinds
import scalaz.Id
import scalaz.Monad
import scalaz.syntax.monad._
import scalaz.syntax.std.option._
import scalaz.std.list._
import scalaz.std.option._

def add42[F[_] : Monad](f: F[Int]): F[Int] =
  f.flatMap(x => (x + 42).pure[F])

val lists = add42(List(1,2,3))
val option = add42(Option(1))

val int = add42(1: Id.Id[Int])

option.toRightDisjunction("error")

def flatMap[X,A,B](fa: X => A)(f: A => (X => B)): X => B = {
  (x: X) => f(fa(x))
}

def pure[X,A](a: A): X => A = _ => a