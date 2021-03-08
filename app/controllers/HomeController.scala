package controllers

import javax.inject._
import play.api.mvc._
import play.api.Logger
import play.api.inject.Injector

import scala.concurrent.{ExecutionContext, Promise}
import common._
import play.api.libs.json.{JsValue, Json}

import scala.collection.mutable
import scala.util.{Failure, Success}

@Singleton
class HomeController @Inject()(injector: Injector, readConfig: ReadConfig, cc: MessagesControllerComponents)(implicit ec:ExecutionContext)
        extends MessagesAbstractController(cc) {
  val logger:Logger = Logger(this.getClass)

  def index:Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok("SmartPond project started")
  }

  def register: Action[AnyContent] = Action.async { implicit request =>
    val rtrn : Promise[Result] = Promise()
    logger.info(s"In register Endpoint")
    if(!request.rawQueryString.isEmpty) {
      val str = request.rawQueryString.split("&").toList
      logger.info(s"$str")
      val maps = mutable.Map[String, String]()
      for(s <- str) {
        val temp = s.split("=")
        maps += (temp(0) -> temp(1))
      }
      logger.info(s"$maps")
      if(maps.contains("apiKey")) {
        if(maps.get("apiKey").head == readConfig.API_KEY) {
          val regInfoRepo: RegInfoRepo = injector.instanceOf(classOf[RegInfoRepo])
          regInfoRepo.create(maps.get("deviceId").head.toInt, maps.get("mobileNo").head, maps.get("loThrsPh").head.toFloat, maps.get("upThrsPh").head.toFloat,
            maps.get("loThrsDo").head.toFloat, maps.get("upThrsDo").head.toFloat, maps.get("loThrsTemp").head.toFloat,
            maps.get("upThrsTemp").head.toFloat, maps.get("mobileNo").head).onComplete({
            case Success(value) => rtrn.success(Status(200)(s"OK"))
            case Failure(exception) => rtrn.failure(exception)
          })
        } else {
          rtrn.success(Status(401)(s"Pass correct apiKey"))
        }
      } else {
        rtrn.success(Status(403)(s"Pass apiKey parameter"))
      }
    } else {
      rtrn.success(Status(400)(s"Give appropriate parameters"))
    }
    rtrn.future
  }

  def lastFiveMinData:Action[AnyContent] = Action.async { implicit request =>
    val rtrn: Promise[Result] = Promise()
    logger.info(s"In lastFiveMinData Endpoint")
    if(!request.rawQueryString.isEmpty) {
      val str = request.rawQueryString.split("&").toList
      logger.info(s"$str")
      val maps = mutable.Map[String, String]()
      for(s <- str) {
        val temp = s.split("=")
        maps += (temp(0) -> temp(1))
      }
      logger.info(s"$maps")
      if(maps.contains("apiKey")) {
        if(maps.get("apiKey").head == readConfig.API_KEY) {
          val lastFiveMinRepo: LastFiveMinDataRepo = injector.instanceOf(classOf[LastFiveMinDataRepo])
          lastFiveMinRepo.create(maps.get("deviceId").head.toInt,
            maps.get("pH").head.toFloat, maps.get("dO").head.toFloat, maps.get("temp").head.toFloat).onComplete({
            case Success(value) => rtrn.success(Status(200)(s"OK"))
            case Failure(exception) => rtrn.failure(exception)
          })
        } else {
          rtrn.success(Status(401)(s"Pass correct apiKey"))
        }
      } else {
        rtrn.success(Status(403)(s"Pass apiKey parameter"))
      }
    } else {
      rtrn.success(Status(400)(s"Give appropriate parameters"))
    }
    rtrn.future
  }

  def displayDeviceRegData: Action[AnyContent] = Action.async { implicit request =>
    val rtrn: Promise[Result] = Promise()
    logger.info(s"In the /displayRegData")
    if(!request.rawQueryString.isEmpty) {
      val str = request.rawQueryString.split("&").toList
      logger.info(s"$str")
      val maps = mutable.Map[String, String]()
      for(s <- str) {
        val temp = s.split("=")
        maps += (temp(0) -> temp(1))
      }
      logger.info(s"$maps")
      if(maps.contains("apiKey")) {
        if(maps.get("apiKey").head == readConfig.API_KEY) {
          val regInfo: RegInfoRepo = injector.instanceOf(classOf[RegInfoRepo])
          regInfo.deviceRegInfo(maps.get("deviceId").head.toInt).onComplete({
            case Success(value) =>
              if(value.length != 0) {
                logger.info(s"$value")
                val op = Json.toJson(value.head)
                rtrn.success(Status(200)(op))
              } else {
                rtrn.success(Status(400)(s"DeviceId doesn't exists!!!"))
              }

            case Failure(exception) =>
              rtrn.success(Status(400)(s"$exception"))
          })
        } else {
          rtrn.success(Status(401)(s"Pass correct apiKey"))
        }
      } else {
        rtrn.success(Status(403)(s"Pass apiKey parameter"))
      }
    } else {
      rtrn.success(Status(400)(s"Give appropriate parameters"))
    }
    rtrn.future
  }
  def deviceList: Action[JsValue] = Action(parse.json) { implicit request =>
    Ok("TODO")
  }
  def last24HrDataPerUser: Action[JsValue] = Action(parse.json) { implicit request =>
    Ok("TODO")
  }
  def login: Action[JsValue] = Action(parse.json) { implicit request =>
    Ok("TODO")
  }
}
