import com.gvolpe.advanced.functors._

import scalaz.std.string._
import FoldMappableSyntax._
import scalaz.syntax.functor._

// HigherKinds exercise

val list = List(1,3,5)
list.foldMap(_.toString)

// Last exercise
val result1 = success(100)
result1 map (_ * 2)

val result2 = warning(100, "Don't care about it")
result2 map (_ * 2)

val result3 = failure("You should care about this error...")
result3 map ((a: String) => a.toUpperCase)