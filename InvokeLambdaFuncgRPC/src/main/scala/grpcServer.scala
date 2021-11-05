import LambdaInvocation.getLogs.{LambdaFuncGrpc, LambdaInvokeRequest, LambdaInvokeReply}
import io.grpc.{Server, ServerBuilder}
import HelperUtils.{CreateLogger, ObtainConfigReference}
import com.typesafe.config.Config

import java.util.logging.Logger
import scala.concurrent.{ExecutionContext, Future}

object grpcServer {

  val config: Config = ObtainConfigReference("parameters") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  private val logger = Logger.getLogger(classOf[grpcServer].getName)

  def main(args: Array[String]): Unit = {
    val server = new grpcServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = config.getInt("parameters.port")
}

class grpcServer(executionContext: ExecutionContext) {self =>

  private[this] var server: Server = _
  private val logger = Logger.getLogger(classOf[grpcServer].getName)

  private def start(): Unit = {
    server = ServerBuilder.forPort(grpcServer.port).addService(LambdaFuncGrpc.bindService(new LambdaFuncImpl, executionContext)).build.start
    logger.info("Server started, listening on " + grpcServer.port)
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

  private class LambdaFuncImpl extends LambdaFuncGrpc.LambdaFunc {
    override def retrieveLogs(req: LambdaInvokeRequest): Future[LambdaInvokeReply] = {
      val exec = LambdaInvoker(req.timestamp, req.dt)
      val reply = LambdaInvokeReply(exec)
      Future.successful(reply)
    }
  }
}