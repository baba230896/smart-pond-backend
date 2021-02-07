package controllers

import common._
import javax.inject._
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class HomeController @Inject()(readConfig: ReadConfig, cc: MessagesControllerComponents)(implicit ec:ExecutionContext)
        extends MessagesAbstractController(cc) {
  val logger:Logger = Logger(this.getClass)

  /*val dataForm: Form[RegistrationInfo] = Form {
    mapping (
      "deviceId"      -> number,
      "mobileNo"      -> text,
      "lowrThresPh"   -> bigDecimal,
      "upprThresPh"   -> bigDecimal,
      "lowrThresDo"   -> bigDecimal,
      "upprThresDo"   -> bigDecimal,
      "lowrThresTemp" -> bigDecimal,
      "upprThresTemp" -> bigDecimal
    )(RegistrationInfo.apply)(RegistrationInfo.unapply)
  }*/

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("SmartPond project started")
  }

  def register(): Action[JsValue] = Action(parse.json) { implicit request =>
    logger.info(s"In register Endpoint")
    /**if (request.body.asFormUrlEncoded != None) {
      logger.info(s"${request.body.asFormUrlEncoded.get}")
    } else {
      logger.info(s"Empty asFormUrlEncoded")
    }*/
    var apiKey: String = null
    if(request.headers.get("x-api-key").isDefined) {
      apiKey = request.headers.get("x-api-key").get
      logger.info(s"API_KEY = $apiKey")
    }
    request.body.validate[RegistrationInfo] match {
      case JsSuccess(registrationInfo, _) =>
        if(apiKey == readConfig.API_KEY) {
          logger.info(s"$registrationInfo")
          Ok
        } else {
          Forbidden
        }
      case JsError(errors) =>
        logger.info(s"$errors")
        BadRequest
    }
  }

  def register_get():Action[AnyContent] = Action { implicit request =>
    logger.info(s"In register Endpoint")
    /**if (request.body.asFormUrlEncoded != None) {
      logger.info(s"${request.body.asFormUrlEncoded.get}")
    } else {
      logger.info(s"Empty asFormUrlEncoded")
    }*/
    var apiKey: String = null
    if(request.headers.get("x-api-key").isDefined) {
      apiKey = request.headers.get("x-api-key").get
      logger.info(s"API_KEY = $apiKey")
    }
    /**dataForm.bindFromRequest.fold(
      errorForm => {
        logger.error(errorForm.toString)
        BadRequest(errorForm.toString)
      },
      result => {
        logger.info(s"CaseNumber = ${result}")
        Ok
      }
    )*/
    if(request.body.asText != None) {
      logger.info(s"${request.body.asText.get}")
      Ok
    } else {
      BadRequest
    }

  }

  def lastFiveMinData(): Action[JsValue] = Action(parse.json) { implicit request =>
    logger.info(s"In lastFiveMinData Endpoint")
    /**if (request.body.asFormUrlEncoded != None) {
      logger.info(s"${request.body.asFormUrlEncoded.get}")
    } else {
      logger.info(s"Empty asFormUrlEncoded")
    }*/
    var apiKey: String = null
    if(request.headers.get("x-api-key").isDefined) {
      apiKey = request.headers.get("x-api-key").get
      logger.info(s"API_KEY = $apiKey")
    }
    request.body.validate[LastFiveMinData] match {
      case JsSuccess(lastFiveMinData, _) =>
        if(apiKey == readConfig.API_KEY) {
          logger.info(s"$lastFiveMinData")
          Ok
        } else {
          Forbidden
        }
      case JsError(errors) =>
        logger.info(s"$errors")
        BadRequest
    }

  }

}
