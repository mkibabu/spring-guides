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

A more in-depth look is as follows:
Brokers (e.g. RabbitMQ) use two key components to send messages from producers to
consumers: an exchange and a queue. The publisher or consumer creates the exchange
and makes the name public. The consumer then creates a queue and attaches it to an
exchange. Messages received by the exchange are matched to a particular queue via
rules (bindings) defined by the consumer.

An AMPQ message contains three sections:
* header
* properties
* byte[] data
 The data and properties are application-specific, but the headers are defined by
 AMPQ specification. The headers are key-value pairs, and the one that binds a
 message to a particular queue is the *routing-key*. Each queue specifies its own
 binding-key property, and messages are routed to queues whose binding keys match
 the routing-key value in their header.

 The exchange routes the messages to the queues. There are four kinds of exchanges,
 depending on how the exahange uses the queue's binding-key and a message's routing-key:
 * Direct - the bnding key must match the routing key exactly - no wildcard support.
 * Topic - Wildcards are allowed in the binding key; '#' matches zero or more dot-limited
 words, and ' * ' matches exactly one word.
 * Fanout - The routing and binding keys are ignored: all messages go to all subscribed
 queues.
 * Headers - Routes on multiple attributes, some of which can be expressed in ways
 less restrictive than topic exchange headers. They ignore the routing key and use
 whatever header attributes are specified.
