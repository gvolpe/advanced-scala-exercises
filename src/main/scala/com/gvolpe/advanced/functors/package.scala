package com.gvolpe.advanced

import scala.language.higherKinds
import scalaz.Id._
import scalaz.{Monad, Functor, Monoid}
import scalaz.syntax.monoid._
import scalaz.syntax.monad._

package object functors {

  trait FoldMappable[F[_]] {
    def foldMap[A, B: Monoid](fa: F[A])(f: A => B): B
    def foldMapM[A, M[_] : Monad, B: Monoid](seq: F[A])(f: A => M[B]): M[B]
  }

  object FoldMappable {
    def apply[F[_] : FoldMappable]: FoldMappable[F] = implicitly[FoldMappable[F]]

    implicit object ListFoldMappable extends FoldMappable[List] {
      def foldMap[A, B: Monoid](fa: List[A])(f: (A) => B): B = {
        fa.foldLeft(mzero[B])(_ |+| f(_))
      }
      def foldMapAsMonad[A, B: Monoid](fa: List[A])(f: (A) => B): B = {
        foldMapM[A, Id, B](fa)(f(_).point[Id])
      }
      def foldMapM[A, M[_] : Monad, B: Monoid](seq: List[A])(f: A => M[B] = (a: A) => a.point[Id]): M[B] =
        seq.foldLeft(mzero[B].point[M]){ (acc, item) =>
          for {
            a <- acc
            b <- f(item)
          } yield a |+| b
        }
    }
  }

  object FoldMappableSyntax {
    implicit class FoldMappableOps[F[_]: FoldMappable, A](fa: F[A]) {
      def foldMap[B: Monoid](f: (A) => B): B = {
        FoldMappable[F].foldMap(fa)(f)
      }
      def foldMapM[M[_] : Monad, B: Monoid](f: A => M[B] = (a: A) => a.point[Id]): M[B] = {
        FoldMappable[F].foldMapM(fa)(f)
      }
    }
  }

  // Last exercise

  sealed trait Result[+A]
  final case class Success[A](value: A) extends Result[A]
  final case class Warning[A](value: A, message: String) extends Result[A]
  final case class Failure(message: String) extends Result[Nothing]

  implicit val resultFunctor = new Functor[Result] {
    override def map[A, B](result: Result[A])(f: (A) => B): Result[B] = result match {
      case Success(value) => Success(f(value))
      case Warning(value, message) => Warning(f(value), message)
      case failure @ Failure(message) => failure
    }
  }

  // To solve the same invariant problem with some(value) and none[T] to return Option[T]
  def success[A](value: A): Result[A] = Success(value)
  def warning[A](value: A, message: String): Result[A] = Warning(value, message)
  def failure[A](message: String): Result[A] = Failure(message)

}
