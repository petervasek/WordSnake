package powerex.backend.task.kafkastreams.consumer;

import java.time.Duration;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import powerex.backend.task.kafkastreams.generator.WordSnakeGenerator;

@Slf4j
public class KafkaConsumer {

  private static String inputTopic = "processed-sentence";
  private static Consumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(KafkaConsumerConfig.consumerProperties());

  public static void subscribe() {
    consumer.subscribe(Collections.singleton(inputTopic));
  }

  public static void consume() {
    int count = 0;

    do {
      final ConsumerRecords<String, String> records =
          consumer.poll(Duration.ofSeconds(3600));
      count++;
      records.forEach(record -> log.info(WordSnakeGenerator.getSnake(record.value())));
      consumer.commitAsync();
    } while (count <= 50);

    consumer.close();
    log.info("Consumer closed");
  }

}
