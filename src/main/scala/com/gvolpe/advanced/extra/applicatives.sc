import com.gvolpe.advanced.extra.Applicative
import Applicative._

optionApplicative.map(Some(1))(_ + 1)
optionApplicative.map(None: Option[Int])(_ + 1)

listApplicative.map(List(1, 2, 3))(_ + 1)

optionApplicative.map2(Some(1), Some(2))(_ + _)
listApplicative.map2(List(1, 2), List(4, 5))(_ * _)

optionApplicative.map3(Some(1), Some(2), Some(3))(_ + _ + _)

optionApplicative.tuple3(Some(1), Some(2), Some(3))
listApplicative.tuple2(List(1,2,3), List(4,5,6))

optionApplicative.map4(Some(1), Some(2), Some(3), Some(4))(_ + _ + _ + _)

// Composition
val comp = listApplicative compose optionApplicative
comp.map2(List(Some(1), None, Some(2)), List(Some(2), Some(3)))(_ + _)