package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.Logger

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.{JsError, JsSuccess, Json, __}
import java.sql.Timestamp

import common._

@Singleton
class HomeController @Inject()(readConfig: ReadConfig, cc: MessagesControllerComponents)(implicit ec:ExecutionContext)
        extends MessagesAbstractController(cc) {
  val logger:Logger = Logger(this.getClass())

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("SmartPond project started")
  }

  def register() = Action(parse.json) { implicit request =>
    logger.info(s"In register Endpoint")
    /**if (request.body.asFormUrlEncoded != None) {
      logger.info(s"${request.body.asFormUrlEncoded.get}")
    } else {
      logger.info(s"Empty asFormUrlEncoded")
    }*/
    /**var apiKey: String = null
    if(request.headers.get("x-api-key")!= None) {
      apiKey = request.headers.get("x-api-key").get
      logger.info(s"API_KEY = ${apiKey}")
    }*/
    request.body.validate[RegistrationInfo] match {
      case JsSuccess(registrationInfo, _) => {
        if(registrationInfo.apiKey == readConfig.API_KEY) {
          logger.info(s"${registrationInfo}")
          Ok
        } else {
          Forbidden(s"${registrationInfo.apiKey}")
        }
      }
      case JsError(errors) =>
        logger.info(s"$errors")
        BadRequest(s"$errors")
    }
  }

  def lastFiveMinData() = Action(parse.json) { implicit request =>
    logger.info(s"In lastFiveMinData Endpoint")
    /**if (request.body.asFormUrlEncoded != None) {
      logger.info(s"${request.body.asFormUrlEncoded.get}")
    } else {
      logger.info(s"Empty asFormUrlEncoded")
    }*/
    /**var apiKey: String = null
    if(request.headers.get("x-api-key")!= None) {
      apiKey = request.headers.get("x-api-key").get
      logger.info(s"API_KEY = ${apiKey}")
    }*/
    request.body.validate[LastFiveMinData] match {
      case JsSuccess(lastFiveMinData, _) => {
        if(lastFiveMinData.apiKey == readConfig.API_KEY) {
          logger.info(s"${lastFiveMinData}")
          Ok
        } else {
          Forbidden(s"${lastFiveMinData.apiKey}")
        }
      }
      case JsError(errors) =>
        logger.info(s"$errors")
        BadRequest(s"$errors")
    }
  }
}
