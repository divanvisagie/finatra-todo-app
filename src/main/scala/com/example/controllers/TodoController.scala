package com.example.controllers

import javax.inject.{Inject, Provider}

import com.example.domain.{Todo, UserContext}
import com.example.domain.http.{LoginRequest, TodoPostRequest, TodoPostResponse}
import com.example.services.TodoService
import com.example.swagger.TodoSwaggerDocument
import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class TodoController @Inject()(userContext: Provider[UserContext],todoService: TodoService) extends Controller with SwaggerSupport  {
  implicit protected val swagger = TodoSwaggerDocument

  get("/todo", swagger {
    _.summary("Get list of todos for the logged in user")
      .tag("Todo")
      .headerParam[String]("Authorization","The authorization token", required = true)
      .responseWith[Seq[Todo]](200, "user's list of todos")
  }) { request: Request =>
    info("todo get")
    val user = userContext.get.user
    todoService.list(user)
  }

  post("/todo", swagger {
    _.summary("Create new todo for user")
      .tag("Todo")
      .bodyParam[TodoPostRequest]("Todo object")
      .headerParam[String]("Authorization","The authorization token", required = true)
      .responseWith[TodoPostResponse](200, "write was a success")
  }) { post: TodoPostRequest =>
    info("todo post")
    val user = userContext.get.user
    todoService.create(user, post.toDomain)
    TodoPostResponse("write successful")
  }
}
