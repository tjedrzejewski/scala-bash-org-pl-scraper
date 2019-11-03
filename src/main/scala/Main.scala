import com.typesafe.config.ConfigFactory

import scala.util.Try

object Main {
  def main(args: Array[String]): Unit = {
    if(args.isEmpty || args.length > 2){
      throw new Exception("Nie poprawna ilosc argumentow. Instrukcja obslugi w pliku readme.md")
    }
    if(!(args(0).equals("entry") || args(0).equals("latest"))){
      throw new Exception("Niepoprawy pierwszy parametr, dopuszczalne słowa kluczowe to entry lub latest")
    }
    if(!(Try(args(1).toInt).isSuccess)){
      throw new Exception("Przyjmowane sa tylko liczby calkowite")
    }
    val config = ConfigFactory.load("defaults.conf")
    val filename = config.getString("conf.filepath")
    val mode = args(0)
    val numValue = args(1).toInt
    val entries = Scraper.download(mode, numValue)
    FileSaver.writeFile(filename, entries)
    print("Liczba pozyskanych wpisow: " + entries.size + sys.props("line.separator"))
    mode match {
      case "latest" => print("Sredni czas pozyskiwania jednej strony: " + Scraper.getAverageTime() + " ms"
        + sys.props("line.separator"))
      case "entry"  => print("Sredni czas pozyskiwania jednego wpisu: " + Scraper.getAverageTime() + " ms"
        + sys.props("line.separator"))
    }
  }
}
