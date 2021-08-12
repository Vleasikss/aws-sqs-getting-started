package org.example
package fifo

import javax.jms.{Message, MessageListener, TextMessage}

object SQSFifoMessageConsumer extends SQSFifoMessageQueueProcessing {

  val sqsMessageListener: MessageListener = (receivedMessage: Message) => {
    println(receivedMessage.getJMSMessageID)
    logger.info(s"Received new message from $queueName: messageValue=${receivedMessage.asInstanceOf[TextMessage].getText}, " +
      s"groupId=${receivedMessage.getStringProperty("JMSXGroupID")}, " +
      s"deduplicationId=${receivedMessage.getStringProperty("JMS_SQS_DeduplicationId")}, " +
      s"sequenceNumber=${receivedMessage.getStringProperty("JMS_SQS_SequenceNumber")}")
  }

  override def main(args: Array[String]): Unit = startConsuming(sqsMessageListener)

}
