import com.typesafe.config.{Config, ConfigFactory}
import LambdaInvocation.getLogs.LambdaFuncGrpc.LambdaFuncBlockingStub
import LambdaInvocation.getLogs.{LambdaFuncGrpc, LambdaInvokeRequest}
import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

object Client {

  val config: Config  = ConfigFactory.load()
  val timestamp: String = config.getString("parameters.timestamp")
  val dt: String = config.getString("parameters.dt")
  val port: Int = config.getInt("parameters.port")

  private[this] val logger = Logger.getLogger(classOf[Client].getName)

  def apply(host: String, port: Int): Client = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = LambdaFuncGrpc.blockingStub(channel)
    new Client(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    logger.info("Starting Client:" + "" +
      "port\t: " + port)
    val client = Client("localhost", port)
    try client.find(timestamp, dt)
    finally client.shutdown()
  }
}

class Client private(private val channel: ManagedChannel, private val blockingStub: LambdaFuncBlockingStub) {
  private[this] val logger = Logger.getLogger(classOf[Client].getName)

  def shutdown(): Unit = {
    logger.info("Trying to shutdown")
    channel.shutdown.awaitTermination(ConfigFactory.load().getLong("parameters.duration"), TimeUnit.SECONDS)
  }

  def find(timestamp: String, dt: String): Unit = {
    val request = LambdaInvokeRequest(timestamp, dt)
    logger.info("Request created")
    try {
      val response = blockingStub.retrieveLogs(request)
      logger.info("Response: " + response.result)
      if (response.result == null)
        logger.info("No log statements found for given parameters")
      else
        logger.info("Log message(s) at (and around) Index " + response.result + " fits the given parameters.")
    }
    catch {
      case e: StatusRuntimeException =>
        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
    }
  }
}