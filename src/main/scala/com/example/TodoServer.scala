package com.example

import com.example.controllers.{PingController, TodoController, UserController}
import com.example.filters.TokenFilter
import com.example.services.TokenService
import com.example.swagger.TodoSwaggerDocument
import com.example.warmup.WarmupHandler
import com.github.xiaodongw.swagger.finatra.SwaggerController
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import io.swagger.models.Info

object TodoServerMain extends TodoServer

class TodoServer extends HttpServer {

  TodoSwaggerDocument.info(new Info()
    .description("Todo application API")
    .version("0.0.1")
    .title("Todo")
  )

  override def defaultFinatraHttpPort = ":9999"

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add(new SwaggerController(swagger = TodoSwaggerDocument))
      .add[TokenFilter, PingController]
      .add[TokenFilter, TodoController]
      .add[UserController]
  }

  override def warmup(): Unit = {
    run[WarmupHandler]()
  }

}
