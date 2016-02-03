import com.gvolpe.advanced.functors.FoldMappableSyntax._
import scalaz.std.anyVal._
import scalaz.std.option._

// FoldMap exercise

val list = List(1, 2, 3)

list.foldMapM(a => some(a))

list.foldMapM()
