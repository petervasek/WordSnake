package powerex.backend.task.kafkastreams.producer;

import static powerex.backend.task.kafkastreams.Schemas.keySchema;
import static powerex.backend.task.kafkastreams.Schemas.valueSchema;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import powerex.backend.task.kafkastreams.generator.input.SentenceCreator;

@Service
@Slf4j
public class KafkaProducer {

  private SentenceCreator sc = new SentenceCreator();

  private Producer<GenericRecord, GenericRecord> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(KafkaProducerConfig.producerProperties());

  public void send() {

    String outputTopic = "raw-sentence";

    log.info(Instant.now().toString());

    GenericRecord key = new GenericRecordBuilder(keySchema)
        .set("time", Instant.now().toString())
        .build();

    GenericRecord value = new GenericRecordBuilder(valueSchema)
        .set("sentence", sc.getSentence())
        .build();

    producer.send(new ProducerRecord<>(outputTopic, key, value));
  }
}

