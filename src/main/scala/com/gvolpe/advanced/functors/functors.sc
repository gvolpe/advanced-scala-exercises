import scalaz.Functor
import scalaz.std.function._
import scalaz.syntax.functor._

// Functions with a single argument
val func1 = (x: Int) => x.toDouble
val func2 = (y: Double) => y * 2
val func3 = func1 map func2

func3(1)

/*
  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }
*/

val optionFunctor = new Functor[Option] {
  def map[A, B](value: Option[A])(func: A => B): Option[B] =
    value map func
}

optionFunctor.map(Some(123))((a: Int) => a * 2)

//Some(123) map (_ * 2)