package com.example.services

import javax.inject.{Inject, Singleton}

import com.example.domain.User
import com.example.domain.http.{LoginRequest, LoginResponse}
import com.twitter.util.Future

@Singleton
class UserService @Inject()(tokenService: TokenService) {

  def login(loginRequest: LoginRequest): Future[Option[LoginResponse]] = {

    val token = tokenService.generateTokenForUser(
      User(loginRequest.username)
    )

    Future value Option(LoginResponse(token))
  }
}
