package com.example.domain

import javax.inject.Inject

case class User(username: String)

case class UserContext(var user: User) {
  def this() {
    this(User(""))
  }
}
//object UserContext {
//  def apply(user: User) = new UserContext()
//}


