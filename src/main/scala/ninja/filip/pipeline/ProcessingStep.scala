package ninja.filip.pipeline

import org.apache.spark.sql.Dataset

trait ProcessingStep[IN, OUT] extends Step{

  def process(ds: Dataset[IN]): Dataset[OUT]

}
