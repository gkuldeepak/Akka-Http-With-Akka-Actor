package com.knoldus.bootstrap

import com.knoldus.routes.UserRoutes

object Application extends App {

  val userRoutes = new UserRoutes
  userRoutes.initiateRoutes
  
}
