package powerex.backend.task.kafkastreams.streams;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class InputSentenceTransformerConfig {

  static Properties streamProperties() {
    Properties p = new Properties();
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "input-sentence-transformer");
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092,localhost:9093");
    p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    return p;
  }
}
