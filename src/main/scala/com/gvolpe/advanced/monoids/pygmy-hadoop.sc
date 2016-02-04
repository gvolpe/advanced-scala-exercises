import com.gvolpe.advanced.monoids.ListMonoid
import ListMonoid._
import scalaz.std.anyVal._
import scalaz.std.string._
import scala.concurrent.ExecutionContext.Implicits.global

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

List(1, 5, 7, 9).foldMapP(_.toString)