package com.example.controllers

import com.example.domain.http.PingResponse
import com.example.swagger.TodoSwaggerDocument
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller



class PingController extends Controller with SwaggerSupport {

  implicit protected val swagger = TodoSwaggerDocument

  get("/ping", swagger {
    _.summary("Get response for ping")
      .tag("Ping")
      .responseWith[PingResponse](200, "The pong message")
  }) { request: Request =>
  	info("ping")
    PingResponse("pong")
  }
}
