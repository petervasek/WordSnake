package powerex.backend.task.kafkastreams.consumer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;

public class KafkaConsumerConfig {

  static Properties consumerProperties() {
    Properties p = new Properties();
    p.put("group.id", "sentence-consumer");
    p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");
    p.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
    p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
    p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);

    return p;
  }

}
