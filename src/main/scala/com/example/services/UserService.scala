package com.example.services

import javax.inject.{Inject, Singleton}
import com.github.t3hnar.bcrypt._
import com.example.domain.User
import com.example.domain.http.{LoginRequest, LoginResponse}
import com.example.repositories.UserRepository
import com.twitter.util.Future

@Singleton
class UserService @Inject()(tokenService: TokenService, userRepository: UserRepository) {

  private def authenticateUser(user: User,password: String): Future[String] =
    if (password.isBcrypted(user.password.toString))
      Future value tokenService.generateTokenForUser(user)
    else Future.exception(new Exception("Invalid password"))


  private def findUserByUsername(username: String): Future[User] =
    userRepository.findByUsername(username) flatMap {
      case None => Future.exception(new Exception("User not found"))
      case Some(user) => Future.value(user)
    }


  def login(loginRequest: LoginRequest): Future[Option[LoginResponse]] = {

    for {
      user <- findUserByUsername(loginRequest.username)
      token <- authenticateUser(user, loginRequest.password)
    } yield {
      Option(LoginResponse(token))
    }
  }
}
