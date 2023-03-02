package com.knoldus.models

import com.knoldus.models.Models.{AllUsersResponse, Response, User}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object Models {

  case class User(userId: String, userName: String, userEmail: String)

  case object Initiate

  case class Response(message: String)

  case object GetAllUsers

  case class AllUsersResponse(users: List[User])

}

trait Protocols extends DefaultJsonProtocol {
  implicit val UserFormat: RootJsonFormat[User] = jsonFormat3(User)
  implicit val ResponseFormat: RootJsonFormat[Response] = jsonFormat1(Response)
  implicit val AllUserResponseFormat = jsonFormat1(AllUsersResponse)
}
