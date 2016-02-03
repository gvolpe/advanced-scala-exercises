import scalaz.Id._
import scalaz.Monad
import scalaz.std.option._
import scalaz.std.list._
import scalaz.std.vector._
import scalaz.syntax.monad._

Monad[Option].point(1)
Monad[List].point(3)
Monad[Vector].point(2)
Monad[List].bind(List(1,2,3))(x => List(x, x * 10))

Monad[Option].tuple3(some(1), some("hi"), some(3.0))

Monad[Option].sequence(List(some(1), some(2), some(3)))

// The Id monad
3.point[Id]

3.point[Id] flatMap (_ + 2)

3.point[Id] + 2