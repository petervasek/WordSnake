package powerex.backend.task.kafkastreams.producer;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class KafkaProducerConfig {

  static Properties producerProperties() {
    Properties p = new Properties();
    p.put("bootstrap.servers", "localhost:9091,localhost:9092,localhost:9093");
    p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    return p;
  }
}
