package com.gvolpe.advanced.extra

import scala.language.higherKinds

/**
  * Some extra exercises here about functors
  *
  * FSiS Part 1 - Type Constructors, Functors, and Kind Projector
  * https://www.youtube.com/watch?v=Dsd4pc99FSY
  **/
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait FunctorLaws {
  def identity[F[_], A](fa: F[A])(implicit F: Functor[F]) = F.map(fa)(a => a) == fa

  def composition[F[_], A, B, C](fa: F[A], f: A => B, g: B => C)(implicit F: Functor[F]) =
    F.map(F.map(fa)(f))(g) == F.map(fa)(f andThen g)
}

object Functor {
  implicit val listFunctor: Functor[List] = new Functor[List] {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa map f
  }

  implicit val optionFunctor: Functor[Option] = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa map f
  }

  // The question mark is not recognized by the Scala compiler.
  // You'll need this plugin https://github.com/non/kind-projector
  implicit def genericFunctor[X]: Functor[X => ?] = new Functor[X => ?] {
    override def map[A, B](fa: X => A)(f: (A) => B): X => B = fa andThen f
  }
}

