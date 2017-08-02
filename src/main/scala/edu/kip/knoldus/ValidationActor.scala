package edu.kip.knoldus
import akka.actor.{Actor, ActorLogging, ActorRef}



class ValidationActor(purchaseRef : ActorRef) extends Actor with ActorLogging{

  var numberOfItems = 1000

  override def receive: Receive ={

    case customer @ Customer(name, address, creditCardNo, mobileNo) =>
      log.info(s"Hi $name, we are checking the availability with the stock")

      self ! validateNumberOfItem(customer)

  }

  def validateNumberOfItem(customer: Customer) = {

    if(numberOfItems>0){

      log.info(s"Hey Congrats, Mobile is available with availability $numberOfItems")
      numberOfItems -= 1
      purchaseRef.forward(customer)

    }

    else{

      log.error("Invalid Request")

    }

  }

}
