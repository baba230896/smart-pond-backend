package controllers

import javax.inject._
import play.api.mvc._
import play.api.Logger
import play.api.inject.Injector

import scala.concurrent.{ExecutionContext, Promise}

import play.api.libs.json.{JsValue, Json}

import scala.util.{Failure, Success}

import common._
import common.CaseClasses._
import common.CommonFunctions._

@Singleton
class HomeController @Inject()(injector: Injector, readConfig: ReadConfig, cc: MessagesControllerComponents)(implicit ec:ExecutionContext)
        extends MessagesAbstractController(cc) {
  val logger:Logger = Logger(this.getClass)

  def index:Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("SmartPond project started")
  }

  def register:Action[AnyContent] = Action.async { implicit request =>
    val rtrn: Promise[Result] = Promise();
    logger.info(s"In register Endpoint")
    if(request.rawQueryString.isEmpty) {
      rtrn.success(BadRequest(s"Parameters missing!!!"))
    } else {
      val maps = convertRawStringToMap(request.rawQueryString)
      val missParam = checkParameters(maps, listOfParamForRegister)
      if(missParam.length != 0 ) {
        rtrn.success(Unauthorized(s"Parameter(s) missing = $missParam"))
      } else {
        if(!(maps.get("apiKey").head == readConfig.API_KEY)) {
          rtrn.success(Forbidden("Wrong ApiKey!!!"))
        } else {
          val regInfoRepo: RegInfoRepo = injector.instanceOf(classOf[RegInfoRepo])
          regInfoRepo.deviceRegInfo(maps.get("deviceId").head.toInt).onComplete({
            case Success(value) =>
              if(value.length == 0) {
                regInfoRepo.create(maps).onComplete({
                  case Success(value) => rtrn.success(Ok(s"OK!!!"))
                  case Failure(exception) => rtrn.failure(exception)
                })
              } else {
                regInfoRepo.update(maps).onComplete({
                  case Success(value) => rtrn.success(Ok(s"OK!!!"))
                  case Failure(exception) => rtrn.failure(exception)
                })
              }
              val customerInfoRepo = injector.instanceOf(classOf[CustomerInfoRepo])
              customerInfoRepo.checkUserExists(maps.get("mobileNo").head).onComplete({
                case Success(value) =>
                  if(value.length == 0)
                    customerInfoRepo.create(maps.get("mobileNo").head).onComplete({
                      case Success(value) => logger.info(s"$value")
                      case Failure(exception) => logger.info(s"$exception")
                    })
                case Failure(exception) => logger.info(s"$exception")
              })
            case Failure(exception) =>
              rtrn.failure(exception)
          })
        }
      }
    }
    rtrn.future
  }

  def lastFiveMinData:Action[AnyContent] = Action.async { implicit request =>
    val rtrn: Promise[Result] = Promise()
    logger.info(s"In lastFiveMinData Endpoint")
    if(request.rawQueryString.isEmpty) {
      rtrn.success(BadRequest(s"Parameters missing!!!"))
    } else {
      val maps = CommonFunctions.convertRawStringToMap(request.rawQueryString)
      val missParam = checkParameters(maps, listOfParamForLastFiveMinData)
      if(missParam.length != 0 ) {
        rtrn.success(Unauthorized(s"Parameter(s) missing = $missParam"))
      } else {
        if(!(maps.get("apiKey").head == readConfig.API_KEY)) {
          rtrn.success(Forbidden("Wrong ApiKey!!!"))
        } else {
          val regInfoRepo: RegInfoRepo = injector.instanceOf(classOf[RegInfoRepo])
          regInfoRepo.deviceRegInfo(maps.get("deviceId").head.toInt).onComplete({
            case Success(value) =>
              if(value.length != 0) {
                val lastFiveMinRepo: LastFiveMinDataRepo = injector.instanceOf(classOf[LastFiveMinDataRepo])
                lastFiveMinRepo.create(maps).onComplete({
                  case Success(value) => rtrn.success(Ok(s"OK!!!"))
                  case Failure(exception) => rtrn.failure(exception)
                })
              } else {
                rtrn.success(NotFound(s"DeviceId doesn't Exists!!!"))
              }
            case Failure(exception) =>
              rtrn.failure(exception)
          })
        }
      }
    }
    rtrn.future
  }

  def displayDeviceRegData: Action[AnyContent] = Action.async { implicit request =>
    val rtrn: Promise[Result] = Promise()
    logger.info(s"In the /displayRegData")
    if(request.rawQueryString.isEmpty) {
      rtrn.success(BadRequest(s"Parameters Missing!!!"))
    } else {
      val maps = CommonFunctions.convertRawStringToMap(request.rawQueryString)
      val missParam = checkParameters(maps, listOfParamForDeviceData)
      if(missParam.length != 0 ) {
        rtrn.success(Unauthorized(s"Parameter(s) missing = $missParam"))
      } else {
        if(!(maps.get("apiKey").head == readConfig.API_KEY)) {
          rtrn.success(Forbidden("Wrong ApiKey!!!"))
        } else {
          val regInfoRepo: RegInfoRepo = injector.instanceOf(classOf[RegInfoRepo])
          regInfoRepo.deviceRegInfo(maps.get("deviceId").head.toInt).onComplete({
            case Success(value) =>
              if(value.length != 0) {
                rtrn.success(Ok(Json.toJson(value.head)))
              } else {
                rtrn.success(NotFound(s"DeviceId doesn't Exists!!!"))
              }
            case Failure(exception) =>
              rtrn.failure(exception)
          })
        }
      }
    }
    rtrn.future
  }

  def deviceList:Action[AnyContent] = Action.async { implicit request =>
   logger.info(s"Into the /deviceList Endpoint")
    val rtrn : Promise[Result] = Promise()
    var apiKey: String = null
    if(request.headers.get("x-api-key").isDefined) {
      logger.info(s"API_KEY = ${request.headers.get("x-api-key").head}")
    } else {

    }
    rtrn.future
  }

  def last24HrDataPerUser: Action[JsValue] = Action(parse.json) { implicit request =>
    Ok("TODO")
  }

  def login: Action[JsValue] = Action(parse.json) { implicit request =>
    Ok("TODO")
  }
}
