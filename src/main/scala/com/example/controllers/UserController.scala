package com.example.controllers

import javax.inject.Inject
import com.example.domain.http.{LoginRequest, LoginResponse}
import com.example.services.UserService
import com.example.swagger.TodoSwaggerDocument
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finatra.http.Controller


class UserController @Inject()(userService: UserService) extends Controller with SwaggerSupport {

  implicit protected val swagger = TodoSwaggerDocument

  post("/login", swagger {
    _.summary("Check login details and return token if successful")
      .tag("User")
      .bodyParam[LoginRequest]("User details")
      .responseWith[LoginResponse](200, "Valid token to use in headers")
      .responseWith(401, "Invalid Credentials")
  }) { loginRequest: LoginRequest =>

    for {
      loginResponse <- userService.login(loginRequest)
    } yield {
      loginResponse match {
        case None => response.unauthorized("Invalid credentials")
        case _ => response.ok(loginResponse)
      }
    }
  }
}
