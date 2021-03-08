package common
import javax.inject.{Inject, Singleton}
import java.util.UUID

import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future, Promise}

class LastFiveMinDataRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext)  {
  val logger: Logger = Logger(this.getClass)
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  try {
    Class.forName("org.postgresql.Driver")
  } catch {
    case e: Throwable => logger.info(s"$e")
  }

  private class LastFiveMin(tag: Tag) extends Table[LastFiveMinData](tag, "devicedata") {

    def deviceId = column[Int]("deviceid")
    def pH = column[Float]("ph")
    def dO = column[Float]("doxy")
    def temp = column[Float]("temp")

    def * = (deviceId, pH, dO, temp) <> ((LastFiveMinData.apply _).tupled, LastFiveMinData.unapply)

  }
  private val lastFiveMin = TableQuery[LastFiveMin]

  def create(deviceId: Int, pH: Float, dO: Float, temp: Float): Future[Int]  = db.run {
    lastFiveMin.map(l => (l.deviceId, l.pH, l.dO, l.temp )) += (deviceId, pH, dO, temp)
  }

}
