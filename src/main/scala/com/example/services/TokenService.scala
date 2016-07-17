package com.example.services

import javax.inject.Singleton
import com.example.domain.User
import com.twitter.util.Future

@Singleton
class TokenService {
  def generateTokenForUser(user: User): String = {
    s"my-token-${user.username}"
  }

  def userForToken(token: String): Future[Option[User]] = {

    if (token.isEmpty) return Future value None
    val splitToken = token.split("-")

    if (splitToken.length != 3) return Future value None

    val username = splitToken(2)
    val user = User(username)
    Future value Option(user)
  }
}
