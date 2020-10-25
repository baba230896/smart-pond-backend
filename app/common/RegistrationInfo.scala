package common

import play.api.libs.json.Json

case class RegistrationInfo(deviceId: Int, mobileNo: String,
                            threshold_pH: Float, threshold_dO: Float,threshold_temp: Float)

object RegistrationInfo{
  implicit val writesRegisterationInfo = Json.writes[RegistrationInfo]
  implicit val readsRegisterationInfo  = Json.reads[RegistrationInfo]
  implicit val formatRegisterationInfo = Json.format[RegistrationInfo]
}
