package ninja.filip.pipeline

import org.apache.spark.sql.Dataset

object Pipeline {

  def read[OUT](step: ReadingStep[OUT]): Pipeline[Nothing, OUT] = {
    val pipeline = new Pipeline[Nothing, OUT]()
    pipeline.readingStep = Some(step)
    pipeline
  }

  def from[OUT](ds: Dataset[OUT]): Pipeline[Nothing, OUT] = ???

}

class Pipeline[IN, OUT] {

  private var readingStep: Option[ReadingStep[OUT]] = None
  private var processingStep: Option[ProcessingStep[IN, OUT]] = None
  private var prevPipeline: Option[Pipeline[_, IN]] = None

  def addStep[T](processingStep: ProcessingStep[OUT, T]): Pipeline[OUT, T] = {
    val pipeline = new Pipeline[OUT, T]()
    pipeline.processingStep = Some(processingStep)
    pipeline.prevPipeline = Some(this)
    pipeline
  }

  def collect(): Dataset[OUT] = {
    if (processingStep.isDefined) {
      processingStep.get.process(prevPipeline.get.collect())
    } else {
      readingStep.get.read()
    }
  }
}
