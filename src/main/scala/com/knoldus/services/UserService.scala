package com.knoldus.services

import akka.actor.{Actor, ActorLogging}
import com.knoldus.models.Models.{AllUsersResponse, GetAllUsers, Initiate, Response, User}

class UserService extends Actor with ActorLogging{

  val user1 = User("m7gx4o7qgt", "Kuldeepak Gupta", "kuldeepak.gupta@knoldus.com")
  val user2 = User("bx7rf4x6743t", "Knolder", "knolder@knoldus.com")
  val userList = AllUsersResponse(List(user1, user2))

  override def receive: Receive = {
    case Initiate =>
      log.info("[INFO] Initiating Service")
      sender() ! Response("Pong")

    case GetAllUsers =>
      log.info("[INFO] Sending all Users List")
      sender() ! userList
  }

}
