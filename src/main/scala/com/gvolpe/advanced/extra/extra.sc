import com.gvolpe.advanced.extra.Functor
import com.gvolpe.advanced.extra.Functor._

import scala.language.higherKinds

// Some extra exercises here
//
// FSiS Part 1 - Type Constructors, Functors, and Kind Projector
// https://www.youtube.com/watch?v=Dsd4pc99FSY

// So F[_] takes a type constructor of one argument (kind of one)
// for example: List[Int] which kind is F[+A]

def bar[F[_], A](x: F[A], y: F[A]) = null

bar(List(1,2,3), List(2))

bar(Some(1), Some(2))

// Trying out our implementation
val functor = implicitly[Functor[List]]
functor.map(List(1,2,3))(_ * 3)

implicitly[Functor[Option]].map(Some(1))(_ + 1)

val genFunctor = implicitly[Functor[Int => ?]]
val func = genFunctor.map(_ + 1)(_ + 2)