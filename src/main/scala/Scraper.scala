import java.net.UnknownHostException

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.model.Element
import org.jsoup.HttpStatusException

import scala.collection.mutable.ArrayBuffer

object Scraper {
  val timeOfDownloads = ArrayBuffer[Long]()

  def download(mode: String, numValue: Int): Iterable[Entry] = {
    val browser = JsoupBrowser()
    var currentPage = 1
    var errorMessage = "" // empty when no exception
    var idElements = Iterable[String]()
    var pointsElements = Iterable[String]()
    var contentElements = Iterable[Element]()
    var entries = Iterable[Entry]()

    try {
      do {
        val timeBefore = System.currentTimeMillis()
        val site = browser.get(getUrl(mode, numValue, currentPage))
        val timeAfter = System.currentTimeMillis()
        timeOfDownloads.addOne(timeAfter - timeBefore)
        idElements ++= (site >> texts(".qid"))
        pointsElements ++= (site >> texts(".points"))
        contentElements ++= (site >> elementList(".quote"))
        currentPage += 1
      } while (mode.equals("latest") && currentPage <= numValue && errorMessage.length() == 0)
    } catch {
    case httpStatus: HttpStatusException => {
      if (httpStatus.getStatusCode() == 404) {
        errorMessage = "Brak wpisu lub zbyt wiele podanych stron"
      } else {
        errorMessage = "Niepoprawny status strony: " + httpStatus.getStatusCode()
      }
    }
    case unknownHostException: UnknownHostException => { errorMessage = "Brak polaczenia z bash.org.pl" }
    case _ => { errorMessage = "Nieznany blad" }
  }

    if (errorMessage.length == 0) {
      entries = (idElements, pointsElements, contentElements).zipped.map { (id, points, content)
      =>
        new Entry(id.substring(1).toLong, points.toLong, content.innerHtml)
      }
    } else {
      entries = List(Entry(0, 0, errorMessage))
    }

    entries
  }

  def getUrl(mode: String, numValue: Int, currentPage: Int): String = {
    var url = "http://bash.org.pl/"
    mode match {
      case "latest" => url += ("latest/?page=" + currentPage)
      case "entry" => url += numValue
    }
    url
  }

  def getAverageTime(): Long = {
    if(timeOfDownloads.size != 0) {
      timeOfDownloads.sum / timeOfDownloads.size
    } else {
      return 0
    }
  }
}