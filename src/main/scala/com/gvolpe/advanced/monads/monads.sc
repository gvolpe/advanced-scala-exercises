import scalaz.Monad
import scalaz.std.option._
import scalaz.std.list._
import scalaz.std.vector._

Monad[Option].point(1)
Monad[List].point(3)
Monad[Vector].point(2)
Monad[List].bind(List(1,2,3))(x => List(x, x * 10))

Monad[Option].tuple3(some(1), some("hi"), some(3.0))

Monad[Option].sequence(List(some(1), some(2), some(3)))