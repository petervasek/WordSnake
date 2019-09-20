package powerex.backend.task.kafkastreams.producer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import powerex.backend.task.kafkastreams.generator.SentenceCreator;

@Service
public class KafkaProducer {

  private SentenceCreator sc = new SentenceCreator();
  private Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(KafkaProducerConfig.producerProperties());

  public void send() {
    producer.send(new ProducerRecord<>("raw-sentence", "testKey", sc.getSentence()));
  }
}

