package com.example.domain

case class UserContext(var username: String) {
  def this() {
    this("")
  }
}
