package com.gvolpe.advanced.typeclasses

import scalaz.Show
import scalaz.std.string._
import scalaz.std.anyVal._
import scalaz.syntax.show._

object AnatomyScalaz extends App {

  implicit val catShow = Show.shows[Cat] { cat =>
    val name = cat.name.shows
    val age = cat.age.shows
    val color = cat.color.shows

    s"$name is a $age year-old $color cat."
  }

  val cat = Cat("Tom", 12, "White & Grey")

  cat.println

}
