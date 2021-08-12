package org.example

import com.amazon.sqs.javamessaging.SQSConnection
import com.amazonaws.services.sqs.model.CreateQueueRequest

import java.util
import java.util.Scanner
import javax.jms.{MessageListener, MessageProducer, Queue, Session, TextMessage}

abstract class SQSMessageQueueProcessing(val queueName: String, isFifo: Boolean) extends Logging {

  /**
   * Creates a connection to SQS
   */
  val connection: SQSConnection = SqsConnectionFactory.getConnection

  /**
   * Create the non transacted session with AUTO_ACKNOWLEDGE mode
   */
  val session: Session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)

  val queue: Queue = createQueueIfNotExists(queueName, session)

  def startConsuming(messageListener: MessageListener): Unit = {
    val fifoConsumer = session.createConsumer(queue)
    fifoConsumer.setMessageListener(messageListener)
    connection.start()
  }

  def startProducing(messageGenerator: () => TextMessage, waitInterval: Int, sendMessageCount: Int = 10): Unit = {
    val producer: MessageProducer = session.createProducer(queue)
    (1 to sendMessageCount).foreach(_ => {
      val message = messageGenerator()
      Thread.sleep(waitInterval)
      producer.send(message)
      logger.info(s"Sent JMS message to $queueName, message=${message.getText}")
    })

    new Scanner(System.in).nextLine()
    connection.close()
  }

  /**
   * Queue has to be specified before using.
   * Creates a queue identity and specify the queue name to the session
   *
   * @param queueName - name of
   * @param session - sqsConnectionSession
   * @return sessionQueue
   */
  private def createQueueIfNotExists(queueName: String, session: Session): Queue = {
    if (isFifo) {
      val client = connection.getWrappedAmazonSQSClient
      val attributes = new util.HashMap[String, String]()
      attributes.put("FifoQueue", "true")
      attributes.put("ContentBasedDeduplication", "true")
      client.createQueue(new CreateQueueRequest().withQueueName(queueName)
        .withAttributes(attributes))
    } else {
      val client = connection.getWrappedAmazonSQSClient
      if (!client.queueExists(queueName)) client.createQueue(queueName)
    }
    session.createQueue(queueName)
  }


}
