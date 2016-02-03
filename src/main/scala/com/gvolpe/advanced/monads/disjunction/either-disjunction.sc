import scalaz.{\/-, \/}
import scalaz.syntax.either._
import scalaz.syntax.std.string._

// Either is unbiased, you need to choose right or left before flatMap
Right[String, Int](123).right.flatMap(x => Right(x * 2))
Right[String, Int](123).left.flatMap(x => Left("Hi there!"))

// For disjunction, right is always the successful path
\/.right(123)
\/.right(1).flatMap(x => \/-(x + 2))

// Using Scalaz syntax
1.right[String].flatMap(x => (x * 3).right)

for {
  x <- 1.right[String]
  y <- 2.right[String]
} yield x*x + y*y

1.right[String].fold(
  l = l => s"FAIL!",
  r = r => s"SUCCESS: $r!"
)

1.left[Int].fold(
  l = l => s"FAIL!",
  r = r => s"SUCCESS: $r!"
)

1.right[String].getOrElse(0)
"Halo".left[Int].getOrElse(2)

1.right[String] orElse 2.right[String]
"No integer".left[Int] orElse 2.right[String]

// Swapping flow
123.right[String].swap

// Fail-fast error handling. Computations stop running when some error
for {
  a <- 1.right[String]
  b <- 0.right[String]
  c <- if(b == 0) "DIV0".left[Int] else (a/b).right[String]
} yield c * 100

// Representing errors
sealed trait LoginError
final case class UserNotFound(username: String) extends LoginError
final case class PasswordIncorrect(username: String) extends LoginError
trait UnexpectedLoginError extends LoginError

type LoginResult[A] = LoginError \/ A

"Cat".parseInt.disjunction
"1".parseInt.disjunction