package powerex.backend.task.kafkastreams.producer;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import powerex.backend.task.kafkastreams.common.KafkaConfig;

@Slf4j
@Configuration
@AllArgsConstructor
public class KafkaCreateTopic {

  private final KafkaConfig kafkaConfig;

    @Bean
    public KafkaAdmin kafkaAdmin() {
      Map<String, Object> m = new HashMap<>();
      m.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getKafkaBrokers());
      return new KafkaAdmin(m);
    }

    @Bean
    public NewTopic rawDataTopic() {
      return new NewTopic(kafkaConfig.getRawDataTopic(), 3, (short) 1);
    }

    @Bean
    public NewTopic processedDataTopic() {
      return new NewTopic(kafkaConfig.getProcessedDataTopic(), 3, (short) 1);
    }

}
