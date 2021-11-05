import LambdaInvoker.LambdaInvoker.config
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class testgRPC extends AnyFlatSpec with Matchers {
  behavior of "configuration parameters module"

  it should "obtain the port value string" in {
    config.getString("parameters.dt") shouldBe "00:00:01.000"
  }

  it should "obtain the port value string" in {
    config.getString("parameters.port") shouldBe "50051"
  }

  it should "obtain the duration value string" in {
    config.getString("parameters.duration") shouldBe "10"
  }

  it should "obtain the url value string" in {
    config.getString("parameters.url") shouldBe "https://nsefpc32vd.execute-api.us-east-1.amazonaws.com/default/TSAvailability/"
  }

  it should "obtain the output file path string" in {
    config.getString("parameters.fileOutputPath") shouldBe "OutputLogs/MatchedLogs.txt"
  }
}