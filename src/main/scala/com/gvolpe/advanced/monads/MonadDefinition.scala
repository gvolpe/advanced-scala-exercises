package com.gvolpe.advanced.monads

trait MonadDefinition[F[_]] {
  // Also called "bind"
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  // Also called "return"
  def point[A](a: A): F[A]

  // First exercise
  def map[A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(a => point(f(a)))
}

trait MonadLaws {
  /*
  * Left identity: (point(a) flatMap f) == f(a)
  * Right identity: (m flatMap point) == m
  * Associativity: (m flatMap f flatMap g) == (m flatMap (x => f(x) flatMap g))
  * */
}
