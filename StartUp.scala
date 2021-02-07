package common

import com.google.inject.AbstractModule
import play.api.Logger

class StartUp extends AbstractModule {
  val logger: Logger = Logger(this.getClass())
  val className: String = this.getClass.getName()

  override def configure(): Unit = {
    logger.info(s"Start configuring SmartPond Rest Server")
    bind(classOf[ReadConfig]).asEagerSingleton()
  }
}
