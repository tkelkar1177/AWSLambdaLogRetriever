import com.google.gson.Gson
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.Logger
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

import java.io.{File, PrintWriter}
import scala.concurrent.duration.{Duration, DurationInt}

case class Input(timestamp: String, dt: String)

class LambdaInvoker

object LambdaInvoker {

  val logger: Logger = Logger("Logger")

  val duration: Duration = ConfigFactory.load().getInt("parameters.duration").seconds

  def apply(timestamp: String, dt: String): Array[String] = {
    logger.info("Started awsCaller execution\n")
    logger.info("Calling sendRequest")
    sendRequest(timestamp, dt)
  }

  def sendRequest(timestamp: String, dt: String): Array[String] = {
    val input = Input(ConfigFactory.load().getString("lambdaInvoker.timestamp"), ConfigFactory.load().getString("lambdaInvoker.dt"))
    val inputAsJson: String = new Gson().toJson(input)

    val url = ConfigFactory.load().getString("lambdaInvoker.url")

    val requestConfig = RequestConfig.custom().build()

    val client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()

    val post: HttpPost = new HttpPost(url)
    post.setEntity(new StringEntity(inputAsJson))

    val response: CloseableHttpResponse = client.execute(post)

    val statusLine = response.getStatusLine

    val entity = response.getEntity

    val str = EntityUtils.toString(entity,"UTF-8").split("\",\"")
    val pw = new PrintWriter(new File(ConfigFactory.load().getString("lambdaInvoker.fileOutputPath")))
    str.foreach(line => pw.write(line + "\n"))

    if(str.length == 1) {
      logger.info(str(0))
    }
    else {
      logger.info(statusLine.getStatusCode + " " + statusLine.getReasonPhrase)
    }
    pw.close()
    str
  }
}