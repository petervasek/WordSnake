package powerex.backend.task.kafkastreams.streams;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import powerex.backend.task.kafkastreams.common.KafkaConfig;

@EnableKafka
@Configuration
@Slf4j
@AllArgsConstructor
public class SentenceStreamConfig {

  private final KafkaConfig kafkaConfig;

  @Bean
  Properties streamProperties() {
    Properties p = new Properties();
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getStreamId());
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getKafkaBrokers());
    p.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaConfig.getSchemaRegistryUrl());
    p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
    p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);

    log.debug("Stream properties:\n" + p.toString());
    return p;
  }

  @Bean
  SentenceStream sentenceStream(){

    SentenceStream stream = new SentenceStream(
        kafkaConfig,
        new SentenceStreamConfig(kafkaConfig),
        new StreamsBuilder());

    stream.streamRunner();

    return stream;
  }

}
