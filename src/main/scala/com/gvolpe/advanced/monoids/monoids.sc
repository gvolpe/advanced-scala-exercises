import scalaz.std.anyVal._
import scalaz.std.list._
import scalaz.std.option._
import scalaz.std.string._
import scalaz.syntax.foldable._
import scalaz.syntax.monoid._
import scalaz.syntax.tag._
import scalaz.Tags._
import scalaz.{Tag, @@}

List(1,2,3).foldMap()
List(1,2,3).foldMap(_.toString)

Some(1)
some(1) //Scalaz smart constructor
None
none[Int] //Scalaz smart constructor

Multiplication(1) |+| Multiplication(4)

Multiplication.unwrap(Multiplication(3))

Multiplication(5).unwrap

// @@ represents a type @@ tag (infix of @@ [type, tag])
def double(in: Int @@ Multiplication): Int @@ Multiplication =
  Multiplication(in.unwrap * 2)

sealed trait SampleTag
val sampleTag = Tag.of[SampleTag]

double(Multiplication(7))