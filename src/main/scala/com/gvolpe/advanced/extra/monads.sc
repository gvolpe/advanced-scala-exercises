import com.gvolpe.advanced.extra.Monad
import Monad._

import scala.language.higherKinds

val comp = listMonad compose optionMonad

// StackOverflowError because flatten is implemented in terms of flatMap and flatMap in terms of flatten
//comp.flatMap(List(Some(1), None, Some(2)))(x => List(Some(1)))