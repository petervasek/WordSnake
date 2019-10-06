package powerex.backend.task.kafkastreams.producer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import powerex.backend.task.kafkastreams.common.KafkaConfig;
import powerex.backend.task.kafkastreams.generator.input.SentenceCreator;

@EnableKafka
@Configuration
@Slf4j
@AllArgsConstructor
class KafkaProducerConfig {

  private final KafkaConfig kafkaConfig;

  private <K, V>KafkaProducer<K, V> kafkaProducer() {
    Properties p = new Properties();
    p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getKafkaBrokers());
    p.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaConfig.getSchemaRegistryUrl());
    p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

    log.debug("Producer properties:\n" + p.toString());
    return new KafkaProducer<>(p);
  }

  @Bean
  KafkaProducerRawData kafkaProducerRawData(){

    return new KafkaProducerRawData(
        kafkaConfig,
        kafkaProducer(),
        new SentenceCreator());
  }
}
