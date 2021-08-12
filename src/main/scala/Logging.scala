package org.example

import org.apache.log4j.{LogManager, Logger, PropertyConfigurator}

trait Logging {

  PropertyConfigurator.configure(getClass.getResourceAsStream("/conf/log4j.properties"))

  val logger: Logger = LogManager.getLogger(getClass)

}
