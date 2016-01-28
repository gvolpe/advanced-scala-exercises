package com.gvolpe.advanced

package object typeclasses {

  object PrintDefaults {
    implicit val stringPrintable = new Printable[String] {
      override def format(a: String): String = s"S:[$a]"
    }
    implicit val intPrintable = new Printable[Int] {
      override def format(a: Int): String = s"I:[$a]"
    }
  }

}
