import scalaz.concurrent.Task
import scalaz.stream._

val zinc: Sink[Task, Int] = sink.lift(x => Task.delay(println(x)))
val s = Process.constant((n: Int) => Task.delay(println(n))).toSource

val result = Process(1,2,3) to zinc
val observer = Process(1,3,5) observe s

result.run.unsafePerformSync
observer.run.unsafePerformSync

val c = channel.lift[Task, Int, String](n => Task.delay((n+1).toString))

val zincs: Sink[Task, String] = sink.lift(x => Task.delay(println(x)))
val r = Process(1,2,3) through c to zincs

r.runLog.unsafePerformSync