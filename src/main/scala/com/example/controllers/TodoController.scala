package com.example.controllers

import javax.inject.Inject

import com.example.domain.Todo
import com.example.filters.UserContext._
import com.example.services.TodoService
import com.example.swagger.TodoSwaggerDocument
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller




class TodoController @Inject()(todoService: TodoService) extends Controller with SwaggerSupport  {
  implicit protected val swagger = TodoSwaggerDocument

  get("/todo", swagger {
    _.summary("Get list of todos for the logged in user")
      .tag("Todo")
      .headerParam[String]("Authorization","The authorization token",required = true)
      .responseWith[Seq[Todo]](200, "user's list of todos")
  }) { request: Request =>
    todoService.getTodoList(request.user)
  }
}
