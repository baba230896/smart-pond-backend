package common

import play.api.libs.json.Json

case class LastFiveMinData(deviceId: Int, pH: Float, dO: Float, temp: Float)

object LastFiveMinData {
  implicit val writesLastFiveMinData = Json.writes[LastFiveMinData]
  implicit val readsLastFiveMinData = Json.reads[LastFiveMinData]
  implicit val formatLastFiveMinData = Json.format[LastFiveMinData]
}
