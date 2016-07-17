package com.example.feature

import com.example.TodoServer
import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class TokenFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new TodoServer)

  "get /ping with correct token header" should {
    "respond to ping with 200 and message" in {
      server.httpGet(
        path = "/ping",
        headers = Map("Authorization" -> "my-mock-modersky"),
        andExpect = Ok,
        withJsonBody =
          """
            {"message": "pong modersky"}
          """)
    }
  }

  "get /ping with no Authorization header" should {
    "respond to ping with 401" in {
      server.httpGet(
        path = "/ping",
        andExpect = Unauthorized
      )
    }
  }

  "get /ping with empty Authorization header" should {
    "respond to ping with 401" in {
      server.httpGet(
        path = "/ping",
        headers = Map("Authorization" -> ""),
        andExpect = Unauthorized
      )
    }
  }
}
