package com.example

import com.example.swagger.TodoSwaggerDocument
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

case class Pong(message: String)

class PingController extends Controller with SwaggerSupport {

  implicit protected val swagger = TodoSwaggerDocument

  get("/ping", swagger { o =>
    o.summary("Get response for ping")
      .tag("Ping")
      .responseWith[Pong](200, "The pong message")
  }) { request: Request =>
  	info("ping")
    Pong("pong")
  }
}
