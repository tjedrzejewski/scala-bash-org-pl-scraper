import java.io.{BufferedWriter, File, FileWriter}
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.writePretty

object FileSaver {
  def writeFile(filename: String, lines : Iterable[Entry]): Unit = {
    val file = new File(filename)
    val writer = new BufferedWriter(new FileWriter(file))
    implicit val formats = DefaultFormats
    for (line <- lines) {
      writer.write(writePretty((line)))
      writer.newLine()
    }
    writer.close()
  }
}
