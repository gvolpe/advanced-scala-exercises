package com.gvolpe.advanced.training.day2

import java.util.concurrent.ArrayBlockingQueue

import scalaz.Applicative
import scalaz.syntax.applicative._

sealed trait Value[+A] extends Product with Serializable {
  def map[B](f: A => B): Value[B] = this match {
    case Emit(a) => Emit(f(a))
    case Done    => Done
  }
  def flatMap[B](f: A => Value[B]): Value[B] = this match {
    case Emit(a) => f(a)
    case Done    => Done
  }
}
final case class Emit[A](a: A) extends Value[A]
case object Done extends Value[Nothing]

object Value {
  implicit object valueAp extends Applicative[Value] {
    override def point[A](a: => A): Value[A] = Emit(a)
    override def ap[A, B](fa: => Value[A])(f: => Value[(A) => B]): Value[B] = f match {
      case Emit(ff) => fa match {
        case Emit(a) => Emit(ff(a))
        case Done    => Done
      }
      case Done    => Done
    }
  }
}

sealed trait EventStream[A] extends Product with Serializable {
  def map[B](f: A => B): EventStream[B] = Map(this, f)
  def zip[B](that: EventStream[B]): EventStream[(A,B)] = Zip(this, that)
  def foldLeft[B](initialValue: B)(f: (B,A) => B): Sink[A,B] = Sink(this, initialValue, f)
  def step: Value[A]
}

final case class Sink[A,B](source: EventStream[A], initialValue: B, f: (B,A) => B) {
  def run: B = {
    def loop(current: B): B = source.step match {
      case Emit(a) => loop(f(current, a))
      case Done    => current
    }
    loop(initialValue)
  }
}

final case class Pure[A](a: A) extends EventStream[A] {
 override def step: Value[A] = Emit(a)
}
final case class Map[A,B](source: EventStream[A], f: A => B) extends EventStream[B] {
  override def step: Value[B] = source.step.map(f)
}
final case class Zip[A,B](left: EventStream[A], right: EventStream[B]) extends EventStream[(A,B)] {
  override def step: Value[(A,B)] = (left.step |@| right.step).tupled
//  override def step: Value[(A,B)] = for {
//    a <- left.step
//    b <- right.step
//    v <- Emit((a,b))
//  } yield v
}
final case class Callback[A](cb: (Value[A] => Unit) => Unit) extends EventStream[A] {
  val queue = new ArrayBlockingQueue[Value[A]](1)
  var registered: Boolean = false
  override def step: Value[A] = {
    if (!registered) {
      cb((a: Value[A]) => queue.put(a))
      registered = true
    }
    queue.take()
  }
}

object Driver {
  def run(limit: Int, cb: Value[Int] => Unit): Unit = {
    new Thread {
      var counter = 0
      override def run(): Unit = {
        while (counter < limit) {
          println(s"Thread generating $counter")
          cb(Emit(counter))
          counter = counter + 1
        }
        cb(Done)
      }
    }.start()
  }
}