package de.codepitbull.vertx.scala.chat

import io.vertx.lang.scala.ScalaVerticle.nameForVerticle
import io.vertx.scala.core.Vertx

/**
  * @author <a href="mailto:jochen.mader@codecentric.de">Jochen Mader</a>
  */
object HttpVerticleLauncher {
  def main(args: Array[String]): Unit = {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(nameForVerticle[HttpVerticle])
  }
}
