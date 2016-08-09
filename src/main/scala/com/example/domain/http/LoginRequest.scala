package com.example.domain.http

import com.example.domain.User

case class LoginRequest(username: String, password: String) {
  def toDomain = User(0,username,password,"")
}

