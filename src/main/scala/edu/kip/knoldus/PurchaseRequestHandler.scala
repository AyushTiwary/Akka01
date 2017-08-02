package edu.kip.knoldus

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class PurchaseRequestHandler(validationRef : ActorRef) extends Actor with ActorLogging {

  override def receive = {

    case customer @ Customer(name, address, creditCardNo, mobileNo) => {

      log.info(s"Mobile Purchase Request by $name. Your Contact Number is $mobileNo")
      validationRef.forward(customer)

    }

    case msg => log.error("Invalid Call")

  }

}
