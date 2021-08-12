package org.example

import java.util.Scanner
import javax.jms.{Message, MessageListener, TextMessage}

object SQSMessageConsumer extends SQSMessageQueueProcessing {

  val sqsMessageListener:MessageListener = (message: Message) =>
    logger.info(s"Received new message from $SQS_QUEUE_NAME: messageValue=${message.asInstanceOf[TextMessage].getText}, jmsMessageId=${message.getJMSMessageID}")

  def main(args: Array[String]): Unit = {
    // Create a consumer for the 'SQS_QUEUE_NAME'
    val consumer = session.createConsumer(queue)
    consumer.setMessageListener(sqsMessageListener)
    connection.start()
    new Scanner(System.in).nextLine()
    connection.close()

  }
}
