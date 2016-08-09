package com.example.services

import com.example.domain.http.TodoPostResponse
import com.example.domain.{Todo, User}
import com.google.inject.Singleton
import com.twitter.util.Future

@Singleton
class TodoService {
  def create(username: String, todo: Todo): Future[TodoPostResponse] = {
    Future.value(TodoPostResponse("write successful"))
  }

  def list(username: String) : Future[Seq[Todo]] = {
    val rubbishData = Seq[Todo](
       Todo("Feed the dogs"),
       Todo("Wash the cat")
    )
    Future.value(rubbishData)
  }
}
