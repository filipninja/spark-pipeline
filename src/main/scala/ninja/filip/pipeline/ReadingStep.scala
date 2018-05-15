package ninja.filip.pipeline

import org.apache.spark.sql.Dataset

trait ReadingStep[OUT] extends Step{

  def read(): Dataset[OUT]

}
