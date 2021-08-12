package org.example
package standard

/**
 * @see <a href="https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/getting-started.html"> AWS SQS getting started </a>
 */
abstract class SQSStandardMessageQueueProcessing
  extends SQSMessageQueueProcessing("MyQueue", false) {

  def main(args: Array[String]): Unit

}