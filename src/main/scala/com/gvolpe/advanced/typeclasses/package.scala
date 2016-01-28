package com.gvolpe.advanced

import com.gvolpe.advanced.typeclasses.Anatomy.Cat

package object typeclasses {

  object PrintDefaults {
    implicit val stringPrintable = new Printable[String] {
      override def format(a: String): String = a
    }
    implicit val intPrintable = new Printable[Int] {
      override def format(a: Int): String = a.toString
    }
  }

  object CatDefaults {
    import PrintDefaults._
    implicit val carPrintable = new Printable[Cat] {
      override def format(cat: Cat): String = {
        val name = Print.format(cat.name)
        val age = Print.format(cat.age)
        val color = Print.format(cat.color)

        s"$name is a $age year-old $color cat."
      }
    }
  }

  object PrintSyntax {
    implicit class PrintOps[A](a: A) {
      def format(implicit printable: Printable[A]): String = {
        printable.format(a)
      }
      def print(implicit printable: Printable[A]): Unit = {
        println(format)
      }
    }
  }

}
