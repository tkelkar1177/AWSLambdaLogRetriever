import com.typesafe.config.ConfigFactory
import io.grpc.examples.helloworld.getLogs.{GreeterGrpc, LambdaInvokeRequest, LambdaInvokeReply}
import io.grpc.{Server, ServerBuilder}

import java.util.logging.Logger
import scala.concurrent.{ExecutionContext, Future}

object Server{

  private val logger = Logger.getLogger(classOf[Server].getName)
  private val port = ConfigFactory.load().getInt("parameters.port")

  def main(args: Array[String]): Unit = {

    logger.info("Starting grpcServer.")
    val server = new Server(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()

  }
}

class Server(executionContext: ExecutionContext) {self =>

  private[this] var server: Server = null
  private val logger = Logger.getLogger(classOf[Server].getName)

  /**
   * Starter function for the server
   */
  private def start(): Unit = {
    server = ServerBuilder.forPort(Server.port).addService(GreeterGrpc.bindService(new GreeterImpl, executionContext)).build.start
    logger.info("Server started, listening on " + Server.port)
    sys.addShutdownHook {
      System.err.println("shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("server shut down")
    }
  }

  private def stop(): Unit =
    if (server != null) server.shutdown()

  private def blockUntilShutdown(): Unit =
    if (server != null) server.awaitTermination()

  /**
   * ScalaPB implementation
   * This function calls awsCaller, which calls the lambda function on AWS.
   * Upon receiving response, it completes the future object.
   */
  private class GreeterImpl extends GreeterGrpc.Greeter {
    override def findLog(req: LambdaInvokeRequest): Future[LambdaInvokeReply] = {
      val exec = awsCaller(req.timestamp, req.dt)
      val reply = LambdaInvokeReply(exec)
      Future.successful(reply)
    }
  }
}