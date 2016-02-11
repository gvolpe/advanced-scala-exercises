package com.gvolpe.advanced.extra

import scala.language.higherKinds

/**
  * Some extra exercises here about monads
  *
  * FSiS Part 3 - Applicative monad class
  * https://www.youtube.com/watch?v=VWCtLhH815M
  **/
trait Monad[F[_]] extends Applicative[F] {
  self =>

  def pure[A](a: A): F[A]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  override def apply[X, Y](fx: F[X])(ff: F[X => Y]): F[Y] =
    flatMap(ff)((f: X => Y) => map(fx)(f))

  def flatten[A, B](ffa: F[F[A]]): F[A] =
    flatMap(ffa)(identity) // Same as flatMap(ffa)(fa => fa)

  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))

  def compose[G[_]](implicit G: Monad[G]): Monad[Lambda[X => F[G[X]]]] =
    new Monad[Lambda[X => F[G[X]]]] {
      def pure[A](a: A): F[G[A]] = self.pure(G.pure(a))
      def flatMap[A, B](fga: F[G[A]])(ff: A => F[G[B]]): F[G[B]] = {
        val nested = self.map(fga) { ga => G.map(ga) { a => ff(a)}}
        flatten(nested)
      }
    }
}

object Monad {
  implicit val listMonad: Monad[List] = new Monad[List] {
    def pure[A](a: A): List[A] = List(a)
    def flatMap[A, B](fa: List[A])(f: (A) => List[B]): List[B] = fa.flatMap(f)
  }
  implicit val optionMonad: Monad[Option] = new Monad[Option] {
    def pure[A](a: A): Option[A] = Option(a)
    def flatMap[A, B](fa: Option[A])(f: (A) => Option[B]): Option[B] = fa.flatMap(f)
  }
}
