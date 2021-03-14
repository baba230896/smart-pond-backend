package common
import javax.inject.{Inject, Singleton}
import java.sql.Timestamp

import scala.collection.mutable
import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future, Promise}

class CustomerInfoRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                (implicit ec: ExecutionContext) {

  val logger: Logger = Logger(this.getClass)
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  try {
    Class.forName("org.postgresql.Driver")
  } catch {
    case e: Throwable => logger.info(s"$e")
  }

  private class CustInfo(tag: Tag) extends Table[CustomerInfo](tag, "customerinfo"){

    def userId = column[String]("userid")
    def password = column[String]("password")

    def * = (userId, password) <> ((CustomerInfo.apply _).tupled, CustomerInfo.unapply)
  }

  private val customerInfo = TableQuery[CustInfo]

  def create(userId: String): Future[Int] = db.run {
    customerInfo.map(c => (c.userId, c.password)) += (userId, userId)
  }

  def checkUserExists(userId: String): Future[Seq[CustomerInfo]] = db.run {
    customerInfo.filter(_.userId === userId).result
  }
}
