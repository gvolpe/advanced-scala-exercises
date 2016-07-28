import scalaz.stream._

val p1 = Process(0,1,2,3,4)
val p2 = Process(5,6,7,8,9)

val r1 = p1 interleave p2
val r2 = p1 zip p2
val r3 = p1 merge p2
val r4 = p1 yip p2
val r5 = p1 either p2

r1.toSource.runLog.unsafePerformSync
r2.toSource.runLog.unsafePerformSync
r3.runLog.unsafePerformSync
r4.runLog.unsafePerformSync
r5.runLog.unsafePerformSync

wye(Process.constant(1).take(2), Process.constant(0))(wye.mergeHaltBoth).runLog.unsafePerformSync