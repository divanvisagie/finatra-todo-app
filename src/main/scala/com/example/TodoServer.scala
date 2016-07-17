package com.example

import com.example.controllers.{PingController, UserController}
import com.example.filters.TokenFilter
import com.example.swagger.TodoSwaggerDocument
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import io.swagger.models.Info
import com.github.xiaodongw.swagger.finatra.SwaggerController
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future
import com.twitter.finagle.http.Status._

object TodoServerMain extends TodoServer



class TodoServer extends HttpServer {

  TodoSwaggerDocument.info(new Info()
    .description("Todo application API")
    .version("0.0.1")
    .title("Todo")
  )


  override def defaultFinatraHttpPort = ":9999"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add(new SwaggerController(swagger = TodoSwaggerDocument))
      .add[TokenFilter, PingController]
      .add[UserController]
  }

}
