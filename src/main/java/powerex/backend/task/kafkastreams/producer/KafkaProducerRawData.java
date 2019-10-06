package powerex.backend.task.kafkastreams.producer;

import static powerex.backend.task.kafkastreams.common.Schemas.keySchema;
import static powerex.backend.task.kafkastreams.common.Schemas.valueSchema;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import powerex.backend.task.kafkastreams.common.KafkaConfig;
import powerex.backend.task.kafkastreams.common.SchemaFields;
import powerex.backend.task.kafkastreams.generator.input.SentenceCreator;

@Slf4j
@AllArgsConstructor
public class KafkaProducerRawData {

  private final KafkaConfig kafkaConfig;

  private final KafkaProducer<GenericRecord, GenericRecord> producer;

  private final SentenceCreator sentenceCreator;

  public void send() {

    log.info(Instant.now().toString());

    GenericRecord key = new GenericRecordBuilder(keySchema)
        .set(SchemaFields.KEY_TIME_FIELD_NAME, Instant.now().toString())
        .build();

    GenericRecord value = new GenericRecordBuilder(valueSchema)
        .set(SchemaFields.VALUE_SENTENCE_FIELD_NAME, sentenceCreator.getSentence())
        .build();

    log.info("Sentence generated: " + value.get(SchemaFields.VALUE_SENTENCE_FIELD_NAME));

    producer.send(new ProducerRecord<>(kafkaConfig.getRawDataTopic(), key, value));
  }
}

