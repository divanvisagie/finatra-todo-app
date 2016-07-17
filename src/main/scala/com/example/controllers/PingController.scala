package com.example.controllers

import com.example.domain.http.PingResponse
import com.example.swagger.TodoSwaggerDocument
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.example.filters.UserContext._

class PingController extends Controller with SwaggerSupport {

  implicit protected val swagger = TodoSwaggerDocument

  get("/ping", swagger {
    _.summary("Get response for ping")
      .tag("Ping")
      .headerParam[String]("Authorization","The authorization token",required = true)
      .responseWith[PingResponse](200, "The pong message")
  }) { request: Request =>
  	info("ping")

    PingResponse(s"pong ${request.user.username}")
  }
}
