package controllers

import models.{NewUrlShortenerItem, UrlShortenerItem}
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.UrlShortenerService

import javax.inject._

@Singleton
class UrlShortenerController @Inject()(
  val controllerComponents: ControllerComponents,
  val urlShortenerService: UrlShortenerService
) extends BaseController {
  implicit val urlListJson: OFormat[UrlShortenerItem] =
    Json.format[UrlShortenerItem]

  implicit val newUrlListJson: OFormat[NewUrlShortenerItem] =
    Json.format[NewUrlShortenerItem]

  def createCtrl(): Action[AnyContent] = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson

    val urlListItem =
      jsonObject.flatMap(Json.fromJson[NewUrlShortenerItem](_).asOpt)

    urlListItem match {
      case Some(newItem) =>
        val shortUrl = urlShortenerService.create(newItem)
        val urlShortenerItem = UrlShortenerItem(newItem.realUrl, shortUrl)

        Ok(Json.toJson(urlShortenerItem))
      case None =>
        BadRequest(Json.toJson(s"URL is not allowed"))
    }
  }

  def redirectCtrl(shortUrl: String): Action[AnyContent] = Action {
    val record = urlShortenerService.redirect(shortUrl)

    record match {
      case "" =>
        BadRequest(
          Json.toJson(s"Short URL is not allowed or not created or expired")
        )
      case _ => Redirect(record)
    }
  }
}
