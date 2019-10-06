package powerex.backend.task.kafkastreams.streams;

import static powerex.backend.task.kafkastreams.common.Schemas.valueSchema;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import powerex.backend.task.kafkastreams.common.KafkaConfig;
import powerex.backend.task.kafkastreams.common.SchemaFields;

@Slf4j
@EnableKafka
@EnableKafkaStreams
@AllArgsConstructor
public class SentenceStream {

  private final KafkaConfig kafkaConfig;

  private final SentenceStreamConfig sentenceStreamConfig;

  private final StreamsBuilder builder;

  void streamRunner() {

    KStream<GenericRecord, GenericRecord> rawSentences = builder.stream(kafkaConfig.getRawDataTopic());

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
        .to(kafkaConfig.getProcessedDataTopic());

    Topology topology = builder.build();

    try {
      KafkaStreams streams = new KafkaStreams(topology,
          sentenceStreamConfig.streamProperties());
      streams.start();
    } catch (IllegalStateException | StreamsException e) {
      log.error(e.toString());
    }

  }
}