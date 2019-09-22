package powerex.backend.task.kafkastreams.streams;

import static powerex.backend.task.kafkastreams.Schemas.valueSchema;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

@Slf4j
public class InputSentenceTransformer {

  private static StreamsBuilder builder = new StreamsBuilder();

  @Bean
  public static void streamRunner() {
    String inputTopic = "raw-sentence";
    String outputTopic = "processed-sentence";
    Logger log = LoggerFactory.getLogger("stream-logger");

    KStream<GenericRecord, GenericRecord> rawSentences = builder.stream(inputTopic);

    rawSentences.mapValues(
        v ->v.get("sentence").toString()
            .replaceAll("[.,]", " ")
            .replaceAll(" +", " ")
            .toUpperCase()
        )
        .mapValues(v -> new GenericRecordBuilder(valueSchema)
            .set("sentence", v)
            .build()
        )
        .to(outputTopic);

    Topology topology = builder.build();

    try {
      KafkaStreams streams = new KafkaStreams(topology,
          InputSentenceTransformerConfig.streamProperties());
      streams.start();
    } catch (IllegalStateException | StreamsException e) {
      log.error(e.toString());
    }

  }
}