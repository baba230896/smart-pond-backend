package common

import play.api.libs.json.{Json, OFormat, OWrites, Reads}

case class LastFiveMinData(deviceId: Int, pH: Float, dO: Float, temp: Float)

object LastFiveMinData {
  /**implicit val writesLastFiveMinData: OWrites[LastFiveMinData] = Json.writes[LastFiveMinData]
  implicit val readsLastFiveMinData: Reads[LastFiveMinData] = Json.reads[LastFiveMinData]*/
  implicit val formatLastFiveMinData: OFormat[LastFiveMinData] = Json.format[LastFiveMinData]
}
