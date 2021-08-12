package org.example
package standard


import java.util.UUID
import javax.jms.TextMessage

/**
 * @see <a href="https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/standard-queues.html">Amazon SQS Standard queues</a>
 *
 *      To guarantee sort of message, use <a href=https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/FIFO-queues.html>FIFO queues</a>
 */
object SQSStandardMessageProducer extends SQSStandardMessageQueueProcessing {

  val generator: () => TextMessage = () =>
    session.createTextMessage(UUID.randomUUID().toString)

  override def main(args: Array[String]): Unit =
    startProducing(generator, 5000)

}
