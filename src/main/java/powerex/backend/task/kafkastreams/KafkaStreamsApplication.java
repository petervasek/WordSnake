package powerex.backend.task.kafkastreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import powerex.backend.task.kafkastreams.consumer.KafkaConsumer;
import powerex.backend.task.kafkastreams.streams.SentenceStream;

@SpringBootApplication
public class KafkaStreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamsApplication.class, args);

		SentenceStream.streamRunner();
		KafkaConsumer.subscribe();
		KafkaConsumer.consume();
	}
}
