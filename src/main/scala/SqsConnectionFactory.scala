package org.example

import com.amazon.sqs.javamessaging.{ProviderConfiguration, SQSConnection, SQSConnectionFactory}
import com.amazonaws.services.sqs.AmazonSQSClientBuilder

object SqsConnectionFactory {

  /**
   * Create a new connection factory
   * with all defaults (credentials and region) set automatically
   */
  private val connectionFactory = new SQSConnectionFactory(new ProviderConfiguration, AmazonSQSClientBuilder.defaultClient())

  def getConnection: SQSConnection = connectionFactory.createConnection()

}
