package powerex.backend.task.kafkastreams.consumer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import powerex.backend.task.kafkastreams.common.KafkaConfig;
import powerex.backend.task.kafkastreams.generator.output.WordSnakeGenerator;

@EnableKafka
@Configuration
@Slf4j
@AllArgsConstructor
class KafkaConsumerConfig {

  private final KafkaConfig kafkaConfig;

  private Map<String, Object> kafkaConsumerConfig() {
    Map<String, Object> m = new HashMap<>();
    m.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getConsumerGroupId());
    m.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getKafkaBrokers());
    m.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaConfig.getSchemaRegistryUrl());
    m.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
    m.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);

    log.debug("Consumer properties:\n" + m.toString());
    return m;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<GenericRecord, GenericRecord>
  kafkaListenerContainerFactory() {

    ConcurrentKafkaListenerContainerFactory<GenericRecord, GenericRecord> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig()));

    return factory;
  }

  @Bean
  KafkaConsumerProcessedData kafkaConsumerProcessedData(){
    return new KafkaConsumerProcessedData(
        new WordSnakeGenerator());
  }

}