package powerex.backend.task.kafkastreams.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import powerex.backend.task.kafkastreams.common.SchemaFields;
import powerex.backend.task.kafkastreams.generator.output.WordSnakeGenerator;

@Slf4j
@EnableKafka
@EnableKafkaStreams
@AllArgsConstructor
public class KafkaConsumerProcessedData {

  private final WordSnakeGenerator snakeGenerator;

  @KafkaListener(topics = "${kafka.topics.processed-data}", groupId = "${kafka.consumer.group-id}")
  public void listen(GenericRecord record) {
    log.info(snakeGenerator.getSnake(record.get(
        SchemaFields.VALUE_SENTENCE_FIELD_NAME).toString()));
  }

}
