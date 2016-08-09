package com.example.domain

import javax.inject.Inject

import sun.security.util.Password

case class User(
  id: Int,
  username: String,
  password: String,
  email: String
)


//object UserContext {
//  def apply(user: User) = new UserContext()
//}


