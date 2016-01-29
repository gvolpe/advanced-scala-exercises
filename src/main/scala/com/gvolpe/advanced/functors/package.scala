package com.gvolpe.advanced

import scala.language.higherKinds
import scalaz.Monoid
import scalaz.syntax.monoid._

package object functors {

  trait FoldMappable[F[_]] {
    def foldMap[A, B: Monoid](fa: F[A])(f: A => B): B
  }

  object FoldMappable {
    def apply[F[_] : FoldMappable]: FoldMappable[F] = implicitly[FoldMappable[F]]

    implicit object ListFoldMappable extends FoldMappable[List] {
      override def foldMap[A, B: Monoid](fa: List[A])(f: (A) => B): B = {
        fa.foldLeft(mzero[B])(_ |+| f(_))
      }
    }
  }

  object FoldMappableSyntax {
    implicit class FoldMappableOps[F[_]: FoldMappable, A](fa: F[A]) {
      def foldMap[B: Monoid](f: (A) => B): B = {
        FoldMappable[F].foldMap(fa)(f)
      }
    }
  }

}
