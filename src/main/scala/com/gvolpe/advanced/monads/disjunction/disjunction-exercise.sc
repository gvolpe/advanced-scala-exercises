import com.gvolpe.advanced.functors.FoldMappableSyntax._
import scalaz.\/
import scalaz.std.anyVal._
import scalaz.syntax.std.string._

type ErrorMonad[A] = NumberFormatException \/ A

List("cat", "dog").foldMapM[ErrorMonad, Int](_.parseInt.disjunction)
List("3", "5").foldMapM[ErrorMonad, Int](_.parseInt.disjunction)
List("1", "3", "ice").foldMapM[ErrorMonad, Int](_.parseInt.disjunction)