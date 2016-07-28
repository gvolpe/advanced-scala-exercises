package com.gvolpe.advanced.training.day3

import scala.io.StdIn
import scalaz.concurrent.Task
import scalaz.stream._

object ChatDemo extends App {

  val topic = async.topic[String]()

  val printer = sink.lift[Task, String](x => Task.delay(println(s">> $x")))

  val ex = Exchange(topic.subscribe, topic.publish)

  val reader = Process.repeatEval(Task.delay(StdIn.readLine()))

  val botMatcher = (v: String) => v.toLowerCase match {
    case "scala"    => "Awesome!"
    case "python"   => "Mmmmmm..."
    case "java"     => "You could do better!"
    case "haskell"  => "neeerddddd!!!"
    case _          => v
  }

  val bot = channel.lift[Task, String, String](x => Task.delay(botMatcher(x)))

  val program = merge.mergeN(2)(
    Process(
      reader  through bot to ex.write,
      ex.read to      printer
    )
  )

  program.run.unsafePerformSync

}
