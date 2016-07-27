package com.gvolpe.advanced.training.day2

object StreamDemo extends App {

  val register = { (cb: Value[Int] => Unit) =>
    Driver.run(100, cb)
  }

  val source = Callback(register)

//  val derp = source.map(identity) zip source.map(identity)
//  val result = derp.foldLeft(())((_,v) => println(v)).run

  val result = source.foldLeft(0)((acc,v) => acc + v).run

  println(s"RESULT: $result")
}
