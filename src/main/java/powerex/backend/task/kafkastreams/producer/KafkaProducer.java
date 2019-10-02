package powerex.backend.task.kafkastreams.producer;

import static powerex.backend.task.kafkastreams.common.Schemas.keySchema;
import static powerex.backend.task.kafkastreams.common.Schemas.valueSchema;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import powerex.backend.task.kafkastreams.common.KafkaConfig;
import powerex.backend.task.kafkastreams.common.SchemaFields;
import powerex.backend.task.kafkastreams.generator.input.SentenceCreator;

@Service
@Slf4j
public class KafkaProducer {

  private SentenceCreator sc = new SentenceCreator();

  private Producer<GenericRecord, GenericRecord> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(KafkaProducerConfig.producerProperties());

  public void send() {

    log.info(Instant.now().toString());

    GenericRecord key = new GenericRecordBuilder(keySchema)
        .set(SchemaFields.KEY_TIME_FIELD_NAME, Instant.now().toString())
        .build();

    GenericRecord value = new GenericRecordBuilder(valueSchema)
        .set(SchemaFields.VALUE_SENTENCE_FIELD_NAME, sc.getSentence())
        .build();

    producer.send(new ProducerRecord<>(KafkaConfig.RAW_DATA_TOPIC, key, value));
  }
}

