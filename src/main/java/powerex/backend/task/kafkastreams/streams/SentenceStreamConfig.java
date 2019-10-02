package powerex.backend.task.kafkastreams.streams;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class SentenceStreamConfig {

  static Properties streamProperties() {
    Properties p = new Properties();
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "input-sentence-transformer");
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");
    p.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
    p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
    p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);

    return p;
  }
}
