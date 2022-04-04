package models

case class UrlShortenerItem(realUrl: String, shortUrl: String)

case class NewUrlShortenerItem(realUrl: String)

case class InvalidUrlResponse(url: String,
                              message: String = "URL is not allowed or invalid")
