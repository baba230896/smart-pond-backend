package common

import play.api.Logger

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object CommonFunctions {

  val listOfParamForRegister: List[String] = List("apiKey","deviceId", "mobileNo", "loThrsPh", "upThrsPh",
    "loThrsDo", "upThrsDo", "loThrsTemp", "upThrsTemp")

  val listOfParamForLastFiveMinData = List("apiKey", "deviceId", "pH", "dO", "temp")

  val listOfParamForDeviceData = List("apiKey", "deviceId")

  val logger:Logger = Logger(this.getClass)
  def convertRawStringToMap(qury: String):mutable.Map[String, String] = {
    var query = qury
    if(query.endsWith("/"))
      query = query.dropRight(1)
    val maps = mutable.Map[String, String] ()
    val temp = query.split("&").toList
    for(s <- temp) {
      val t = s.split("=")
      maps += (t(0) -> t(1))
    }
    maps
  }

  def checkParameters(map: mutable.Map[String, String], requiredParams: List[String]): String = {
    val missParam: ListBuffer[String] = ListBuffer[String]()
    for(r <- requiredParams) {
      if(!map.contains(r))
        missParam += r
    }
    missParam.mkString(", ")
  }
}
