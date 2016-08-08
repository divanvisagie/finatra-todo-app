package com.example.filters

import javax.inject.Inject

import com.example.domain.{User, UserContext}
import com.example.services.TokenService
import com.twitter.finagle.http.Status._
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.inject.requestscope.FinagleRequestScope
import com.twitter.util.Future



class TokenFilter @Inject()(requestScope: FinagleRequestScope, tokenService: TokenService)
  extends SimpleFilter[Request,Response] {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {

    val authHeader = request.headerMap.getOrElse("Authorization","")

    tokenService.userForToken(authHeader) flatMap {
      case Some(tokenUser: User) =>
        val userContext = UserContext(tokenUser)
        requestScope.seed[UserContext](userContext)
        service(request)
      case _ =>
        request.response.statusCode = Unauthorized.code
        Future value request.response
    }
  }
}
