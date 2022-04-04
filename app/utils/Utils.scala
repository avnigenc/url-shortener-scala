package utils

import scala.util.Random
import scala.util.matching.Regex

object Utils {
  def validateUrl(url: String): Boolean = {
    val pattern = new Regex(
      "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)"
    )
    val isValidURL = pattern findFirstIn url

    isValidURL.isDefined
  }

  def createShortUrl(): String = {
    Random.alphanumeric.filter(_.isLetter).take(6).mkString
  }
}
