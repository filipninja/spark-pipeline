package ninja.filip.pipeline

import org.apache.spark.sql.{Row, SparkSession}
import org.scalatest.GivenWhenThen
import org.scalamock.scalatest.MockFactory

class PipelineTest extends org.scalatest.WordSpec
  with GivenWhenThen
  with MockFactory {

  "A Pipeline" when {
    "with ReadingStep" should {
      "invoke read" in {
        Given("ReadingStep and Pipeline")
        val readingStep = mock[ReadingStep[Row]]
        (readingStep.read _).expects().once()
        val pipeline = Pipeline.read(readingStep)

        When("collecting result")
        pipeline.collect()

        Then("read() should be executed")
      }
    }
    "with ProcessingStep" should {
      "invoke process" in {
        Given("ReadingStep, ProcessingStep and Pipeline")
        val spark = SparkSession
          .builder()
          .master("local[*]")
          .appName("Spark SQL basic example")
          .config("spark.some.config.option", "some-value")
          .getOrCreate()
        import spark.implicits._

        val readingStep = stub[ReadingStep[Row]]
        (readingStep.read _).when().returns(Seq("dupa").toDF("col1"))
        val processingStep = mock[ProcessingStep[Row, String]]
        (processingStep.process _).expects(*).once()
        val pipeline = Pipeline
          .read(readingStep)
          .addStep(processingStep)

        When("collecting result")
        pipeline.collect()

        Then("read() and process() should be executed")
      }
    }
  }
}
