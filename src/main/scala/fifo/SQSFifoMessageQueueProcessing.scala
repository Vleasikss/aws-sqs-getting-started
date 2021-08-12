package org.example
package fifo

abstract class SQSFifoMessageQueueProcessing
  extends SQSMessageQueueProcessing("MyFifoQueue.fifo", true) with Logging {

  def main(args: Array[String]): Unit

}
