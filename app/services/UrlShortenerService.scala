package services

import db.RedisRepository
import models.NewUrlShortenerItem
import utils.Utils.{createShortUrl, validateUrl}

import javax.inject.Inject

class UrlShortenerService @Inject()(redisRepository: RedisRepository) {

  def create(newItem: NewUrlShortenerItem): String = {
    val isValid = validateUrl(newItem.realUrl)

    if (!isValid) return s"${newItem.realUrl} is not valid URL"

    val shortUrl = createShortUrl()
    redisRepository.set(shortUrl, newItem.realUrl)
    shortUrl
  }

  def redirect(shortUrl: String): String = {
    val record = redisRepository.get(shortUrl)

    record match {
      case Some(item) => item
      case None       => ""
    }
  }
}
