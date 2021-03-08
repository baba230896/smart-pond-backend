package common

import javax.inject.{Inject, Singleton}
import java.util.UUID

import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future, Promise}

@Singleton
class RegInfoRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit ec: ExecutionContext) {

  val logger: Logger = Logger(this.getClass)
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  private class RegInfo(tag: Tag) extends Table[RegistrationInfo](tag, "register") {

    def deviceId = column[Int]("deviceid")
    def mobileNo = column[String]("mobileno")
    def loThrsPh = column[Float]("lothrsph")
    def upThrsPh = column[Float]("upthrsph")
    def loThrsDo = column[Float]("lothrsdo")
    def upThrsDo = column[Float]("upthrsdo")
    def loThrsTemp = column[Float]("lothrstemp")
    def upThrsTemp = column[Float]("upthrstemp")
    def pass = column[String]("password")

    def * = (deviceId, mobileNo, loThrsPh, upThrsPh, loThrsDo, upThrsDo, loThrsTemp, upThrsTemp, pass) <> ((RegistrationInfo.apply _).tupled, RegistrationInfo.unapply)
  }
  private val regInfo = TableQuery[RegInfo]

  try {
    Class.forName("org.postgresql.Driver")
  } catch {
    case e: Throwable => logger.info(s"$e")
  }

  def create(deviceId:Int, mobileNo: String, loThrsPh: Float, upThrsPh: Float, loThrsDo: Float, upThrsDo: Float, loThrsTemp: Float, upThrsTemp: Float, pass: String): Future[Int]  = db.run {
    regInfo.map(r => (r.deviceId, r.mobileNo, r.loThrsPh, r.upThrsPh, r.loThrsDo, r.upThrsDo, r.loThrsTemp, r.upThrsTemp, r.pass )) +=
      (deviceId, mobileNo, loThrsPh, upThrsPh, loThrsDo, upThrsDo, loThrsTemp, upThrsTemp, pass)
  }

  def list(): Future[Seq[RegistrationInfo]] = db.run {
    regInfo.result
  }

  def deviceRegInfo(deviceId: Int) : Future[Seq[RegistrationInfo]] = db.run {
    regInfo.filter(_.deviceId === deviceId).result
  }

}
