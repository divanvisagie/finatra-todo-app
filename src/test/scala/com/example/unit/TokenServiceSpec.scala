package com.example.unit

import com.example.domain.{User, UserContext}
import com.example.services.TokenService
import com.example.unit.helper.TwitterFutures
import org.scalatest.{FlatSpec, Matchers}

class TokenServiceSpec extends FlatSpec with Matchers with TwitterFutures  {
  val tokenService = new TokenService()

  "given example user, generateToken " should " return deserializable token" in {
    val user = User(0,"modersky","","")
    val userContext = UserContext("modersky")

    val token = tokenService.generateTokenForUser(user)

    whenReady(tokenService.userContextForToken(token)) { tokenUser =>
      tokenUser.getOrElse(None) should equal(userContext)
    }
  }
}
