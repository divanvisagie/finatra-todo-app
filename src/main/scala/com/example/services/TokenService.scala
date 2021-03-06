package com.example.services

import javax.inject.Singleton

import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}
import com.example.domain.{User, UserContext}
import com.twitter.util.Future

@Singleton
class TokenService {

  val secret: String = "mySecret"

  private def generateTokenFromMap(payload: Map[String, Any]): String = {
    val header = JwtHeader("HS256")
    val claimSet = JwtClaimsSet(
      payload
    )
    JsonWebToken(header, claimSet, secret)
  }

  private def getPayloadForToken(token: String): Option[Map[String, String]] = token match {
    case JsonWebToken(header, claimsSet, signature) =>
      claimsSet.asSimpleMap.toOption
    case x =>
      None
  }

  def generateTokenForUser(user: User): String = {
    generateTokenFromMap(Map(
      "username" -> user.username
    ))
  }

  def userContextForToken(token: String): Future[Option[UserContext]] = {
    Future.value(getPayloadForToken(token) match {
      case Some(map: Map[String,String]) => Option(
        UserContext(
          username = map("username")
        )
      )
      case None => None
    })
  }
}
