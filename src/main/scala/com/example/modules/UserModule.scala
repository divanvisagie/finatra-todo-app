package com.example.modules

import com.example.domain.UserContext
import com.twitter.inject.TwitterModule
import com.twitter.inject.requestscope.RequestScopeBinding

object UserModule extends TwitterModule with RequestScopeBinding{
  override def configure(): Unit = bindRequestScope[UserContext]
}
