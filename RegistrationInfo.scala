package common

import play.api.libs.json.Json

case class RegistrationInfo(deviceId: Int, mobileNo: String,
                             lowrThresPh: BigDecimal, upprThresPh: BigDecimal,  lowrThresDo: BigDecimal,upprThresDo: BigDecimal,  lowrthresTemp: BigDecimal,  upprThresTemp: BigDecimal )

object RegistrationInfo{
  implicit val writesRegisterationInfo = Json.writes[RegistrationInfo]
  implicit val readsRegisterationInfo  = Json.reads[RegistrationInfo]
  implicit val formatRegisterationInfo = Json.format[RegistrationInfo]
}
