package com.gvolpe.advanced

import scalaz.Monad

package object monads {

  sealed trait Result[+A]
  final case class Success[A](value: A) extends Result[A]
  final case class Warning[A](value: A, message: String) extends Result[A]
  final case class Failure(message: String) extends Result[Nothing]

  // flatMap and point exercise

  implicit val resultMonad = new Monad[Result] {
    def point[A](a: => A): Result[A] = Success(a)
    def bind[A, B](fa: Result[A])(f: (A) => Result[B]): Result[B] = fa match {
      case Success(value) => f(value)
      case Warning(value, message1) =>
        f(value) match {
          case Success(value) =>
            Warning(value, message1)
          case Warning(value, message2) =>
            Warning(value, s"$message1 $message2")
          case Failure(message2) =>
            Failure(s"$message1 $message2")
        }
      case failure @ Failure(message) => failure
    }
  }

  // To solve the same invariant problem with some(value) and none[T] to return Option[T]
  def success[A](value: A): Result[A] = Success(value)
  def warning[A](value: A, message: String): Result[A] = Warning(value, message)
  def failure[A](message: String): Result[A] = Failure(message)

}
