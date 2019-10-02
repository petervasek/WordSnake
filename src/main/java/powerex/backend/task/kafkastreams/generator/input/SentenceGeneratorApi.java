package powerex.backend.task.kafkastreams.generator.input;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import powerex.backend.task.kafkastreams.producer.KafkaProducer;

@RestController
public class SentenceGeneratorApi {

  private final KafkaProducer kafkaProducer;

  public SentenceGeneratorApi(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  @GetMapping("/")
  public ResponseEntity<String> generate() {
    kafkaProducer.send();
    return ResponseEntity.ok("");
  }
}