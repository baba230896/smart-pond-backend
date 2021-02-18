package common

import play.api.libs.json.Json

case class RegistrationInfo(apiKey:String, deviceId: Int, mobileNo: String, loThrsPh: Float, upThrsPh: Float,
                            loThrsDo: Float,upThrsDo: Float,  loThrsTemp: Float,  upThrsTemp: Float)

object RegistrationInfo{
  implicit val writesRegisterationInfo = Json.writes[RegistrationInfo]
  implicit val readsRegisterationInfo  = Json.reads[RegistrationInfo]
  implicit val formatRegisterationInfo = Json.format[RegistrationInfo]
}
