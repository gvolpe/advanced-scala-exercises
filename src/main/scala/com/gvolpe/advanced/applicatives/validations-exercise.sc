import scalaz.{Success, Failure, Validation}
import scalaz.std.list._
import scalaz.syntax.std.string._
import scalaz.syntax.applicative._
import scalaz.syntax.validation._

case class User(name: String, age: Int)

type WebForm = Map[String, String]
type Result[A] = Validation[List[String], A]

def readName(form: WebForm): Result[String] = {
  form.get("name") match {
    case None => List("Missing parameter name").failure
    case Some("") => List("Name shouldn't be empty").failure
    case Some(name) => name.success
  }
}

def readAge(form: WebForm): Result[Int] = {
  form.get("age") match {
    case None => List("Missing parameter age").failure
    case Some("") => List("Age shouldn't be empty").failure
    case Some(age) => age.parseInt match {
      case Failure(e) => List("Age must be a number").failure
      case Success(age) if age < 0 => List("Age must be a positive number").failure
      case Success(age) => age.success
    }
  }
}

def readUser(form: WebForm): Result[User] = {
  (readName(form) |@| readAge(form))(User.apply)
  // Same as:
  // Applicative[Result].apply2(readName(form), readAge(form))(User.apply)
}

readUser(Map("name" -> "Gabi", "age" -> "28"))
readUser(Map("name" -> "", "age" -> "28"))
readUser(Map("age" -> "28"))
readUser(Map("name" -> "Gabi", "age" -> "Hi"))
readUser(Map("name" -> "Gabi", "age" -> ""))
readUser(Map("name" -> "Gabi", "age" -> "-5"))
readUser(Map("name" -> "Gabi"))
readUser(Map("name" -> "", "age" -> "-5"))
readUser(Map())