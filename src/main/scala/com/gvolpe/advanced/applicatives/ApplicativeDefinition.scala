package com.gvolpe.advanced.applicatives

trait ApplicativeDefinition[F[_]] {
  def ap[A, B](fa: => F[A])(f: => F[A => B]): F[B]
  // Sometimes called 'pure'. Same as point in Monads
  def point[A, B](fa: => F[A])(f: A => F[B]): F[B]
}

trait ApplicativeLaws {
  /*
  * Identity: ap(a)(point(x => x)) == a
  * Homomorphism: ap(point(a))(point(b)) == point(b(a))
  * Interchange: ap(point(a))(fb) == ap(fb)(point(x => x(a)))
  * Map-like: map(fa)(fb) == ap(fa)(point(fb))
  * */
}
