import LambdaInvocation.getLogs.LambdaFuncGrpc.LambdaFuncBlockingStub
import LambdaInvocation.getLogs.{LambdaFuncGrpc, LambdaInvokeRequest}
import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}
import HelperUtils.{CreateLogger, ObtainConfigReference}
import com.typesafe.config.Config

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

object Client {

  val config: Config = ObtainConfigReference("parameters") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  private[this] val logger = Logger.getLogger(classOf[Client].getName)

  def apply(host: String, port: Int): Client = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = LambdaFuncGrpc.blockingStub(channel)
    new Client(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    logger.info("Starting Client:" + "" +
      "port\t: " + config.getInt("parameters.port"))
    val client = Client("localhost", config.getInt("parameters.port"))
    try client.find(config.getString("parameters.timestamp"), config.getString("parameters.dt"))
    finally client.shutdown()
  }
}

class Client private(private val channel: ManagedChannel, private val blockingStub: LambdaFuncBlockingStub) {
  private[this] val logger = Logger.getLogger(classOf[Client].getName)

  val config: Config = ObtainConfigReference("parameters") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  def shutdown(): Unit = {
    logger.info("Trying to shutdown")
    channel.shutdown.awaitTermination(config.getLong("parameters.duration"), TimeUnit.SECONDS)
  }

  def find(timestamp: String, dt: String): Unit = {
    val request = LambdaInvokeRequest(timestamp, dt)
    logger.info("Request created")
    blockingStub.retrieveLogs(request)
  }
}