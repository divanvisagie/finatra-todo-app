package com.example.domain.http

import com.example.domain.Todo
import com.twitter.finatra.validation.Size

case class TodoPostRequest(@Size(min=1,max = 150) text: String) {
  def toDomain = {
    Todo(text = text)
  }
}
