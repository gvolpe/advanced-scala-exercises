import scalaz.Monad
import scalaz.std.option._
import scalaz.std.list._
import scalaz.syntax.monad._

def sumSquare[A[_] : Monad]: A[Int] = {
  val a = 3.point[A]
  val b = 4.point[A]
  a flatMap (x => b map (y => x*x + y*y))
}

sumSquare[Option]
sumSquare[List]

def sumSquareForComprehension[A[_] : Monad]: A[Int] = {
  for {
    x <- 3.point[A]
    y <- 4.point[A]
  } yield x*x + y*y
}

sumSquareForComprehension[Option]
sumSquareForComprehension[List]