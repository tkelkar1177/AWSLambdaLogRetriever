import com.typesafe.config.ConfigFactory
import LambdaInvocation.getLogs.{LambdaFuncGrpc, LambdaInvokeRequest, LambdaInvokeReply}
import io.grpc.{Server, ServerBuilder}

import java.util.logging.Logger
import scala.concurrent.{ExecutionContext, Future}

object grpcServer{

  private val logger = Logger.getLogger(classOf[grpcServer].getName)
  private val port = ConfigFactory.load().getInt("parameters.port")

  def main(args: Array[String]): Unit = {

    logger.info("Starting grpcServer.")
    val server = new grpcServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()

  }
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