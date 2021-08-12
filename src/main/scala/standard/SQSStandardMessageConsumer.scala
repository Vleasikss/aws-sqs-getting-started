package org.example
package standard

import javax.jms.{Message, MessageListener, TextMessage}

object SQSStandardMessageConsumer extends SQSStandardMessageQueueProcessing {

  val sqsMessageListener: MessageListener = (message: Message) =>
    logger.info(s"Received new message from $queueName: messageValue=${message.asInstanceOf[TextMessage].getText}, jmsMessageId=${message.getJMSMessageID}")

  def main(args: Array[String]): Unit = startConsuming(sqsMessageListener)

}
