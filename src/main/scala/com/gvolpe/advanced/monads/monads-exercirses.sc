import com.gvolpe.advanced.monads._
import scalaz.syntax.monad._

val result1 = success(100)
val result2 = warning(100, "Don't care about it")
val result3 = failure("You should care about this error...")

result1 flatMap(x => Warning(2, "Warning from success"))

result2 map (_ - 5)

for {
  a <- success(1)
  b <- warning(2, "Message1")
  c <- warning(a + b, "Message2")
} yield c * 10