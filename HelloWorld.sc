import $ivy.`com.typesafe.akka::akka-actor:2.5.18`
import $ivy.`com.typesafe.akka::akka-stream:2.5.18`
import $ivy.`com.typesafe.akka::akka-http:10.1.3`

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.Await
import scala.util.{Failure, Success}

implicit val system: ActorSystem = ActorSystem("helloworld")
implicit val materializer: ActorMaterializer = ActorMaterializer()
import system.dispatcher

def route = path("hello") { get { complete("Hello, World!") } }

val host = "0.0.0.0"
val port = 8188
val bindingFuture = Http().bindAndHandle(route, host, port)
bindingFuture.onComplete {
  case Success(serverBinding) => println(s"listening to ${serverBinding.localAddress}")
  case Failure(error) => println(s"error: ${error.getMessage}")
}

