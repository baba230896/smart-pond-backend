package common

import play.api.libs.json.{Json, OFormat, OWrites, Reads}

case class RegistrationInfo(deviceId: Int, mobileNo: String, loThrsPh: Float, upThrsPh: Float,
                            loThrsDo: Float,upThrsDo: Float,  loThrsTemp: Float,  upThrsTemp: Float, password: String)

object RegistrationInfo{
  /**implicit val writesRegisterationInfo: OWrites[RegistrationInfo] = Json.writes[RegistrationInfo]
  implicit val readsRegisterationInfo: Reads[RegistrationInfo] = Json.reads[RegistrationInfo]*/
  implicit val formatRegisterationInfo: OFormat[RegistrationInfo] = Json.format[RegistrationInfo]
}
