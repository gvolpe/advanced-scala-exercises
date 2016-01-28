package com.gvolpe.advanced.monoids

import ListMonoid._
import scalaz.std.anyVal._
import scalaz.std.string._
import scala.concurrent.ExecutionContext.Implicits.global

object PygmyHadoop extends App {

  def time[A](msg: String)(f: => A): A = {
    // Let Hotspot do some work
    f
    val start = System.nanoTime()
    val result = f
    val end = System.nanoTime()
    println(s"$msg took ${end - start} nanoseconds")
    result
  }

  //time("Futures")(List(1,5,7,9))

  println(List(1, 5, 7, 9).foldMapP(_.toString))

}
