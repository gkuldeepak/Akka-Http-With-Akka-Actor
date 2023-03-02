package com.knoldus.routes

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.knoldus.models.Models.{AllUsersResponse, GetAllUsers, Initiate, Response, User}
import com.knoldus.models.Protocols
import com.knoldus.services.UserService
import spray.json._
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps
import scala.util.{Failure, Success}

class UserRoutes extends Protocols{

  implicit val actorSystem = ActorSystem("ActorSystem")
  implicit val materializer = ActorMaterializer()
  implicit val timeOut = Timeout(5 seconds)

  val actor = actorSystem.actorOf(Props[UserService], "UserService")

  def initiateRoutes: Future[Http.ServerBinding] = {
    val route: Route = {
      path("ping"){
        get {
          pingRoute
        }
      } ~ path("user"){
        get {
          getUsers
        }
      }
    }
    Http().bindAndHandle(route, "localhost", 8008)
  }

  private def pingRoute: Route = {
    val result: Future[Response] = (actor ? Initiate).mapTo[Response]
    onComplete(result){
      case Success(value) => complete(StatusCodes.OK, HttpEntity(ContentTypes.`application/json`, value.toJson.prettyPrint))
      case Failure(exception) => complete(StatusCodes.BadRequest, HttpEntity(ContentTypes.`application/json`, exception.toString))
    }
  }

  private def getUsers: Route = {
    val result = (actor ? GetAllUsers).mapTo[AllUsersResponse]
    onComplete(result){
      case Success(value) => complete(StatusCodes.OK, HttpEntity(ContentTypes.`application/json`, value.toJson.prettyPrint))
      case Failure(exception) => complete(StatusCodes.BadRequest, HttpEntity(ContentTypes.`application/json`, exception.toString))
    }
  }


}
