package org.example

import com.amazon.sqs.javamessaging.{ProviderConfiguration, SQSConnection, SQSConnectionFactory}

import javax.jms.{Queue, Session}
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import org.apache.log4j.PropertyConfigurator


/**
 * @see <a href="https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/getting-started.html"> AWS SQS getting started </a>
 */
trait SQSMessageQueueProcessing extends Logging {

  val SQS_QUEUE_NAME = "MyQueue"

  PropertyConfigurator.configure(getClass.getResourceAsStream("/conf/log4j.properties"))

  /**
   * Create a new connection factory
   * with all defaults (credentials and region) set automatically
   */
  val connectionFactory = new SQSConnectionFactory(new ProviderConfiguration, AmazonSQSClientBuilder.defaultClient())

  /**
   * Creates a connection to SQS
   */
  val connection: SQSConnection = connectionFactory.createConnection()

  /**
   * Create the non transacted session with AUTO_ACKNOWLEDGE mode
   */
  val session: Session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)

  /**
   * Create a queue identity and specify the queue name to the session
   */
  val queue: Queue = session.createQueue(SQS_QUEUE_NAME)

  def main(args: Array[String]): Unit

}
