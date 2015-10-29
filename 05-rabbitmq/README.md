# MESSAGING WITH RABBITMQ

Builds an application that publishes a message using Spring Advanced Message Queuing
Protocol's RabbitTemplate and subscribes to the message on a POJO using MessageListenerAdapter.
Project documentation at [Messaging with RabbitMQ] (https://spring.io/guides/gs/messaging-rabbitmq/).

RabbitMQ is an AMPQ server. More on RabbitMQ can be found on the [RabbitMQ website] (http://www.rabbitmq.com/)

## Understanding AMPQ
AMPQ is an open wire spec for async messaging. The other common Java messaging service 
JMS (Java Message Service). In JMS, message formats and transmission protocols
are not specified, leading to many different implementations and formats (though
the API is uniform). AMPQ has publishes its spec in a downloadable XML format. This
allows library maintainers to generate APIs driven by the specs and easily marshal
and unmarshal messages. This makes it easy for applications to communicate through
AMPQ brokers.

### Brokers and Their Role
Messaging brokers receive messages from *publishers* and route them to *consumers*.

Internally, messages are published to *exchanges*, which are somewhat analogous to
post offices. Exchanges then distribute message copies to *queues* using rules called
*bindings* or *routes*. The messages are then either delivered to consumers subscribed
to the queues, or the consumers fetch/pull messages from the quese(s) on demand.

Upon receipt of the mesage, the consumer sends an ACK to the broker.
