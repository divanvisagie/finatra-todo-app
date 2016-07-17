package com.example.warmup

import javax.inject.Inject

import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.httpclient.RequestBuilder._
import com.twitter.inject.utils.Handler

class WarmupHandler @Inject()(httpWarmup: HttpWarmup) extends Handler {
  override def handle(): Unit = {
    httpWarmup.send(get("/ping"),times = 5)
  }
}
