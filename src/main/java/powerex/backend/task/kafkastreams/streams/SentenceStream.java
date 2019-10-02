package powerex.backend.task.kafkastreams.streams;

import static powerex.backend.task.kafkastreams.common.Schemas.valueSchema;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import powerex.backend.task.kafkastreams.common.KafkaConfig;
import powerex.backend.task.kafkastreams.common.SchemaFields;

@Slf4j
public class SentenceStream {

  private final StreamsBuilder builder = new StreamsBuilder();

  @Bean
  public void streamRunner() {

    KStream<GenericRecord, GenericRecord> rawSentences = builder.stream(KafkaConfig.RAW_DATA_TOPIC);

    rawSentences.mapValues(
        v ->v.get(SchemaFields.VALUE_SENTENCE_FIELD_NAME).toString()
            .replaceAll("[.,]", " ")
            .replaceAll(" +", " ")
            .toUpperCase()
        )
        .mapValues(v -> new GenericRecordBuilder(valueSchema)
            .set(SchemaFields.VALUE_SENTENCE_FIELD_NAME, v)
            .build()
        )
        .to(KafkaConfig.PROCESSED_DATA_TOPIC);

    Topology topology = builder.build();

    try {
      KafkaStreams streams = new KafkaStreams(topology,
          SentenceStreamConfig.streamProperties());
      streams.start();
    } catch (IllegalStateException | StreamsException e) {
      log.error(e.toString());
    }

  }
}