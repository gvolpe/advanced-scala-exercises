import scala.util.Random
import scalaz.concurrent.Task
import scalaz.stream._

val oneForever = Process.constant(1)
val printer = oneForever.evalMap(x => Task.delay(println(x)))
val randomForever = Process.constant(Random.nextDouble)

oneForever.take(10).toSource.runLog.unsafePerformSync
randomForever.take(10).toSource.runLog.unsafePerformSync
printer.take(5).run.unsafePerformSync