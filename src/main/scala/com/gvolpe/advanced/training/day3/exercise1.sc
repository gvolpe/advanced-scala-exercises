import scalaz.std.anyVal._
import scalaz.stream._

/***
  * Create a Process to emits integers from 0 to 100
  *
  * Transform this Process so iit only has even numbers
  *
  * Transform this Process emitting even numbers so it emits all the even numbers and the
  * odd number that immediately follows each even number
  *
  * Create a Process that emits the missing numbers above
  *
  * Now reconstruct the natural numbers from 0 to 101 by adding in the missing numbers at
  * the start of the Process
  *
  * Create a Process that emits the sum of all the numbers it has seen so far, with input
  * the naturals from 0 to 100.
  *
  */
val zeroToOneHundred = Process.range(0,101)
val evenNumbers = zeroToOneHundred.filter(x => x % 2 == 0 && x != 0)
//  val evenAndOddNumbers = evenNumbers.zip(evenNumbers.map(_ + 1))
val evenAndOddNumbers = evenNumbers interleave evenNumbers.map(_ + 1)
val missingNumbers = Process.emitAll(0 to 1) ++ evenAndOddNumbers
//  val reconstructed = missingNumbers.flatMap{ case (e, o) => Process.emitAll(Seq(e,o)) }
val sum = zeroToOneHundred.foldMap(x => x)
val accum = zeroToOneHundred.scan1(_ + _)

evenNumbers.toSource.runLog.unsafePerformSync
evenAndOddNumbers.toSource.runLog.unsafePerformSync
missingNumbers.toSource.runLog.unsafePerformSync
//  reconstructed.toSource.runLog.unsafePerformSync
sum.toSource.runLog.unsafePerformSync
accum.toSource.runLog.unsafePerformSync