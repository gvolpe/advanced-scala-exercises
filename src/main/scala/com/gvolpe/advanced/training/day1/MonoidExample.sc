import scalaz.std.anyVal._
import scalaz.std.list._
import scalaz.std.option._
import scalaz.syntax.monoid._
import scalaz.syntax.std.option._

val two = 1 |+| 1

val lists = List(1,2,3) |+| List(4,5,6)

val optionLists = Option(List(1,2,3)) |+| Option(List(4,5,6))

val tricky = some(1) |+| some(2)
val tricky2 = 1.some |+| 2.some