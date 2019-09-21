package powerex.backend.task.kafkastreams.consumer;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerConfig {

  static Properties consumerProperties() {
    Properties p = new Properties();
    p.put("group.id", "sentence-consumer");
    p.put("bootstrap.servers", "localhost:9091,localhost:9092,localhost:9093");
    p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

    return p;
  }

}
