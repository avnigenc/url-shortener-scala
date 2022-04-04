package db

import com.redis._

class RedisRepository {
  val redisClient = new RedisClient("localhost", 6379)

  def get(key: String): Option[String] = {
    redisClient.get(key)
  }

  def set(key: String, value: String): Unit = {
    redisClient.set(key, value)
  }
}
