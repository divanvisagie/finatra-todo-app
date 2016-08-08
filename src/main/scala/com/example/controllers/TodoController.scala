package com.example.controllers

import javax.inject.Inject

import com.example.domain.Todo
import com.example.domain.http.{LoginRequest, TodoPostRequest, TodoPostResponse}
import com.example.filters.UserContext._
import com.example.services.TodoService
import com.example.swagger.TodoSwaggerDocument
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request

class TodoController @Inject()(todoService: TodoService) extends Controller with SwaggerSupport  {
  implicit protected val swagger = TodoSwaggerDocument

  get("/todo", swagger {
    _.summary("Get list of todos for the logged in user")
      .tag("Todo")
      .headerParam[String]("Authorization","The authorization token", required = true)
      .responseWith[Seq[Todo]](200, "user's list of todos")
  }) { request: Request =>
    info("todo get")
    todoService.list(request.user)
  }

  post("/todo", swagger {
    _.summary("Create new todo for user")
      .tag("Todo")
      .bodyParam[TodoPostRequest]("Todo object")
      .headerParam[String]("Authorization","The authorization token", required = true)
      .responseWith[TodoPostResponse](200, "write was a success")
  }) { post: TodoPostRequest =>
    val user = request.asInstanceOf[Request].user

    info("todo post")
    //todoService.create(request.user, request)
    TodoPostResponse("write successful")
  }
}
