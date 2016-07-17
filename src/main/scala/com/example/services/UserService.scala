package com.example.services

import javax.inject.Singleton

import com.example.domain.http.{LoginRequest, LoginResponse}
import com.twitter.util.Future

@Singleton
class UserService {

  def login(loginRequest: LoginRequest): Future[Option[LoginResponse]] = {
    val response = LoginResponse("")
    Future value Option(response)
  }
}
