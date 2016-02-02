package com.gvolpe.advanced.monads

import scalaz.Monad

trait CustomMonads {

  // A way to define custom Monads
  val optionMonad = new Monad[Option] {
    def bind[A, B](fa: Option[A])(f: (A) => Option[B]): Option[B] = fa flatMap f
    def point[A](a: => A): Option[A] = Some(a)
  }

}
