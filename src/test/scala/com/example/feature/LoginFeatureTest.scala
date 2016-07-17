package com.example.feature

import com.example.TodoServer
import com.example.domain.http.{LoginRequest, LoginResponse}
import com.example.services.UserService
import com.google.inject.testing.fieldbinder.Bind
import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future

class LoginFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedHttpServer(new TodoServer)

  @Bind val userService = smartMock[UserService]

  val mockToken = "my-mock-modersky"

  "/login" should {
    s"return json object containing $mockToken token if details correct" in {

      userService.login(LoginRequest("modersky","0x(*B%20a&dFO0D)")) returns
        Future.value(Option(LoginResponse(mockToken)))

      server.httpPost(
        path = "/login",
        postBody =
          """
             {
                "username": "modersky",
                "password": "0x(*B%20a&dFO0D)"
             }
          """,
        andExpect = Ok,
        withJsonBody =
          s"""
            {
                "token" : "$mockToken"
            }
          """
      )
    }
  }

  "/login" should {
    "return 401 response if details are incorrect" in {

      userService.login(LoginRequest("wrong","")) returns
        Future.value(None)

      server.httpPost(
        path = "/login",
        postBody =
        """
          {
            "username" : "wrong",
            "password" : ""
          }
        """,
        andExpect = Unauthorized
      )
    }
  }
}
