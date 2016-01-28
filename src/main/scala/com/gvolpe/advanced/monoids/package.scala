package com.gvolpe.advanced

import com.gvolpe.advanced.monoids.SuperAdderApp.Order

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future}
import scalaz.Monoid
import scalaz.syntax.monoid._

package object monoids {

  object OrderOps {
    implicit val orderMonoid: Monoid[Order] = Monoid.instance[Order]((o1, o2) =>
      Order(o1.totalCost + o2.totalCost, o1.quantity + o2.quantity), Order(0, 0))
  }

  object ListMonoid {
    implicit class MonoidOps[A](list: List[A]) {
      def foldMap[B: Monoid](f: A => B = (a: A) => a): B = {
        list.foldLeft(mzero[B])(_ |+| f(_))
      }
    }
    implicit class SeqFoldMapOps[A](seq: Seq[A]) {
      def foldMapP[B: Monoid](f: A => B = (a: A) => a)(implicit ec: ExecutionContext): B = {
        FoldMap.foldMapP(seq)(f)
      }
    }
  }

  object FoldMap {
    def foldMapP[A, B: Monoid](list: Seq[A])(f: A => B = (a: A) => a)(implicit ec: ExecutionContext): B = {
      val group: Iterator[Seq[A]] = list.grouped(2)
      val sum: Iterator[Future[B]] = group map { g =>
        Future {
          g.foldLeft(mzero[B])(_ |+| f(_))
        }
      }
      val result: Future[B] = Future.sequence(sum) map { i =>
        i.foldLeft(mzero[B])(_ |+| _)
      }
      Await.result(result, Duration.Inf)
    }

  }

}
