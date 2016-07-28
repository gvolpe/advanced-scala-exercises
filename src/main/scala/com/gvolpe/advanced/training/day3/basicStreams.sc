import scalaz.stream._
import scalaz.std.anyVal._
import scalaz.concurrent.Task

val process = Process(1,2,3,4,5)
val runnable = process.toSource
val output = runnable.runLog.unsafePerformSync

def run[A](p: Process[Task, A]): Vector[A] =
  p.runLog.unsafePerformSync

val map = run(process.map(_ + 42).toSource)
val filtered = run(process.filter(_ % 2 == 0).toSource)

Process.range(0,10).toSource.runLog.run

Process.fail(new Exception("error")).toSource.runLog.unsafePerformSyncAttempt

Process.range(0,10).toSource.foldMap(x => x).runLog.run

Task.delay(println("Hola"))