package com.example.feature

import com.example.TodoServer
import com.example.domain.{Todo, User}
import com.example.services.{TodoService, TokenService}
import com.google.inject.testing.fieldbinder.Bind
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.finagle.http.Status._
import com.twitter.inject.server.FeatureTest
import com.twitter.inject.Mockito
import com.twitter.util.Future

class TodoFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedHttpServer(new TodoServer)

  @Bind val tokenService = smartMock[TokenService]
  @Bind val todoService = smartMock[TodoService]

  "get /todo" should {
    "return the json list of the users todos" in {
      tokenService.userForToken(any[String]) returns
        Future.value(Option(User("""divan""")))
      todoService.list(any[User]) returns
        Future.value(Seq[Todo](
          Todo("Clean the cat"),
          Todo("Set fire to the rain")
        ))

      server.httpGet(
        path = "/todo",
        headers = Map("Authorization" -> "my-mock-token"),
        andExpect = Ok,
        withJsonBody =
          """
           [{
             "text": "Clean the cat"
           },{
             "text": "Set fire to the rain"
           }]
          """
      )
    }
  }

  "post /todo" should {
    "confirm creation of todo" in {
      tokenService.userForToken(any[String]) returns
        Future.value(Option(User("""divan""")))
      server.httpPost(
        path = "/todo",
        headers = Map("Authorization" -> "my-mock-token"),
        postBody =
          """
            {
              "text" : "Todo Test"
            }
          """,
        withJsonBody =
          """
            {
              "message": "write successful"
            }
          """,
        andExpect = Ok
      )
    }
  }
}
