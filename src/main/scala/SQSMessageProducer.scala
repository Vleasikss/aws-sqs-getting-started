package org.example

import java.util.Scanner
import javax.jms.MessageProducer

object SQSMessageProducer extends SQSMessageQueueProcessing {

  val TEST_TEXT_MESSAGE = "Hello SQS"

  override def main(args: Array[String]): Unit = {
    val textMessage = session.createTextMessage(TEST_TEXT_MESSAGE)
    // Create a producer for the 'SQS_QUEUE_NAME'
    val producer: MessageProducer = session.createProducer(queue)
    producer.send(textMessage)
    logger.info(s"Sent JMS message to $SQS_QUEUE_NAME, message=$TEST_TEXT_MESSAGE")

    new Scanner(System.in).nextLine()
    connection.close()

  }

}
