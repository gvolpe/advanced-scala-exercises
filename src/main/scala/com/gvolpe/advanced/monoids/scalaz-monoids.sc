import scalaz.Monoid
import scalaz.std.anyVal._
import scalaz.std.string._
import scalaz.syntax.monoid._

val instance = Monoid[String]
val name = instance.append("Gabi", instance.zero)
//println(name)

// Custom Monoids
val bitwiseXorInstance: Monoid[Int] = Monoid.instance[Int](_ ^ _, 0)
val number = bitwiseXorInstance.append(12, bitwiseXorInstance.zero)
//println(number)

val stringResult = "Hi" |+| "There" |+| mzero[String]

val intResult = 1 |+| 2 |+| mzero[Int]