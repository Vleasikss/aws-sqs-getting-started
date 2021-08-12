package org.example
package fifo

import java.util.UUID
import javax.jms.TextMessage

/**
 * <h1>First in First out.</h1>
 * Guaranties that the messages will be retrieved in the order they were sent.
 * Guaranties that the messages will be send exactly one time
 *
 * Duplicates aren't introduced into the queue.
 * (Duplicate message -> message1.deduplicationId == message2.deduplicationId).
 *
 * Message FIFO structure:
 *
 *  - <strong>Message deduplicationId.</strong>
 *    If a message with a particular message deduplication ID is sent successfully,
 *    any messages sent with the same message deduplication ID are accepted successfully
 *    but aren't delivered during the 5-minute deduplication interval.
 *
 *  - <strong>Message group ID.</strong>
 *    Message that belong to the same message group are always processed one by one,
 *    in a strict order relative to the message group
 *
 *  - <strong>receive request attempt ID.</strong>
 *    Token user for deduplication ReceiveMessage calls
 *
 *  - <strong>Sequence number.</strong
 *    Unique number that Amazon SQS assigns to each message
 *
 * @see <a href="https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/FIFO-queues.html">FIFO queues</a>
 * @see <a href="https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/FIFO-queues-understanding-logic.html">FIFO delivery logic</a>
 */
object SQSFifoMessageProducer extends SQSFifoMessageQueueProcessing {

  /**
   * When using FIFO, jmsx_group_id_key is a required field
   */
  val JMSX_GROUP_ID_KEY = "JMSXGroupID"
  val JMSX_GROUP_ID_VALUE = "Default"

  val generator: () => TextMessage = () => {
    val messageValue = UUID.randomUUID().toString
    val fifoMessage: TextMessage = session.createTextMessage(messageValue)
    fifoMessage.setStringProperty(JMSX_GROUP_ID_KEY, JMSX_GROUP_ID_VALUE)
    fifoMessage
  }

  override def main(args: Array[String]): Unit = startProducing(generator, 5000)

}
