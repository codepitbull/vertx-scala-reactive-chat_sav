package de.codepitbull.vertx.scala.chat

import java.util.Calendar

import io.vertx.scala.core.eventbus.Message
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.lang.scala.json.{Json, JsonObject}
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.StaticHandler
import io.vertx.scala.ext.web.handler.sockjs.{BridgeOptions, PermittedOptions, SockJSHandler}

import scala.concurrent.Future

class HttpVerticle extends ScalaVerticle {


  override def startFuture(): Future[_] = {
    val port = 8084
    val sender = vertx.eventBus().publisher[JsonObject]("browser")
    val consumer = vertx.eventBus().consumer[String]("server").handler(msg => sender.write(Json.obj(("msg",msg.body()), ("date", Calendar.getInstance().getTime().toString))))

    val router = Router.router(vertx)
    router.route("/eventbus/*").handler(initSockJs)
    router.route("/*").handler(StaticHandler.create())
    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(port)
  }

  def initSockJs = {
    val sockJSHandler = SockJSHandler.create(vertx)
    val options = BridgeOptions()
      .addOutboundPermitted(PermittedOptions().setAddress("browser"))
      .addInboundPermitted(PermittedOptions().setAddress("server"))
    sockJSHandler.bridge(options)
    sockJSHandler
  }
}
