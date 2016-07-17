package com.example

import com.twitter.finagle.http.Status.Ok
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class PingFeatureTest extends FeatureTest {

  override val server = new EmbeddedHttpServer(new TodoServer)

  "Server" should {
    "ping" in {
      server.httpGet(
        path = "/ping",
        andExpect = Ok,
        withJsonBody =
          """
            {"message": "pong"}
          """)
    }
  }
}
