package powerex.backend.task.kafkastreams.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KafkaConfig {

  @Value("${kafka.bootstrap-servers}")
  private String kafkaBrokers;

  @Value("${kafka.schema-registry.url}")
  private String schemaRegistryUrl;

  @Value("${kafka.app.stream-id}")
  private String streamId;

  @Value("${kafka.consumer.group-id}")
  private String consumerGroupId;

  @Value("${kafka.topics.raw-data}")
  private String rawDataTopic;

  @Value("${kafka.topics.processed-data}")
  private String processedDataTopic;

}