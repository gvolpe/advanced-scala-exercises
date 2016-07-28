import java.io.{BufferedOutputStream, FileOutputStream}

import scala.io.BufferedSource
import scalaz.concurrent.Task
import scalaz.stream._

val dir = "/home/gvolpe/development/workspace/advanced-scala-exercises/src/main/resources"
val filename1 = s"$dir/names.txt"
val filename2 = s"$dir/lines.txt"

def readLines(filename: String): Process[Task, String] = {
  val open: Task[BufferedSource] = Task.delay(scala.io.Source.fromFile(filename))
  val close = (s: BufferedSource) => Process.eval(Task.delay(s.close())).drain
  val read = { (s: BufferedSource) =>
    val it = s.getLines()

    def loop: Process[Task, String] = {
      if (it.hasNext) Process.emit { println("Reading!"); it.next() } ++ loop
      else Process.halt
    }
    loop
  }
  Process.bracket(open)(close)(read)
}

println(readLines(filename1).take(3).runLog.unsafePerformSync)

def writeLines(filename: String, line: String): Process[Task, Unit] = {
  val open = Task.delay(new BufferedOutputStream(new FileOutputStream(filename)))
  val close = (s: BufferedOutputStream) => Process.eval_(Task.delay(s.close()))
  val write = (s: BufferedOutputStream) => Process.eval(Task.delay(s.write(line.getBytes("UTF-8"))))
  Process.bracket(open)(close)(write)
}

writeLines(filename2, "Hello World!").runLog.unsafePerformSync

println(readLines(filename2).runLog.unsafePerformSync)