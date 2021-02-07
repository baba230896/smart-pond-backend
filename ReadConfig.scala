package common

import javax.inject.{Inject, Singleton}
import play.api.{Configuration, Logger}
import java.lang.reflect.Field

@Singleton
class ReadConfig @Inject() (restConfig: Configuration) {

  val requiredVars = List("API_KEY")
  val smartPondConfig = restConfig.underlying.getConfig("SmartPond.restConfig")
  val logger: Logger = Logger(this.getClass)
  var API_KEY: String = null

  def checkRequired(reqVarsList:List[String]) = {
    logger.info(s"Parsing of required config parameters")
    //Errors out if value doesn't exist
    for(param <- reqVarsList) {
      if(smartPondConfig.hasPath(param) && !smartPondConfig.getString(param).isEmpty) {
        val arg : Field = this.getClass.getDeclaredField(param)
        arg.setAccessible(true)
        arg.set(this,smartPondConfig.getString(param))
        logger.info(s"Found ${param} in config set to ${smartPondConfig.getString(param)}.")
      }
      else {
        logger.error(s"${param} is required in SmartPond Rest Config")
        System.exit(1)
      }
    }
  }

  checkRequired(requiredVars)
}
