package com.example.feature

import com.example.TodoServer
import com.example.domain.User
import com.example.services.TokenService
import com.google.inject.testing.fieldbinder.Bind
import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future

class TokenFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedHttpServer(new TodoServer)

  @Bind val tokenService = smartMock[TokenService]

  "get /ping with correct token header" should {
    "respond to ping with 200 and message" in {

      tokenService.userForToken("my-mock-token") returns
        Future.value(Option(User("divan")))

      server.httpGet(
        path = "/ping",
        headers = Map("Authorization" -> "my-mock-token"),
        andExpect = Ok,
        withJsonBody =
          """
            {"message": "pong divan"}
          """)
    }
  }

  "get /ping with no Authorization header" should {
    "respond to ping with 401" in {

      tokenService.userForToken(any[String]) returns(Future value None)
      server.httpGet(
        path = "/ping",
        andExpect = Unauthorized
      )
    }
  }

  "get /ping with empty Authorization header" should {
    "respond to ping with 401" in {
      tokenService.userForToken(any[String]) returns(Future value None)
      server.httpGet(
        path = "/ping",
        headers = Map("Authorization" -> ""),
        andExpect = Unauthorized
      )
    }
  }
}
