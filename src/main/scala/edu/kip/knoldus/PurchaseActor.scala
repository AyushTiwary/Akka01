package edu.kip.knoldus

import akka.actor.{Actor, ActorLogging, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}


class PurchaseActor extends Actor with ActorLogging {

  override def receive = {

    case Customer(name, address, creditCardNo, mobileNo) =>
      log.info("Your item is ready for the booking")
      sender() ! "Congrats, Your Samsung Galaxy S8 has been booked"

    case msg =>
      sender() ! "Unable to book your Samsung Galaxy S8"

  }
}
  class Routing extends Actor {

    var router = {
      val routees = Vector.fill(5) {
        val r = context.actorOf(Props[PurchaseActor])
        context watch r
        ActorRefRoutee(r)
      }
      Router(RoundRobinRoutingLogic(), routees)
    }

    override def receive = {
      case w: Customer =>
        router.route(w, sender())
      case Terminated(a) =>
        router = router.removeRoutee(a)
        val r = context.actorOf(Props[PurchaseActor])
        context watch r
        router = router.addRoutee(r)
    }
  }




