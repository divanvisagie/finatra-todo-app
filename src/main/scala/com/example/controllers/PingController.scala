package com.example.controllers

import javax.inject._

import com.example.domain.{User, UserContext}
import com.example.domain.http.PingResponse
import com.example.swagger.TodoSwaggerDocument
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class PingController @Inject()(userContext: Provider[UserContext]) extends Controller with SwaggerSupport {

  implicit protected val swagger = TodoSwaggerDocument

  get("/ping", swagger {
    _.summary("Get response for ping")
      .tag("Ping")
      .headerParam[String]("Authorization","The authorization token",required = true)
      .responseWith[PingResponse](200, "The pong message")
  }) { request: Request =>
  	info("ping")
    val user = userContext.get.user
    PingResponse(s"pong ${user.username}")
  }
}
