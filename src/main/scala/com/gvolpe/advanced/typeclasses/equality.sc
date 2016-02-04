import scalaz.Equal
import scalaz.std.anyVal._
import scalaz.std.option._
import scalaz.std.string._
import scalaz.syntax.equal._

val intEqual = Equal[Int]

val eq1: Boolean = 123 === 123
val eq2: Boolean = 123 =/= 123

// Sugar syntax for: (Some(1) : Option[Int]) === (None : Option[Int])
val eq3: Boolean = some(1) === none[Int]

case class Dog(name: String, age: Int, owner: Option[String])

val dog1 = Dog("Doberman", 35, Some("Guille"))
val dog2 = Dog("Pepe", 30, Some("Migue"))
val optionDog1: Option[Dog] = Some(dog1)
val optionDog2: Option[Dog] = Some(dog2)

implicit val dogEqual = Equal.equal[Dog] { (dog1, dog2) =>
  dog1.name === dog2.name && dog1.age === dog2.age
}

optionDog1 === optionDog2