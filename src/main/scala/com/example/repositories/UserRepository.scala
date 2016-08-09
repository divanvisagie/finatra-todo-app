package com.example.repositories

import javax.inject.Inject

import com.example.domain.User
import com.twitter.util.{Future => TwitterFuture}
import slick.driver.PostgresDriver.api._
import com.scalaza.raven.future.Conversions._

import scala.concurrent.ExecutionContext.Implicits.global

class UserRepository @Inject()(db: Database) {
  implicit val session: Session = db.createSession()

  private class UserTable(tag: Tag) extends Table[User](tag, "user"){
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def password = column[String]("password")
    def email = column[String]("email")
    def * = (id, username, password, email) <> ((User.apply _).tupled, User.unapply)
  }

  private val userTable = TableQuery[UserTable]

  val setup = DBIO.seq(
    userTable.schema.create
  )
  val setupFuture = db.run(setup)

  def findByUsername(username: String): TwitterFuture[Option[User]] =
    db.run {
      userTable.filter(_.username === username).result.headOption
    }.toTwitterFuture

  def createUser(user: User) : TwitterFuture[Option[User]] = {
    val transaction = userTable.filter(_.username === user.username).result.headOption.flatMap {
      case Some(u) => DBIO.successful(None)
      case None =>
        val userId =
          (userTable returning userTable.map(_.id)) += User(
            id = 0,
            email = user.email,
            password = user.password,
            username = user.username
          )

        val newUser = userId.map { id =>
          User(id,user.username,"",user.email)
        }
        newUser
    }.transactionally
    db.run(transaction).map {
      case u: User => Option(u)
      case None => None
    }.toTwitterFuture
  }
}
