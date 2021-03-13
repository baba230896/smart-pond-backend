package common

import play.api.libs.json.{Json, OFormat}

case class RegistrationInfo(deviceId: Int, mobileNo: String, loThrsPh: Float, upThrsPh: Float,
                            loThrsDo: Float,upThrsDo: Float,  loThrsTemp: Float,  upThrsTemp: Float)
case class LastFiveMinData(deviceId: Int, pH: Float, dO: Float, temp: Float)
case class CustomerInfo(deviceId: Int, password: String)

object CaseClasses {
  implicit val formatLastFiveMinData: OFormat[LastFiveMinData] = Json.format[LastFiveMinData]
  implicit val formatRegisterationInfo: OFormat[RegistrationInfo] = Json.format[RegistrationInfo]
  implicit val formatCustomerInfo: OFormat[CustomerInfo] = Json.format[CustomerInfo]
}


