package powerex.backend.task.kafkastreams.streams;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import java.util.Properties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.kafka.streams.StreamsConfig;
import powerex.backend.task.kafkastreams.common.KafkaConfig;

@Slf4j
@UtilityClass
class SentenceStreamConfig {

  static Properties streamProperties() {
    Properties p = new Properties();
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, KafkaConfig.STREAMS_APP_ID);
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_BROKERS);
    p.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, KafkaConfig.SCHEMA_REGISTRY_URL);
    p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
    p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);

    log.debug("Stream properties:\n" + p.toString());
    return p;
  }
}
