package com.example.feature

import com.example.TodoServer
import com.example.domain.{User, UserContext}
import com.example.domain.http.{LoginRequest, LoginResponse}
import com.example.services.{TokenService, UserService}
import com.google.inject.testing.fieldbinder.Bind
import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future

class RegisterFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedHttpServer(new TodoServer)

  @Bind val userService = smartMock[UserService]

  "/register" should {
    "return success message" in {
      
    }
  }
}
