package powerex.backend.task.kafkastreams.consumer;

import java.time.Duration;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import powerex.backend.task.kafkastreams.common.KafkaConfig;
import powerex.backend.task.kafkastreams.common.Schemas;
import powerex.backend.task.kafkastreams.generator.output.WordSnakeGenerator;

@Slf4j
public class KafkaConsumer {

  private final Consumer<GenericRecord, GenericRecord> consumer =
      new org.apache.kafka.clients.consumer.KafkaConsumer<>(KafkaConsumerConfig.consumerProperties());

  private final WordSnakeGenerator snakeGenerator = new WordSnakeGenerator();

  public void subscribe() {
    consumer.subscribe(Collections.singleton(KafkaConfig.PROCESSED_DATA_TOPIC));
  }

  public void consume() {
    int count = 0;

    do {
      final ConsumerRecords<GenericRecord, GenericRecord> records =
          consumer.poll(Duration.ofSeconds(3600));
      count++;
      records.forEach(record -> log.info(snakeGenerator.getSnake(record.value().get(
          Schemas.valueSchema.getFields().get(0).name()).toString())));
      consumer.commitAsync();
    } while (count <= 50);

    consumer.close();
    log.info("Consumer closed");
  }

}
