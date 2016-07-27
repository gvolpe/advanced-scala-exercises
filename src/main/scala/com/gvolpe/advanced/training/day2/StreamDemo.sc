//import com.gvolpe.advanced.training.day2.{EventStream, Pure}
//
//val s1: EventStream[Int] = Pure(4)
//val s2: EventStream[String] = Pure("Hi")
//
//s1.map(_.toDouble).run
//
//s1.zip(s2).run
//
//
//val a: Option[Int] = Some(1)
//val b: Option[Int] = Some(2)
//
//val result: Option[(Int,Int)] = for {
//  x <- a
//  y <- b
//  v <- Some((x,y))
//} yield v
