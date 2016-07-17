package com.example.filters

import javax.inject.Inject

import com.example.domain.User
import com.example.services.TokenService
import com.twitter.finagle.http.Status._
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

object UserContext {
  private val UserField = Request.Schema.newField[User]()

  implicit class UserContextSyntax(val request: Request) extends AnyVal {
    def user: User = request.ctx(UserField)
  }

  private[filters] def setUser(request: Request, user: User): Unit = {
    request.ctx.update(UserField, user)
  }
}

class TokenFilter @Inject()(tokenService: TokenService)
  extends SimpleFilter[Request,Response] {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {

    val authHeader = request.headerMap.getOrElse("Authorization","")

    tokenService.userForToken(authHeader) flatMap {
      case Some(tokenUser: User) =>
        UserContext.setUser(request,tokenUser)
        service(request)
      case _ =>
        request.response.statusCode = Unauthorized.code
        Future value request.response
    }
  }
}
