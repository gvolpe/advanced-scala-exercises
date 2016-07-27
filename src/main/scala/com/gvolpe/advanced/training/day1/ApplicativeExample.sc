import scala.language.higherKinds
import scalaz.syntax.applicative._
import scalaz.syntax.validation._
import scalaz.std.list._
import scalaz.std.option._

val ap = Option(1) |@| Option(2)
ap.tupled

val l = List(1,2,3) |@| List(4,5,6)
l.tupled

val username = "No user exists".failureNel
val password = "Password incorrect".failureNel
val result = (username |@| password).tupled