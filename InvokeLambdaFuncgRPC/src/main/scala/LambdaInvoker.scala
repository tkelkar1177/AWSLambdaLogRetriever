import com.google.gson.Gson
import com.typesafe.scalalogging.Logger
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

import java.io.{File, PrintWriter}

case class Input(timestamp: String, dt: String)

object CallLambdaFunc {

  val logger: Logger = Logger("Logger")

  def main(args: Array[String]): Unit = {

    val input = new Input("20:33:10.999", "00:01:00.000")
    val inputAsJson: String = new Gson().toJson(input)

    val url = "https://nsefpc32vd.execute-api.us-east-1.amazonaws.com/default/TSAvailability/"
    val timeout = 1800
    val requestConfig = RequestConfig.custom().build()

    val client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()

    val post:HttpPost = new HttpPost(url)
    post.setEntity(new StringEntity(inputAsJson))


    val response:CloseableHttpResponse = client.execute(post)
    val entity = response.getEntity
    val str = EntityUtils.toString(entity,"UTF-8").split("\",\"")
    val pw = new PrintWriter(new File("OutputLogs/MatchedLogs.txt" ))
    str.foreach(line => pw.write(line + "\n"))
    pw.close()
  }
}