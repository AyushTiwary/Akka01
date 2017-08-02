package edu.kip.knoldus
import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration.DurationInt


object CustomerRequest extends App{
  val system = ActorSystem("CustomerActorSystem")

  val props = Props(classOf[Routing])
  val ref = system.actorOf(props)

  val props2 = Props(classOf[ValidationActor],ref)
  val ref2 = system.actorOf(props2)

  val props3 = Props(classOf[PurchaseRequestHandler],ref2)
  val ref3 = system.actorOf(props3)

  implicit val timeout = Timeout(10 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  val purchase1 = ref3 ? Customer("Ayush","Noida",1234000, 956079048)
  purchase1.foreach(println)

  val purchase2 = ref3 ? Customer("Tiwari","Delhi",3435 ,43346)
  purchase2.foreach(println)

}
