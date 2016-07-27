import scala.language.higherKinds
import scalaz.Functor
import scalaz.Id
import scalaz.syntax.functor._
import scalaz.std.list._
import scalaz.std.option._

def add42[F[_] : Functor](f: F[Int]): F[Int] =
  f.map(_ + 42)

val lists = add42(List(1,2,3))
val option = add42(Option(1))

val int = add42(1: Id.Id[Int])